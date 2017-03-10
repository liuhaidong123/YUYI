package com.technology.yuyi.lzh_utils;

import android.app.Application;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wanyu on 2017/3/8.
 */

public class MyApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);//发不时设为false
        JPushInterface.init(getApplicationContext());
        Toast.makeText(getApplicationContext(),"jp注册",Toast.LENGTH_SHORT).show();
    }
}
