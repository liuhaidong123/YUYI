package com.technology.yuyi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.My_messageListView_Adapter;
import com.technology.yuyi.bean.bean_MyMessage;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyActivity;
import com.technology.yuyi.lzh_utils.MyEmptyListView;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/2.
 */
//通知信息列表
public class My_message_Activity extends MyActivity{
    private MyEmptyListView my_message_listview;
    private My_messageListView_Adapter adapter;
    private List<bean_MyMessage.ResultBean>listBean;
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(My_message_Activity.this);
                    my_message_listview.setError();
                    break;
                case 1:
                    try{
                        bean_MyMessage myMessage= gson.gson.fromJson(resStr,bean_MyMessage.class);
                        if (myMessage!=null){
                            if ("0".equals(myMessage.getCode())){
                                if (myMessage.getResult()!=null&&myMessage.getResult().size()>0){
                                    listBean.addAll(myMessage.getResult());
                                    adapter.notifyDataSetChanged();
                                }
                                else {
                                    toast.toast_gsonEmpty(My_message_Activity.this);
                                }
                            }
                            else {
                                toast.toast_gsonEmpty(My_message_Activity.this);
                            }
                        }
                        else {
                            toast.toast_gsonEmpty(My_message_Activity.this);
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(My_message_Activity.this);
                    }
                        my_message_listview.setEmpty();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initVIew();
//        initData();//测试用
        getMessage();
    }


    private void initVIew() {
        titleTextView= (TextView) findViewById(R.id.activity_include_title);
        titleTextView.setText("消息");
        my_message_listview= (MyEmptyListView) findViewById(R.id.my_message_listview);
        listBean=new ArrayList<>();
        adapter=new My_messageListView_Adapter(this,listBean);
        my_message_listview.setAdapter(adapter);
    }


//    http://192.168.1.55:8080/yuyi/message/findList.do
//    http://192.168.1.55:8080/yuyi/message/findList.do?token=6DD620E22A92AB0AED590DB66F84D064
//    Method:GET
//    参数列表:
//    参数名    |类型    |必需    |描述
//    token    String  Y       令牌

    public void getMessage() {
        Map<String,String>mp=new HashMap<>();
        mp.put("token", user.token);
        okhttp.getCall(Ip.url+Ip.interface_getPushMsg,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                        resStr=response.body().string();
                Log.i("获取最新10条消息---",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
