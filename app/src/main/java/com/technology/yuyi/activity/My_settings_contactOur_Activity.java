package com.technology.yuyi.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.Bean_My_Settings_ContactUs;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyActivity;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class My_settings_contactOur_Activity extends MyActivity {
    private TextView my_settings_contactOur_phone;
    private TextView my_settings_contactOur_phoneNum;
    private String tele;
    private String result;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(My_settings_contactOur_Activity.this);
                    break;
                case 1:
                    try{
                        Bean_My_Settings_ContactUs cons= okhttp.gson.fromJson(result,Bean_My_Settings_ContactUs.class);
                        if (cons!=null){
                            if (cons.getResult()!=null){
                                my_settings_contactOur_phone.setText(cons.getResult().getTellText());
                                my_settings_contactOur_phoneNum.setText(cons.getResult().getTellNum());
                                tele=cons.getResult().getTellNum();
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        toast.toast_gsonFaild(My_settings_contactOur_Activity.this);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings_contact_our_);
        initView();
        getData();
    }

    private void initView() {
        titleTextView= (TextView) findViewById(R.id.activity_include_title);
        setTitleText("联系我们");
        my_settings_contactOur_phone= (TextView) findViewById(R.id.my_settings_contactOur_phone);
        my_settings_contactOur_phoneNum= (TextView) findViewById(R.id.my_settings_contactOur_phoneNum);

        tele=my_settings_contactOur_phoneNum.getText().toString();
    }

    //拨打电话的按钮
    public void callPhone(View view) {
        if (view!=null){
            if (view.getId()==R.id.my_settings_contactOur_callPhone){
                TelephonyManager manager= (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (manager==null){
                    Toast.makeText(this,"该设备不支持拨号功能",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    int stata=0;
                    switch(manager.getSimState()){ //getSimState()取得sim的状态  有下面6中状态
                        case TelephonyManager.SIM_STATE_ABSENT :
//                            sb.append("无卡");
                            stata=1;
                            break;
                        case TelephonyManager.SIM_STATE_UNKNOWN :
//                            sb.append("未知状态");
                            stata=1;
                            break;
                        case TelephonyManager.SIM_STATE_NETWORK_LOCKED :
//                            sb.append("需要NetworkPIN解锁");
                            stata=1;
                            break;
                        case TelephonyManager.SIM_STATE_PIN_REQUIRED :
//                            sb.append("需要PIN解锁");
                            stata=1;
                            break;
                        case TelephonyManager.SIM_STATE_PUK_REQUIRED :
//                            sb.append("需要PUK解锁");
                            stata=1;
                            break;
                        case TelephonyManager.SIM_STATE_READY :
//                            sb.append("良好");
                            stata=0;
                            break;
                    }
                    if (stata==1){
                        Toast.makeText(this,"该设备没有SIM卡或SIM异常，无法拨号",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                Intent intent = new Intent();//创建一个意图对象，用来激发拨号的Activity
                intent.setAction("android.intent.action.DIAL");//android.intent.action.CALL
                intent.setData(Uri.parse("tel:"+tele));
                startActivity(intent);//方法内部会自动添加类别,android.intent.category.DEFAULT
        }
        }
    }
//http://192.168.1.55:8080/yuyi/contactUs/getph.do
    public void getData() {
    Map<String,String> mp=new HashMap<>();
    okhttp.getCall(Ip.url+Ip.interface_My_Settings_ContactOur,mp,okhttp.OK_GET).enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onResponse(Response response) throws IOException {
            result=response.body().string();
            Log.i("联系我们---",result);
            handler.sendEmptyMessage(1);
        }
    });
    }
}
