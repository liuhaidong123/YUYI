package com.technology.yuyi.lzh_utils;

import android.app.Activity;
import android.content.Context;

/**
 * Created by wanyu on 2017/8/21.
 */

public class WindowUtils {
    static WindowUtils utlis;
    private WindowUtils(){

    }
    public static WindowUtils getInstance(){
        if (utlis==null){
            utlis=new WindowUtils();
        }
        return utlis;
    }
    public int getWindowWidth(Activity con){
        return con.getWindowManager().getDefaultDisplay().getWidth();
    }
}
