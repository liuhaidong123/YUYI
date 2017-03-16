package com.technology.yuyi.HttpTools;

/**
 * Created by liuhaidong on 2017/3/7.
 */

public class UrlTools {

    public static final String URL="";
    public static final String BASE="http://192.168.1.55:8080/yuyi";
    public static final String URL_FIRST_PAGE_SIX_DATA="/drugs/findList.do?start=0&limit=6&cid=11";//首页常用药品6条数据
    public static final String URL_FIRST_PAGE_TWO_DATA="/hospital/findList.do?start=0&limit=2";//首页资讯2条数据
    public static final String URL_FIRST_PAGE_TWO_DATA_MESSAGE="/hospital/get.do?";//首页资讯详情数据（传id）
    public static final String URL_GET_VALIDATE_CODE="/personal/vcode.do";//获取验证码接口
    public static final String URL_LOGIN="/personal/login.do";//登录接口
    public static final String URL_USER_MESSAGE="/personal/get.do?";//我的页面用户信息接口（需要token）

}
