package com.technology.yuyi.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.My_messageListView_Adapter;
import com.technology.yuyi.lzh_utils.MyActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/2.
 */

public class My_message_Activity extends MyActivity{
    private ListView my_message_listview;
    private My_messageListView_Adapter adapter;
    private int[]imgId={R.mipmap.msg1,R.mipmap.msg2,R.mipmap.msg3};
    private String[]title={"宇医公告","血压测量","挂号通知"};
    private String[]time={"12:20","10:10","08:36"};
    private String[]info={"更新通知：你已经很久没看我了，最近我有很多好玩的新功能哦，快来更新吧～",
            "测量通知：您当前测量结果是：高压／109mmHg，低压／74mmHg,血压水平正常","挂号成功：LIM，2月22日上午，涿州市中医院-呼吸外科-李美丽医师，请准时就诊"};
    private String[]type={"0","1","0"};
    private List<Map<String,String>>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        initVIew();
        initData();//测试用
    }

    private void initData() {
        list=new ArrayList<>();
        for (int i=0;i<3;i++){
            Map<String,String>mp=new HashMap<>();
            mp.put("image",imgId[i]+"");
            mp.put("title",title[i]);
            mp.put("time",time[i]);
            mp.put("info",info[i]);
            mp.put("type",type[i]);
            list.add(mp);
        }
        adapter=new My_messageListView_Adapter(My_message_Activity.this,list);
        my_message_listview.setAdapter(adapter);
    }

    private void initVIew() {
        titleTextView= (TextView) findViewById(R.id.activity_include_title);
        titleTextView.setText("消息");
        my_message_listview= (ListView) findViewById(R.id.my_message_listview);


    }
}
