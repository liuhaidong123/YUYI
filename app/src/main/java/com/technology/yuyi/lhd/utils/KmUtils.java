package com.technology.yuyi.lhd.utils;

import android.util.Log;

import java.text.DecimalFormat;

/**
 * Created by liuhaidong on 2017/3/30.
 */

public class KmUtils {

    private static final double EARTH_RADIUS = 6378137.0;


    /** 返回单位是米
     *
     * @param longitude1 自己定位的经度
     * @param latitude1  自己定位的纬度
     * @param longitude2 目标经度
     * @param latitude2 目标纬度
     * @return
     */
    public static String getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        //保留一位小数
        DecimalFormat    df   = new DecimalFormat("######0.0");
        String km=df.format(s/1000);
        return km;
    }

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}

