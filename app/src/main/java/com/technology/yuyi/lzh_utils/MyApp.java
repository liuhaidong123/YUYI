package com.technology.yuyi.lzh_utils;
import android.app.Activity;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.technology.yuyi.R;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;
import io.rong.push.RongPushClient;
import io.rong.push.common.RongException;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

/**
 * Created by wanyu on 2017/3/8.
 */

public class MyApp extends Application{
    public static Activity activityCurrent;
    private static List<Activity> list;
    @Override
    public void onCreate() {
        super.onCreate();
        list=new ArrayList<>();
        if (Build.VERSION.SDK_INT>=14){//4.0以上
            registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    list.add(activity);
                }

                @Override
                public void onActivityStarted(Activity activity) {

                }

                @Override
                public void onActivityResumed(Activity activity) {

                    activityCurrent=activity;
//                    Log.i("----Myapp----",activityCurrent.getClass().getSimpleName());
//                    Log.i("activityCurrent==null",(activityCurrent==null)+"");
                }

                @Override
                public void onActivityPaused(Activity activity) {

                }

                @Override
                public void onActivityStopped(Activity activity) {

                }

                @Override
                public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    list.remove(activity);
                }
            });
        }
//        CrashHandler catchHandler = CrashHandler.getInstance();
//        catchHandler.init(getApplicationContext());


        JPushInterface.setDebugMode(true);//发不时设为false
        JPushInterface.init(getApplicationContext());
            RongIM.init(this);
        RongIM.getInstance().setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                if (message!=null){
                    Log.e("-MessageListener-",message.getObjectName()+"--");
                    Log.e("--MessageListener----",message.getSenderUserId()+"--");
                    Log.e("--MessageListener----",message.getTargetId()+"--");
                    Log.e("--MessageListener----",message.getConversationType().getName()+"--");
                    Log.e("-Message--uid--",message.getUId()+"--");
                    UserInfo userInfo=message.getContent().getUserInfo();
                    if (userInfo!=null){
                        Log.e("-Message--userInfo--",userInfo.getName()+"--");

                        Log.e("-MessageListener----",message.getContent().getJsonMentionInfo().toString()+"--");
                    }
                    Log.i("-----id-----","---"+  message.getMessageId());

                }
                if (message!=null){
                    NotificationManager manager= (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder builder=new NotificationCompat.Builder(getApplicationContext());
                    builder.setContentTitle("收到一条新的消息").
                            setContentText("来自："+message.getTargetId()).
                            setTicker(message.getTargetId()).setWhen(System.currentTimeMillis())
                            .setPriority(100).
                            setAutoCancel(true).
                            setDefaults(Notification.DEFAULT_ALL)
                            .setSmallIcon(R.mipmap.logo);

                    Notification notification = builder.build();
                    notification.defaults=Notification.DEFAULT_ALL;
                    notification.flags = Notification.FLAG_AUTO_CANCEL;
                    Uri uri = Uri.parse("rong://" + getApplicationContext().getApplicationInfo().packageName).
                            buildUpon().appendPath("conversation").appendPath(Conversation.ConversationType.PRIVATE.getName().
                            toLowerCase()).appendQueryParameter("targetId", message.getTargetId()).appendQueryParameter("title",
                            "与"+message.getTargetId()+"聊天").build();
                    Intent intent=new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(uri);
                    PendingIntent pendingIntent= PendingIntent.getActivity(getApplicationContext(), 1, intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    notification.contentIntent=pendingIntent;
                    manager.notify(message.getMessageId(),notification);
                    
                }
                return false;
            }
        });


    }
    public static void removeActivity(){
        if (list!=null&&list.size()>0){
            for (int i=0;i<list.size();i++){
                Activity activity=list.get(i);
                Log.i("remove-名字--",activity.getClass().getSimpleName());
                activity.finish();
            }
            list.clear();
        }

    }
}
