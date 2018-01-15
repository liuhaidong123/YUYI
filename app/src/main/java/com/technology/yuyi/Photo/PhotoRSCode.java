package com.technology.yuyi.Photo;

/**
 * Created by wanyu on 2017/9/27.
 */
//定义使用到的请求码与对应的结果吗(r开头的是请求吗，s开头的是结果吗)
public interface PhotoRSCode {
    //私有请求吗与结果码
    int requestCode_Search=101;//浏览系统相册请求码
    int requestCode_Camera=102;//拍照请求码
    //权限请求码
    int requestCode_SearchPermission=201;//浏览图库权限权限请求码
    int requestCode_CameraPermission=202;//拍照权限权限请求码
}
