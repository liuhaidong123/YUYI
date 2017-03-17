package com.technology.yuyi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_My_settings_feadus;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by wanyu on 2017/3/1.
 */

public class My_settings_feedbackIdea_Activity extends Activity {
    private EditText my_settings_idea_editIdea,my_settings_idea_editContact;
    private TextView my_settings_idea_textNum;
    private String resultStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(My_settings_feedbackIdea_Activity.this);
                    break;
                case 1:
                    try{
                            bean_My_settings_feadus feadus= gson.gson.fromJson(resultStr,bean_My_settings_feadus.class);
                            String code=feadus.getCode();
                            if ("-1".equals(code)){
                                Toast.makeText(My_settings_feedbackIdea_Activity.this,"当前登陆状态失效，请重新登陆",Toast.LENGTH_SHORT).show();
                                user.clearLogin(My_settings_feedbackIdea_Activity.this);
                            }
                        else if ("0".equals(code)){
                                Toast.makeText(My_settings_feedbackIdea_Activity.this,"提交成功",Toast.LENGTH_SHORT).show();
                            }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(My_settings_feedbackIdea_Activity.this);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings_feedbackidea);
        initView();

    }

    private void initView() {

        my_settings_idea_editIdea= (EditText) findViewById(R.id.my_settings_idea_editIdea);
        my_settings_idea_editContact= (EditText) findViewById(R.id.my_settings_idea_editContact);
        my_settings_idea_textNum= (TextView) findViewById(R.id.my_settings_idea_textNum);

        my_settings_idea_editIdea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text=s.toString();
                if (!"".equals(s)&&!TextUtils.isEmpty(text)){
                    int length=text.length();
                    my_settings_idea_textNum.setText(length+"/"+200);
                }
                else {
                    my_settings_idea_textNum.setText(0+"/"+200);
                }
            }
        });

    }
    public void goBack(View view){
        if (view!=null){
            if (view.getId()==R.id.activity_idea_include_imageReturn){
                finish();
            }
        }
    }
//提交意见和联系方式
    public void submitIdea(View view) {
        if (view!=null){
            if (view.getId()==R.id.my_settings_idea_submit){
                String idea=my_settings_idea_editIdea.getText().toString();
                String contact=my_settings_idea_editContact.getText().toString();
                if (!"".equals(idea)&&!"".equals(contact)&&!TextUtils.isEmpty(idea)&&!TextUtils.isEmpty(contact)){
//                    http://localhost:8080/yuyi/feedback//save.do?content=“”&contact=192873637&token=2E8B4C79121FBC6CB1377B190C663F52
                    Map<String,String> mp=new HashMap<>();
                    mp.put("content",idea);
                    mp.put("token", user.userPsd);
                    mp.put("contact",contact);
                    okhttp.getCall(Ip.url_F+Ip.interface_User_feedus,mp,okhttp.OK_GET).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            handler.sendEmptyMessage(0);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            resultStr=response.body().string();
                            handler.sendEmptyMessage(1);
                            Log.i("意见反馈返回的数据---",""+resultStr);
                        }
                    });
                }
            }
        }

    }
}
