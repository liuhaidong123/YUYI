package com.technology.yuyi.lzh_utils;

import android.net.Uri;

/**
 * Created by wanyu on 2017/3/20.
 */

public class RongUri {
//    rong://{packagename:应用包名}/conversation/[private|discussion|group]?targetId={目标Id}&[title={开启会话名称}]
    public static Uri uri_im=Uri.parse("rong://com.technology.yuyi/conversation/discussion?targetId=admin&title=医患对话");//启动会话页面的uri
}
