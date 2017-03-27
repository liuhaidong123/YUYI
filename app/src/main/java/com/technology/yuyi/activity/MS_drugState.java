package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MS_DrugStateAdapter;
import com.technology.yuyi.lzh_utils.MyActivity;
import com.technology.yuyi.lzh_view.MyTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MS_drugState extends MyActivity {
    private MyTextView myTextView;
    private ListView ms_drugstate_listview;
    private List<Map<String,String>> list;
    private int MAX=5;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_drug_state);
        initView();
        initData();
    }

    private void initData() {
        list=new ArrayList<>();
        for (int i=0;i<3;i++){
            Map<String,String>mp=new HashMap<>();

            if (i==2){
                mp.put("isCheck","1");//1选中，0未选中
            }
            else {
                mp.put("isCheck","0");//1选中，0未选中
            }

            String time="";
            String state="";
            switch (i){
                case 0:
                    time="08:00";
                    state="药品清单已接收";
                    break;
                case 1:
                    time="09:00";
                    state="配药已完成";
                    break;
                case 2:
                    time="09:36";
                    state="正在煎药";
                    break;
            }
            mp.put("state",state);
            mp.put("time",time);
            list.add(mp);
        }
        myTextView.setWidth(list.size()*1.0f/MAX);
        ms_drugstate_listview.setAdapter(new MS_DrugStateAdapter(MS_drugState.this,list));
    }

    private void initView() {
        myTextView= (MyTextView) findViewById(R.id.ms_drugstate_myTextView);
        ms_drugstate_listview= (ListView) findViewById(R.id.ms_drugstate_listview);
        title= (TextView) findViewById(R.id.activity_include_title);
        title.setText("我的药品状态");
    }
}
