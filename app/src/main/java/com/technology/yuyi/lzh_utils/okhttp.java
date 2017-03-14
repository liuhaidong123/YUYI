package com.technology.yuyi.lzh_utils;

import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by hp on 2016/8/3.
 */
public class okhttp {
    public static final int OK_GET=0;
    public static final  int OK_POST=1;
    public static final Gson gson=new Gson();
    public static final OkHttpClient okhttpclient=new OkHttpClient();
    public static Call getCall(String url, Map<String,String>mp, int state) {
        okhttpclient.setConnectTimeout(7000, TimeUnit.MILLISECONDS);
        if (state == OK_GET) {
            StringBuilder builderString = new StringBuilder();
            for (String s : mp.keySet()) {
                builderString.append(s);
                builderString.append("=");
                builderString.append(mp.get(s));
                builderString.append("&");
            }
            String bString=builderString.toString();
            bString=bString.substring(0,bString.length()-1);
            url = url +bString;
            Log.i("url--okHttpGet--",url);
            return okhttpclient.newCall(new Request.Builder().url(url).build());
        }
        else if (state == OK_POST) {
            FormEncodingBuilder builder = new FormEncodingBuilder();
            for (String s : mp.keySet()) {
                builder.add(s, mp.get(s));
            }
            Request request = new Request.Builder()
                    .url(url)
                    .post(builder.build())
                    .build();
            return okhttpclient.newCall(request);
        }
        return null;
    }
}
