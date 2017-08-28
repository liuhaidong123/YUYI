package com.technology.yuyi.Model;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.bean.BeanDrugStates;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanyu on 2017/8/24.
 */

public class MS_drugStateModel {
    IListener listener;
    String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    try{
                        BeanDrugStates bean= gson.gson.fromJson(resStr,BeanDrugStates.class);
                        listener.onGetMyDrugStates(bean);
                    }
                    catch (Exception e){
                        listener.onGetMyDrugStatesError("数据异常");
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
    public MS_drugStateModel(){

    }

    //获取药品状态http://192.168.1.168:8082/yuyi/prescription/findList2.do?token=97338E8A81C0CC137FC51C6206681EBB
    public void getDrugStates(final IListener lis){
        this.listener=lis;
        Map<String,String> map=new HashMap<>();
        map.put("token", user.token);
        okhttp.getCall(Ip.url+Ip.interface_MyDrugStateList,map,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                listener.onGetMyDrugStatesError("网络异常");
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("获取我的所有药品状态",resStr);
                    handler.sendEmptyMessage(1);
            }
        });
    }


    public interface IListener{
        void onGetMyDrugStates(BeanDrugStates bean);//获取药品状态成功
        void onGetMyDrugStatesError(String msg);//获取药品状态失败
    }
}
