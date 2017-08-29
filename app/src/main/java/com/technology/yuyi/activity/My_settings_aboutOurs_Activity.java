package com.technology.yuyi.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.Bean_MySetting_AboutUs;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyActivity;
import com.technology.yuyi.lzh_utils.XCRoundRectImageView;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//关于我们
public class My_settings_aboutOurs_Activity extends MyActivity {
    private XCRoundRectImageView my_settings_aboutOurs_imageview;
    private TextView my_settings_aboutOurs_name,my_settings_aboutOurs_pro,my_settings_aboutOurs_versionCode;
    private String resStr;
    private final Context con=My_settings_aboutOurs_Activity.this;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(con);
                    break;
                case 1:
                    try{
                        Bean_MySetting_AboutUs about=okhttp.gson.fromJson(resStr,Bean_MySetting_AboutUs.class);
                        if (about!=null){
                            if (about.getResult()!=null){
                                my_settings_aboutOurs_name.setText(about.getResult().getTitle());
                                my_settings_aboutOurs_pro.setText(about.getResult().getContent());
                                my_settings_aboutOurs_versionCode.setText(about.getResult().getVersion());
//                                Picasso.with(con).load(Ip.imagePth+about.getResult().getPicture()).error(R.mipmap.logo).into(my_settings_aboutOurs_imageview);
                            }

                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(con);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings_about_ours);
        initView();
        getData();//获取关于我们
    }

    private void initView() {
        titleTextView= (TextView) findViewById(R.id.activity_include_title);
        setTitleText("关于我们");
        my_settings_aboutOurs_imageview= (XCRoundRectImageView) findViewById(R.id.my_settings_aboutOurs_imageview);
        my_settings_aboutOurs_imageview.setRadios(12.5f);

        my_settings_aboutOurs_name= (TextView) findViewById(R.id.my_settings_aboutOurs_name);
        my_settings_aboutOurs_pro= (TextView) findViewById(R.id.my_settings_aboutOurs_pro);
        my_settings_aboutOurs_versionCode= (TextView) findViewById(R.id.my_settings_aboutOurs_versionCode);
    }

    //获取关与我们http://192.168.1.55:8080/yuyi/aboutUs/get.do
    public void getData() {
        Map<String,String> mp=new HashMap<>();
        okhttp.getCall(Ip.url+Ip.interface_My_Settings_AboutOur,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("关于宇医---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
