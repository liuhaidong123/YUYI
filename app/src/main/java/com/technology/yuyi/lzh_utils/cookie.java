package com.technology.yuyi.lzh_utils;

import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

/**
 * Created by wanyu on 2017/3/21.
 */

public class cookie implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        List<String> cookieList =  originalResponse.headers("Set-Cookie");
        if(cookieList != null) {
            for(String s:cookieList) {//Cookie的格式为:cookieName=cookieValue;path=xxx
                myCookie+=s;
            }
        }
        return originalResponse;
    }
    public static String myCookie;
}
