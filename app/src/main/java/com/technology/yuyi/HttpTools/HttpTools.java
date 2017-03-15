package com.technology.yuyi.HttpTools;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.technology.yuyi.bean.City;
import com.technology.yuyi.bean.FirstPageDrugSixDataRoot;
import com.technology.yuyi.bean.FirstPageInformationTwoDataRoot;
import com.technology.yuyi.bean.Information;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import java.util.Map;

/**
 * Created by liuhaidong on 2017/3/7.
 */

public class HttpTools {

    private FinalHttp mFinalHttp;
    private static HttpTools mHttpTools;
    private Gson mGson = new Gson();

    private HttpTools() {
        if (mFinalHttp == null) {
            mFinalHttp = new FinalHttp();
        }
    }

    //获取本类的实例对象，并且初始化FinalHttp类
    public static HttpTools getHttpToolsInstance() {
        if (mHttpTools == null) {
            //当初始化本类的时候，会初始化mFinalHttp
            mHttpTools = new HttpTools();
        }
        return mHttpTools;
    }

    /**
     * 获取首页常用药品6条数据
     */
    public void getFirstSixDrugData(final Handler handler) {
        String url = UrlTools.BASE + UrlTools.URL_FIRST_PAGE_SIX_DATA;
        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart：", "请求开始药品6条数据");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess请求成功6条数据：", s);
                FirstPageDrugSixDataRoot root = mGson.fromJson(s, FirstPageDrugSixDataRoot.class);
                Message message = new Message();
                message.what = 21;
                message.obj = root;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure请求失败6条数据：", strMsg);
            }
        });
    }



    /**
     * 获取首页资讯2条数据
     */
    public void getFirstPageInformationTwoData(final Handler handler) {
        String url = UrlTools.BASE + UrlTools.URL_FIRST_PAGE_TWO_DATA;
        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart：", "请求开始资讯2条数据");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess请求成功资讯2条数据：", s);
                FirstPageInformationTwoDataRoot root = mGson.fromJson(s, FirstPageInformationTwoDataRoot.class);
                Message message = new Message();
                message.what = 22;
                message.obj = root;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure请求失败资讯2条数据：", strMsg);
            }
        });
    }

    /**
     * 获取首页资讯2条数据的详情
     */
    public void getFirstPageInformationTwoDataMessage(final Handler handler,int id) {
        String url = UrlTools.BASE + UrlTools.URL_FIRST_PAGE_TWO_DATA_MESSAGE+"id="+id;
        mFinalHttp.get(url, new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart：", "请求开始资讯2条数据详情");
            }

            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
                Log.e("onSuccess请求成功资讯2条数据详情：", s);
                Information root = mGson.fromJson(s, Information.class);
                Message message = new Message();
                message.what = 23;
                message.obj = root;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure请求失败资讯2条数据详情：", strMsg);
            }
        });
    }









    //获取网络数据(get方法)
    public void getMessage(final Handler handler, String param1, String param2, String param3) {
        String url = UrlTools.BASE + UrlTools.URL + "&param1=" + param1 + "&param2=" + param2 + "&param3=" + param3;
        mFinalHttp.get(url, new AjaxCallBack<String>() {
            //开始网络请求
            @Override
            public void onStart() {
                super.onStart();
                Log.e("onStart：", "请求开始");
            }

            //网络请求失败
            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
                Log.e("onFailure请求失败：", strMsg);
            }

            //网络请求成功，返回json字符串
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
//                City city = mGson.fromJson(s, City.class);
//                Message message = new Message();
//                message.what = 1;
//                message.obj = city;
//                handler.sendMessage(message);
                Log.e("onSuccess请求成功：", s);
            }
        });
    }

    //获取网络数据（post方法）
    public void submitLoginMessage(final Handler handler, Map<String, String> map) {
        String url = UrlTools.BASE + UrlTools.URL;
        mFinalHttp.post(url, new AjaxParams(map), new AjaxCallBack<String>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            //获取数据成功
            @Override
            public void onSuccess(String s) {
                super.onSuccess(s);
//               City city = mGson.fromJson(s, City.class);
//                Message message = new Message();
//                message.what = 2;
//                message.obj = city;
//                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                super.onFailure(t, errorNo, strMsg);
            }
        });
    }
}
