package com.technology.yuyi.lzh_utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by wanyu on 2017/3/1.
 * 获取用户的登陆状态，是否已经登陆，登陆后密码与用户名保存
 */

public class user {
    public static String userName;
    public static String userPsd;
    public static String RongToken="pYXW0VkX1wkT1F+nXKjS9ouZqhNeP62jUkZowKgPQxvQPCl3PhXaCyPbUOebIV6L3+usYxTImf3bddFByJixoA==";//155
    public static String token;//用户的token；
    public static String RonguserId;//155
    public static String targetId="166";//
    public static double Latitude;//纬度
    public static double Longitude;//经度
    public static int CallType=0;//融云音视频的type:0文字，1音频，2视频
    //当前是否登陆过（未退出登陆）
    public static boolean isLogin(Context context){
        SharedPreferences preferences=context.getSharedPreferences("USER",Context.MODE_APPEND);
        String username=preferences.getString("username","0");
        String userPs=preferences.getString("userpsd","0");
        String rongToken=preferences.getString("RongToken","0");
        if (!"0".equals(username)&&!"0".equals(userPs)&&!"".equals(username)&&!""
                .equals(userPs)&&!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(userPs)){
            userName=username;//存储到类中
            userPsd=userPs;//存储到类中
            token=userPs;
            return true;
        }
        return false;
    }
    //清除登陆信息测试用:在登陆页面：MainActivity会执行清除操作A
    public static void clearLogin(Context context){
        SharedPreferences preferences=context.getSharedPreferences("USER",Context.MODE_APPEND);
        SharedPreferences.Editor editor=preferences.edit();
        editor.remove("username");
        editor.remove("userpsd");
        editor.commit();
        userPsd="";
        userName="";
        token="";
    }

    //保存从服务器获取到到融云的相关信息
    public static void SaveRongMsg(RongUser user,Context context){
        SharedPreferences preferences=context.getSharedPreferences("Rong",Context.MODE_APPEND);
        SharedPreferences.Editor editor=preferences.edit();

        String username=preferences.getString("RongName","0");
        String userPs=preferences.getString("RongToken","0");
        String rongToken=preferences.getString("RongId","0");
    }

    public static void ClearRongMsg(){

    }
    //判断融云的token是否存在
    public static boolean isTokenExits(){
        return false;
    }
}
