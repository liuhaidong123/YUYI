package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.My_shoppingCart_Adapter;
import com.technology.yuyi.lzh_utils.MyListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/2/28.
 */
//购物车
public class My_shoppingCart_Activity extends Activity {
    private RelativeLayout my_shoppingcart_rela_empty;//购物车空时显示的view
    private LinearLayout my_shoppingcart_rela_full;//购物车有货时显示的view
    private MyListView my_shoppingcart_listview;
    private My_shoppingCart_Adapter adapter;
    private List<Map<String,String>>list;
    private Handler hanler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==0){
                my_shoppingcart_rela_empty.setVisibility(View.GONE);
                my_shoppingcart_rela_full.setVisibility(View.VISIBLE);
                initData();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shoppingcart);
        initView();

    }

    private void initData() {
        list=new ArrayList<>();
        for (int i=0;i<10;i++){
            Map<String,String>mp=new HashMap<>();
            mp.put("image",""+R.mipmap.testdrug);
            list.add(mp);
        }
        adapter=new My_shoppingCart_Adapter(My_shoppingCart_Activity.this,list);
        my_shoppingcart_listview.setAdapter(adapter);

    }

    private void initView() {
        my_shoppingcart_rela_empty= (RelativeLayout) findViewById(R.id.my_shoppingcart_rela_empty);
        my_shoppingcart_rela_full= (LinearLayout) findViewById(R.id.my_shoppingcart_rela_full);
        my_shoppingcart_listview= (MyListView) findViewById(R.id.my_shoppingcart_listview);


        my_shoppingcart_rela_empty.setVisibility(View.VISIBLE);
        my_shoppingcart_rela_full.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    hanler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //购物车空时，点击购买进入商城首页
    public void shopping(View view) {
        if (view!=null){
            if (view.getId()==R.id.my_shoppingcart_textV_shopping){
                startActivity(new Intent(My_shoppingCart_Activity.this,MS_home_Activity.class));
            }
        }
    }
}
