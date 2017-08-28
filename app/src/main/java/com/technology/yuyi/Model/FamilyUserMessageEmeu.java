package com.technology.yuyi.Model;

import com.technology.yuyi.R;

/**
 * Created by wanyu on 2017/8/17.
 */
//处理血压，温度的高低判断
public enum  FamilyUserMessageEmeu {
    ERROR,NORMAL;//异常,正常

    //异常，偏高，正常，偏低
    static int[] resId={R.mipmap.abnormalf,R.mipmap.normalf};
    //temp温度36~37.3，maxP收缩压／高压90~140，minP舒张压／低压60~90
    public static FamilyUserMessageEmeu getInfo(float temp,float maxP,float minP){
            if (temp>36&&temp<37.3&&maxP>90&&maxP<140&&minP>60&&minP<90){
                return NORMAL;
            }
            return ERROR;
    }
    public static int getRes(FamilyUserMessageEmeu em){
        switch (em){
            case ERROR:
                return resId[0];
            case NORMAL:
                return resId[1];
        }
        return resId[1];//默认返回正常
    }
}
