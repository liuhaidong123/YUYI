package com.technology.yuyi.lzh_utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wanyu on 2017/3/1.
 * 获取用户的登陆状态，是否已经登陆，登陆后密码与用户名保存
 */

public class user {
    public static String userName;
    public static String userPsd;
    //当前是否登陆过（未退出登陆）
    public static boolean isLogin(Context context){
        SharedPreferences preferences=context.getSharedPreferences("USER",Context.MODE_APPEND);
        String username=preferences.getString("username","0");
        String userPs=preferences.getString("userpsd","0");
        if (!"0".equals(username)&&!"0".equals(userPs)){
            userName=username;//存储到类中
            userPsd=userPs;//存储到类中
            return true;
        }
        return false;
    }
    //清楚登陆信息测试用:在登陆页面：MainActivity会执行清楚操作
    public static void clearLogin(Context context){
        SharedPreferences preferences=context.getSharedPreferences("USER",Context.MODE_APPEND);
        SharedPreferences.Editor editor=preferences.edit();
        editor.remove("username");
        editor.remove("userpsd");
        editor.commit();
    }
}
