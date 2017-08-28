package com.technology.yuyi.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.technology.yuyi.Model.My_Settings_ChangePhoneModel;
import com.technology.yuyi.PhoneCheck.CheckPhone;
import com.technology.yuyi.bean.BeanChangePhone;
import com.technology.yuyi.bean.bean_SMScode;
import com.technology.yuyi.lzh_utils.user;

import static android.content.Context.MODE_APPEND;

/**
 * Created by wanyu on 2017/8/23.
 */

public class My_Settings_ChangePhonePresenter implements My_Settings_ChangePhoneModel.IChangePhoneModel {
    Context con;
    boolean flag=false;//是否正在请求
    int Time=10;//接收到60的时候不能点击
    My_Settings_ChangePhoneModel iModel;
    IChangePhonePresenter iPresenter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what=msg.what;
            if (what>0){
                iPresenter.onClickAble(Time);
            }
            else if (what==0){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            handler.sendEmptyMessage(-2);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                iPresenter.onClickAble(0);
            }
            else if(msg.what==-2){
                iPresenter.onClickAble(-2);
            }
        }
    };
    public My_Settings_ChangePhonePresenter(IChangePhonePresenter iPresenter,Context con){
        this.con=con;
        this.iPresenter=iPresenter;
    }

    public void getSmsCode(String phoneNumber){
        if (CheckPhone.isPhoneNumber(phoneNumber)){
            if (iModel==null){
                iModel=new My_Settings_ChangePhoneModel(this);
            }
            if (flag==false){
                flag=true;
                iModel.getSMSCode(phoneNumber);
            }
        }
        else {
            iPresenter.onInputPhoneError();
            }
    }
    public void changePhone(String phone,String sms){
        if (CheckPhone.isPhoneNumber(phone)){
            if (!"".equals(sms)&&!TextUtils.isEmpty(sms)&&sms.length()==6){
                if (iModel==null){
                    iModel=new My_Settings_ChangePhoneModel(this);
                }
                iModel.changePhone(phone,sms);
            }
            else {
                iPresenter.onInputSMSCodeError();
            }
        }
        else {
            iPresenter.onInputPhoneError();
        }
    }
    @Override
    public void onGetSMSCodeSuccess(bean_SMScode bean) {
        if (bean!=null){
            if ("0".equals(bean.getCode())){
                flag=false;//false当前可以点击（正在请求验证码，没有返回结果，防止用户点击过快），true不能点击
                Time=60;//获取成功后初始化
                iPresenter.ongetSMSSuccess();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (Time>0) {
                            try {
                                Time--;
                                handler.sendEmptyMessage(Time);
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
            else {
                iPresenter.onChangePhoneError(bean.getResult());
            }
        }
    }

    @Override
    public void onGetSMSCodeError(String msg) {
            flag=false;
            iPresenter.ongetSMSError(msg);
    }
    @Override
    public void onChangePhoneSuccess(BeanChangePhone bean) {
        if (bean!=null){
            if ("0".equals(bean.getCode())){
                //保存登录信息
                SharedPreferences pre = con.getSharedPreferences("USER", MODE_APPEND);
                SharedPreferences.Editor edi = pre.edit();
                edi.putString("username", bean.getPersonal().getId()+"");//手机号
                edi.putString("userpsd", bean.getResult());//token
                user.token=bean.getResult();
                user.userName=bean.getPersonal().getId()+"";
                user.userPsd=user.token;
                edi.commit();
                iPresenter.onChangePhoneSuccess();
            }
            else {
                iPresenter.onChangePhoneError(bean.getMessage());
            }
        }
        else {
            iPresenter.onChangePhoneError("更改失败");
        }
    }

    @Override
    public void onChangePhoneError(String msg) {
        iPresenter.onChangePhoneError(msg);
    }

    public interface IChangePhonePresenter{
        void onClickAble(int tm);//获取验证码的按钮可以被再次点击0的时候可以再点击
        void onInputPhoneError();//手机号格式不正确
        void onInputSMSCodeError();//验证码格式不正确
        void ongetSMSError(String msg);//获取验证码失败
        void onChangePhoneError(String msg);//更改手机号失败
        void ongetSMSSuccess();//获取验证码成功
        void onChangePhoneSuccess();//更改手机号成功
    }
}
