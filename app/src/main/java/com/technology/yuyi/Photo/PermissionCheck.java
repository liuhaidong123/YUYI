package com.technology.yuyi.Photo;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * Created by wanyu on 2017/10/9.
 */

public class PermissionCheck {
    static PermissionCheck priCheck;
    private PermissionCheck(){

    }
    public static synchronized PermissionCheck getInstance(){
        if (priCheck==null){
            priCheck=new PermissionCheck();
        }
        return priCheck;
    }
    //是否已经允许了权限
    public boolean isPermissionGet(String pri[], Activity ac){
        boolean isPriGet=true;
        if (Build.VERSION.SDK_INT >= 23){
            for (String pr:pri){
                if (ContextCompat.checkSelfPermission(ac,pr) != PackageManager.PERMISSION_GRANTED){
                    isPriGet=false;
                }
            }
        }
        return isPriGet;
    }
}
