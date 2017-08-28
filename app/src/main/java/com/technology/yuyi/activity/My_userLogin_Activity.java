package com.technology.yuyi.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.LoginSuccess;
import com.technology.yuyi.bean.ValidateCodeRoot;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.JPshAliasAndTags;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.UserInfo;
import com.technology.yuyi.lzh_utils.user;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class My_userLogin_Activity extends AppCompatActivity {
    private int timeOut = 60;//计时器
    private TextView my_userlogin_getSMScode;//获取验证码按钮
    private TextView my_userlogin_SMStimer;//显示计时器的view
    private EditText my_userlogin_edit_name, my_userlogin_edit_smdCode;//用户名与验证码输入框
    private String userName, userPsd;

    private HttpTools mHttptools;
    private Map<String, String> mMap = new HashMap<>();//验证码
    private Map<String, String> mLoginMap = new HashMap<>();//登录
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what=msg.what;
            if (what>0){
                my_userlogin_SMStimer.setText(what+ "S");
                my_userlogin_SMStimer.setVisibility(View.VISIBLE);
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
                my_userlogin_SMStimer.setVisibility(View.GONE);
            }
            else if(msg.what==-2){
                my_userlogin_getSMScode.setClickable(true);
                my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_smscode));
            }
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 26) {//获取验证码
                MyDialog.stopDia();
                Object o = msg.obj;
                if (o != null && o instanceof ValidateCodeRoot) {
                    ValidateCodeRoot root = (ValidateCodeRoot) o;
                    if (root.getCode() != "" && root.getCode().equals("0")&&root.getResult().equals("Success")) {
                        ToastUtils.myToast(My_userLogin_Activity.this, "获取验证码成功");
                    }else {
                        MyDialog.stopDia();
                        ToastUtils.myToast(My_userLogin_Activity.this, "获取验证码失败");
                    }
                }
            } else if (msg.what==208){
                MyDialog.stopDia();
            } else if (msg.what==209){
                MyDialog.stopDia();
            }

            else if (msg.what == 27) {//登录
                MyDialog.stopDia();
                Object o = msg.obj;
                if (o != null && o instanceof LoginSuccess) {
                    LoginSuccess root = (LoginSuccess) o;
                    if (root.getCode() != "" && root.getCode().equals("0")) {
                        //激光注册标签
                        JPshAliasAndTags.setAlias(My_userLogin_Activity.this,userName);
                        Toast.makeText(My_userLogin_Activity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        SharedPreferences pre = getSharedPreferences("USER", MODE_APPEND);
                        SharedPreferences.Editor edi = pre.edit();
                        edi.putString("username", userName);
                        edi.putString("userpsd", root.getResult());
                        userPsd=root.getResult();
                        userPsd=userName;
                        user.userName=userName;
                        user.token=root.getResult();
                        Log.e("token：",root.getResult());
                        edi.commit();

                        Intent intent = new Intent();
                        intent.putExtra("type","1");
                        LoginSuccess.PersonalBean personalBean=root.getPersonal();
                        if (personalBean!=null){
                            try{
                                if (UserInfo.isUserInfoCompletion(personalBean.getTrueName(),personalBean.getAge()+"",personalBean.getGender()+"")){
                                    intent.setClass(My_userLogin_Activity.this, MainActivity.class);
                                }
                                else {
                                    intent.setClass(My_userLogin_Activity.this, UserEditorActivity.class);
                                }
                            }
                            catch (Exception e){
                                intent.setClass(My_userLogin_Activity.this, UserEditorActivity.class);
                            }

                        }
                        else {
                            //d点击登录注释
                            intent.setClass(My_userLogin_Activity.this, UserEditorActivity.class);
                        }
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(My_userLogin_Activity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                        MyDialog.stopDia();
                    }
                }
            }else if (msg.what==210){
                MyDialog.stopDia();
            }else if (msg.what==211){
                MyDialog.stopDia();
                Toast.makeText(My_userLogin_Activity.this, "登陆失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user_login_);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        mHttptools = HttpTools.getHttpToolsInstance();
        my_userlogin_getSMScode = (TextView) findViewById(R.id.my_userlogin_getSMScode);
        my_userlogin_SMStimer = (TextView) findViewById(R.id.my_userlogin_SMStimer);
        my_userlogin_SMStimer.setVisibility(View.INVISIBLE);
        my_userlogin_edit_name = (EditText) findViewById(R.id.my_userlogin_edit_name);
        my_userlogin_edit_smdCode = (EditText) findViewById(R.id.my_userlogin_edit_smdCode);
        my_userlogin_getSMScode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = my_userlogin_edit_name.getText().toString();
                if (!"".equals(userName) && !TextUtils.isEmpty(userName)) {
                    if (isPhoneNum(userName)) {
                        mMap.put("id", userName);//验证码需要的map集合
                        getSMScode();

                    } else {
                        Toast.makeText(My_userLogin_Activity.this, "用户名不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(My_userLogin_Activity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        my_userlogin_SMStimer.setVisibility(View.GONE);
        my_userlogin_getSMScode.setClickable(true);
        my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_smscode));
    }

    //获取验证码
    private void getSMScode() {
        MyDialog.showDialog(My_userLogin_Activity.this);
        my_userlogin_getSMScode.setClickable(false);//获取验证码按钮不能点击
        my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_unclick));
        mHttptools.getValidateCode(mHandler, mMap);//获取验证码接口
        timeOut=60;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (timeOut>0) {
                    try {
                        timeOut--;
                        handler.sendEmptyMessage(timeOut);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }

    //判断是否输入的为手机号
    public boolean isPhoneNum(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    //登陆按钮
    public void Login(View view) {
        if (view != null) {
            if (view.getId() == R.id.my_userlogin_logninButton) {
                userName = my_userlogin_edit_name.getText().toString();
                userPsd = my_userlogin_edit_smdCode.getText().toString();
                if (isPhoneNum(userName) && !"".equals(userPsd) && !TextUtils.isEmpty(userPsd)) {
                    //登录
                    MyDialog.showDialog(My_userLogin_Activity.this);
                    mLoginMap.put("id", userName);
                    mLoginMap.put("vcode", userPsd);
                    mHttptools.login(mHandler, mLoginMap);
                } else {
                    Toast.makeText(My_userLogin_Activity.this, "用户名或密码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
