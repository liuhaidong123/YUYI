package com.technology.yuyi.PhoneCheck;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wanyu on 2017/8/23.
 */

public class CheckPhone {
    int time=60;//验证码读秒

    //检查字符串是不是电话号码
    public static boolean isPhoneNumber(String phone){
        if (!"".equals(phone)&&!TextUtils.isEmpty(phone)){
            //判断是否输入的为手机号
                String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-9])|(147))\\d{8}$";
                Pattern p = Pattern.compile(regExp);
                Matcher m = p.matcher(phone);
                return m.matches();
        }
        return false;
    }
}
