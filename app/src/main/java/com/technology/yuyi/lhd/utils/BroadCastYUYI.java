package com.technology.yuyi.lhd.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * Created by liuhaidong on 2017/4/17.
 */

public  class BroadCastYUYI extends BroadcastReceiver {
    //public static boolean isNetwork = true;

    public static BroadCastYUYI broadCastYUYI=null;
    public static SharedPreferencesUtils sharedPreferencesUtils;
    public static BroadCastYUYI getBroadcastinstance(Context context){
        sharedPreferencesUtils=SharedPreferencesUtils.getSharedPreferencesUtils(context);
        if (broadCastYUYI==null){
            broadCastYUYI=new BroadCastYUYI();
            return broadCastYUYI;
        }
        return broadCastYUYI;
    }
    public BroadCastYUYI(){};
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            sharedPreferencesUtils.setIsnetwork("network",true);
            ToastUtils.myToast(context, "宇医有网了456");
        } else {
            sharedPreferencesUtils.setIsnetwork("network",false);
            ToastUtils.myToast(context, "宇医没网了456");
        }
    }
}
