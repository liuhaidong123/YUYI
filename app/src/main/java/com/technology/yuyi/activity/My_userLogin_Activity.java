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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class My_userLogin_Activity extends AppCompatActivity {
    private int timeOut=60;//计时器
    private boolean isClick=true;//获取验证码按钮是否可用点击
    private TextView my_userlogin_getSMScode;//获取验证码按钮
    private TextView my_userlogin_SMStimer;//显示计时器的view
    private EditText my_userlogin_edit_name,my_userlogin_edit_smdCode;//用户名与验证码输入框
    private String userName,userPsd;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what!=0&&timeOut!=-1){
                my_userlogin_SMStimer.setText(timeOut+"S");
            }
            else if (msg.what==0){
                my_userlogin_SMStimer.setVisibility(View.GONE);
                my_userlogin_getSMScode.setClickable(true);
                my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_smscode));
                isClick=true;
            }
            else if (msg.what==-1){
                timeOut=60;
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
        isClick=true;//可点击
        timeOut=60;//计数器归60
        my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_smscode));
        my_userlogin_SMStimer.setVisibility(View.GONE);
    }

    private void initView() {
        my_userlogin_getSMScode= (TextView) findViewById(R.id.my_userlogin_getSMScode);
        my_userlogin_SMStimer= (TextView) findViewById(R.id.my_userlogin_SMStimer);
        my_userlogin_SMStimer.setVisibility(View.INVISIBLE);
        my_userlogin_edit_name= (EditText) findViewById(R.id.my_userlogin_edit_name);
        my_userlogin_edit_smdCode= (EditText) findViewById(R.id.my_userlogin_edit_smdCode);
        my_userlogin_getSMScode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=my_userlogin_edit_name.getText().toString();
                if (!"".equals(userName)&&!TextUtils.isEmpty(userName)){
                    if (isPhoneNum(userName)){
                        my_userlogin_getSMScode.setClickable(false);//获取验证码按钮不能点击
                        my_userlogin_SMStimer.setVisibility(View.VISIBLE);
                        my_userlogin_SMStimer.setText(timeOut+"S");
                        my_userlogin_getSMScode.setBackground(getResources().getDrawable(R.drawable.my_userlogin_unclick));
                        if (isClick){
                            getSMScode();
                            isClick=false;
                        }

                    }
                    else {
                        Toast.makeText(My_userLogin_Activity.this,"用户名不正确",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(My_userLogin_Activity.this,"用户名不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //获取验证码
    private void getSMScode() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (timeOut!=-1){
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
    public boolean isPhoneNum(String str){
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }
//登陆按钮
    public void Login(View view) {
        if (view!=null){
            if (view.getId()==R.id.my_userlogin_logninButton){
                userName=my_userlogin_edit_name.getText().toString();
                userPsd=my_userlogin_edit_smdCode.getText().toString();
                if (isPhoneNum(userName)&&!"".equals(userPsd)&&!TextUtils.isEmpty(userPsd)){
                    Toast.makeText(My_userLogin_Activity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                    SharedPreferences pre=getSharedPreferences("USER",MODE_APPEND);
                    SharedPreferences.Editor edi=pre.edit();
                    edi.putString("username",userName);
                    edi.putString("userpsd",userPsd);
                    edi.commit();
                    //d点击登录注释
                    Intent intent=new Intent();
                    intent.setClass(My_userLogin_Activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(My_userLogin_Activity.this,"用户名或密码不正确",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
