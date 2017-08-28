package com.technology.yuyi.lzh_utils;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.activity.MainActivity;
import com.technology.yuyi.activity.StartActivity;
import com.technology.yuyi.bean.beanRongToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.*;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by wanyu on 2017/4/6.
 */

public class RongConnect {
    private static Context con;
    private static String resStr;
    private static Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    MyDialog.stopDia();
                    Toast.makeText(con,"连接聊天服务器失败",Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    MyDialog.stopDia();
                    try{
                        beanRongToken tok= gson.gson.fromJson(resStr,beanRongToken.class);
                        if ("1".equals(tok.getCode())){
                            if (tok.getToken()!=null&&!"".equals(tok.getToken())){
                                user.RongToken=tok.getToken();
                                Log.i("Rong--",tok.getId()+"--"+tok.getTrueName());
                                RongIM.getInstance().setCurrentUserInfo(new io.rong.imlib.model.UserInfo(tok.getId()+"",tok.getTrueName()+"", Uri.parse(Ip.imagePth+tok.getAvatar())));
                                RongConnect.initRongCon(con);
                            }
                        }
                        else if ("0".equals(tok.getCode())){
                            Log.e("token错误--1-hospital--","---后台无法返回token----");
                        }
                        else {
                            Log.e("token错误--2-hospital-","---后台无法返回token----");
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(con);
                    }
                    break;
            }
        }
    };
    public static void initRongCon(Context context) {
        MyDialog.showDialog(context);
        con=context;
        if (context.getApplicationInfo().packageName.equals(getCurProcessName(context.getApplicationContext()))){
            RongIM.connect(user.RongToken, new RongIMClient.ConnectCallback() {
                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    MyDialog.stopDia();
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    MyDialog.stopDia();
                    user.RonguserId=userid;
                    Log.i("融云返回的id--hospital-",userid+"--hospital---");
                }
                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    MyDialog.stopDia();
                    Toast.makeText(con,"信息注册失败，无法启动咨询程序",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public static void getRongToken(Context context){
        MyDialog.showDialog(context);
        con=context;
        if (user.userName!=null&&!"0".equals(user.userName)&&!"".equals(user.userName)){
            Map<String,String> mp=new HashMap<>();
            mp.put("personalid",user.userName);
            Log.i("请求融云token---",Ip.url_F+Ip.interface_RongToken+"personalid="+user.userName);
            okhttp.getCall(Ip.url_F+Ip.interface_RongToken,mp,okhttp.OK_GET).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
                }
                @Override
                public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("--token请求-hospital-"+user.userName,resStr);
                    handler.sendEmptyMessage(1);
                }
            });
        }
        else {
            Log.e("userName--null","--------");
;        }
    }
}
