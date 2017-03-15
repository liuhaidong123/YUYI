package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MS_allkinds_ExAdapter;
import com.technology.yuyi.adapter.MS_home_DailyGridViewAdapter;
import com.technology.yuyi.adapter.MS_home_GridViewAdapter;
import com.technology.yuyi.bean.bean_MS_allkinds;
import com.technology.yuyi.lzh_utils.Intent_Code;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyExpanListview;
import com.technology.yuyi.lzh_utils.MyGridView;
import com.technology.yuyi.lzh_utils.MyIntent;
import com.technology.yuyi.lzh_utils.conn;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MS_home_Activity extends AppCompatActivity {
    private final String TAG=getClass().getSimpleName();
    private List<String> listMedicineKind;//药品种类的数据源
    private MyGridView ms_home_gridview;//药品种类展示的view
    private MS_home_GridViewAdapter adapter;


    //常用药的gridview
    private List<bean_MS_allkinds.CategoryBean>listK;//存放所有分类的view
    private String resultStr;//全部分类的返回数据
    private String resultStr_drugs;//具体分类的药品

    private List<String>listDrgus;//获取制定大类的制定药品的数据源，许改动
    private List<Map<String,String>>liKinds;//存放分类，添加了（全部）


    private MyExpanListview ms_home_exlistview;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://请求失败：网络异常，服务器异常
                    toast.toast_faild(MS_home_Activity.this);
                    break;
                case 1://返回的全部分类
                    try{
                        bean_MS_allkinds allkinds= gson.gson.fromJson(resultStr,bean_MS_allkinds.class);
                        listK=allkinds.getCategory();
                        if (listK!=null&&listK.size()>0){
                            liKinds=new ArrayList<>();
                            if (listK.size()>5){//返回的数据大于5条，只取5条
                                for (int i=0;i<4;i++){
                                    Map<String,String>mp=new HashMap<>();
                                    mp.put("name",listK.get(i).getName());
                                    mp.put("id",listK.get(i).getId());
                                    liKinds.add(mp);
                                }
                            }
                            else {//返回到数据小雨等于5条取全部
                                for (int i=0;i<listK.size();i++){
                                    Map<String,String>mp=new HashMap<>();
                                    mp.put("name",listK.get(i).getName());
                                    mp.put("id",listK.get(i).getId());
                                    liKinds.add(mp);
                                }
                            }
                            Map<String,String>mp=new HashMap<>();
                            mp.put("name","全部");
                            mp.put("id","-1");
                            liKinds.add(mp);
                            adapter=new MS_home_GridViewAdapter(liKinds,MS_home_Activity.this);
                            ms_home_gridview.setAdapter(adapter);
                            ms_home_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    if (position==adapter.getCount()-1){
                                        Intent intent=new Intent();
                                        intent.putExtra(MyIntent.intent_MS_allkinds_type, Intent_Code.code_MS_allkinds_typeAll);
                                        intent.putExtra(MyIntent.intent_MS_allkinds_name,"全部");
                                        intent.putExtra(MyIntent.intent_MS_allkinds_id,-1);
                                        intent.setClass(MS_home_Activity.this,MS_allkinds_activity.class);
                                        startActivity(intent);
                                    }
                                    else {
                                        //        type;//0：查询全部药品，1：查询小类，2：大类
//                                        type=getIntent().getIntExtra(MyIntent.intent_MS_allkinds_type,-1);
//                                        cId=getIntent().getIntExtra(MyIntent.intent_MS_allkinds_id,-1);
//                                        Cname=getIntent().getStringExtra(MyIntent.intent_MS_allkinds_name);//大类的名字
                                        Intent intent=new Intent();
                                        intent.putExtra(MyIntent.intent_MS_allkinds_type, Intent_Code.code_MS_allkinds_typeLarge);

                                        intent.putExtra(MyIntent.intent_MS_allkinds_id,Integer.parseInt(liKinds.get(position).get("id")));
                                        intent.putExtra(MyIntent.intent_MS_allkinds_name,liKinds.get(position).get("name"));
//                                        intent.putExtra()
                                        intent.setClass(MS_home_Activity.this,MS_allkinds_activity.class);
                                        startActivity(intent);
                                    }
                                }
                            });

                        }
                        else {
                            toast.toast_gsonEmpty(MS_home_Activity.this);
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MS_home_Activity.this);
                        Log.e(TAG,e.toString());
                    }


                    break;
                case 2:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_home);
        initView();
    }



    //初始化view
    private void initView() {
        ms_home_gridview= (MyGridView) findViewById(R.id.ms_home_gridview);
        ms_home_exlistview= (MyExpanListview) findViewById(R.id.ms_home_exlistview);
//
//        //常用
//        ms_home_gridview_dailyM= (MyGridView) findViewById(R.id.ms_home_gridview_dailyM);//常用药的gridview（只显示1行，3个选项）
//        ms_home_gridview_dailyM.setAdapter(adapterDailly);
//        ms_home_gridview_dailyM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                getDrugInfo();
//            }
//        });
//       //滋补
//        ms_home_gridview_Nu= (MyGridView) findViewById(R.id.ms_home_gridview_Nutritious);//滋补药的gridview（只显示1行，3个选项）
//        ms_home_gridview_Nu.setAdapter(adapterNu);
//        ms_home_gridview_Nu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                getDrugInfo();
//            }
//        });

    }

    //    view的点击事件
    public void ms_homeClick(View v){
        if (v!=null){
            switch (v.getId()){
                case R.id.ms_home_return://首页返回按钮
                    finish();
                    break;
            }
        }

    }


    //药品详情页面
    public void getDrugInfo(){
        Intent intent=new Intent();
        intent.setClass(MS_home_Activity.this,MS_drugInfo_activity.class);
        startActivity(intent);
    }


//获取全部分类的请求
public void getKinds() {
    if (listK!=null&&listK.size()>0){
        return;
    }
    Map<String,String>mp=new HashMap<>();
    okhttp.getCall(Ip.url+Ip.interface_MS_allkinds,mp,okhttp.OK_GET).enqueue(new Callback() {
        @Override
        public void onFailure(Request request, IOException e) {
            resultStr= conn.conn_FAILE;
            handler.sendEmptyMessage(0);
        }
        @Override
        public void onResponse(Response response) throws IOException {
            resultStr=response.body().string();
            handler.sendEmptyMessage(1);
            Log.i(TAG+"-返回数据-",resultStr.toString());
        }
    });
}
    //获取制定个数的大类下的数据
public void getDrug(){
    if (listDrgus!=null&&listDrgus.size()>0){
        return;
    }
}

    //---------------请求数据--------------------------------
    @Override
    protected void onStart() {
        super.onStart();
        if (listK==null){//空的时候请求数据
            getKinds();//获取全部分类

        }
        if (listDrgus==null){
            getDrug(); //获取制定个数的大类下的数据
        }
    }
}


