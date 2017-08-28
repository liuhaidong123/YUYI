package com.technology.yuyi.Model;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.BeanChangePhone;
import com.technology.yuyi.bean.bean_SMScode;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.user;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/8/23.
 */
//修改绑定手机号
public class My_Settings_ChangePhoneModel{
    String cooki="";
    String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case -1://获取验证码失败
                    imodel.onGetSMSCodeError("网络异常");
                    break;
                case 1://获取验证码
                    try{
                        bean_SMScode bean= gson.gson.fromJson(resStr,bean_SMScode.class);
                        imodel.onGetSMSCodeSuccess(bean);
                    }
                    catch (Exception e){
                        imodel.onGetSMSCodeError("数据异常");
                        e.printStackTrace();
                    }
                    break;
                case -2://切换手机号
                    imodel.onChangePhoneError("网络异常");
                    break;
                case 2://切换手机号失败
                    try{
                        BeanChangePhone bean=gson.gson.fromJson(resStr,BeanChangePhone.class);
                        imodel.onChangePhoneSuccess(bean);
                    }
                    catch (Exception e){
                        imodel.onGetSMSCodeError("数据异常");
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };
    IChangePhoneModel imodel;
    public My_Settings_ChangePhoneModel(IChangePhoneModel imodel) {
    this.imodel=imodel;
    }

    //获取验证码参数：电话号码，请确保号码是正确的
    public void getSMSCode(String phone) {
        cooki="";//初始化cooki
        Map<String, String> mp = new HashMap<>();
        mp.put("id", phone);
        okhttp.getCall(Ip.url +Ip.interface_GetSMSCode, mp, okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(-1);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                cooki=response.headers().get("Set-Cookie");
                Log.i("更改手机号验证码",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
//    http://192.168.1.168:8082/yuyi/personal/modifymobile.do?token=1213&newMobile=13717883009&vcode=123456
//    参数：
//    token=令牌
//            newMobile=新手机号
//    vcode=验证码
    public void changePhone(String phone,String sms){
        if (!"".equals(cooki)&&!TextUtils.isEmpty(cooki)){
            Map<String,String>mp=new HashMap<>();
            mp.put("token", user.token);
            mp.put("newMobile",phone);
            mp.put("vcode",sms);
            okhttp.getCallCookie(Ip.url+Ip.interface_ChangePhone,mp,cooki).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(-2);
                }
                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("更改绑定手机号",resStr);
                    handler.sendEmptyMessage(2);
                }
            });
        }
        else {
            imodel.onChangePhoneError("验证码失效");
            }
    }
    public interface IChangePhoneModel{
        void onGetSMSCodeSuccess(bean_SMScode bean);
        void onGetSMSCodeError(String msg);
        void onChangePhoneSuccess(BeanChangePhone bean);
        void onChangePhoneError(String msg);
    }
}

