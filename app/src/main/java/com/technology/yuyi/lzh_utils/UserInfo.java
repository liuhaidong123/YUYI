package com.technology.yuyi.lzh_utils;

import android.text.TextUtils;

/**
 * Created by wanyu on 2017/3/31.
 */

public class UserInfo {
    public static boolean isUserInfoCompletion(String name,String age,String gender){
            if (!"".equals(name)&&!TextUtils.isEmpty(name)&&!""
                    .equals(age)&&!TextUtils.isEmpty(age)&&!""
                    .equals(gender)&&!TextUtils.isEmpty(gender)&&!"0".equals(age)){
                return true;
            }
            return false;
    }
}
