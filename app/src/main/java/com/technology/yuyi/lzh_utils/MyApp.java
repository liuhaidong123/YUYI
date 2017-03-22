package com.technology.yuyi.lzh_utils;

import android.app.Activity;
import android.app.Application;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;


/**
 * Created by wanyu on 2017/3/8.
 */

public class MyApp extends Application{
    public static Activity activityCurrent;
    @Override
    public void onCreate() {
        super.onCreate();
//        if (Build.VERSION.SDK_INT>=14){//4.0以上
//            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//                @Override
//                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//
//                }
//
//                @Override
//                public void onActivityStarted(Activity activity) {
//
//                }
//
//                @Override
//                public void onActivityResumed(Activity activity) {
//                    activityCurrent=activity;
//                    Log.i("----Myapp----",activityCurrent.getClass().getSimpleName());
//                    Log.i("activityCurrent==null",(activityCurrent==null)+"");
//
//                }
//
//                @Override
//                public void onActivityPaused(Activity activity) {
//
//                }
//
//                @Override
//                public void onActivityStopped(Activity activity) {
//
//                }
//
//                @Override
//                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//                }
//
//                @Override
//                public void onActivityDestroyed(Activity activity) {
//
//                }
//            });
//        }
//        CrashHandler catchHandler = CrashHandler.getInstance();
//        catchHandler.init(getApplicationContext());


        JPushInterface.setDebugMode(true);//发不时设为false
        JPushInterface.init(getApplicationContext());
//        Toast.makeText(getApplicationContext(),"jp注册",Toast.LENGTH_SHORT).show();
        RongIM.init(this);

    }
}
