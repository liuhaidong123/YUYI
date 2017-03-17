package com.technology.yuyi.activity;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MS_allkinds_ExAdapter;
import com.technology.yuyi.adapter.MS_allkinds_MyGridViewAdapter;
import com.technology.yuyi.adapter.MS_allkindssort_Adapter;
import com.technology.yuyi.adapter.MS_home_DailyGridViewAdapter;
import com.technology.yuyi.bean.bean_MS_allkinds;
import com.technology.yuyi.bean.bean_MS_allkinds_alldrugs;
import com.technology.yuyi.lzh_utils.Intent_Code;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyExpanListview;
import com.technology.yuyi.lzh_utils.MyGridView;
import com.technology.yuyi.lzh_utils.MyIntent;
import com.technology.yuyi.lzh_utils.MybottomScro;
import com.technology.yuyi.lzh_utils.conn;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//所有药品页面
//显示选中类别的药品Intent intent=new Intent();intent.putInt("type",1);type:药品类别：0全部
/**
 * Created by wanyu on 2017/2/27.
 */

public class MS_allkinds_activity extends Activity implements MS_allkinds_ExAdapter.childSelectListener{
    private String TAG=getClass().getSimpleName();

    private ImageView ms_allkinds_image_sort;
    private TextView ms_allkinds_textV_name;//当前的分类
    private List<String> listKinds;


    private List<Map<String,String>>list;//adapter数据源
    private int[] Id={R.mipmap.c1,R.mipmap.c2,R.mipmap.c3,R.mipmap.z1,R.mipmap.z2,R.mipmap.z3};

    private  RelativeLayout activity_ms_allkinds_rela;//切换药品与分类的按钮

    //以下时所有药品分类相关数据
    private LinearLayout activity_my_allkinds;//所有药品的布局
//-------------------------------------------------------------------------------------------
    private int SelecedDrugId;
    private MyExpanListview ms_allkinds_expandlist;
    private List<bean_MS_allkinds.CategoryBean>listK;//存放所有分类的view
    private MS_allkinds_ExAdapter exAdapter;
    private String resultStr;//请求结果string


    //-----------------------------------------------------

    private List<bean_MS_allkinds_alldrugs.RowsBean>listAlldrgus;//所有药品的数据源
    private MyGridView ms_allkinds_myGridview;
    private MS_allkinds_MyGridViewAdapter adapter;
    //----------------------------------------------------
    private int type;//0：查询全部药品，1：查询大类，2：小类
    private int cId;//类别的id
    private String Cname;//当前大类的名字
    //处理加载更多--------
    private int startIndex;//记录当前请求的开始下标
//    private int type;//当前请求的是哪个接口的药品数据0：全部药品，1小类药品，2大类药品
    private RelativeLayout ms_allkinds_bottom_loading;//加载更多的view
    private MybottomScro ms_allkinds_scrollview;//scrow
    private LinearLayout ms_allkinds_allkinds;
    private ProgressBar ms_allkinds_progress;
    private TextView ms_allkinds_loadingText;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://请求失败：网络异常
                    toast.toast_faild(MS_allkinds_activity.this);
                    break;
                case 1://请求全部分类成功
                    try{
                        bean_MS_allkinds allkinds=gson.gson.fromJson(resultStr,bean_MS_allkinds.class);
                        listK=allkinds.getCategory();
                        if (listK!=null&&listK.size()>0){
                            exAdapter=new MS_allkinds_ExAdapter(MS_allkinds_activity.this,listK,MS_allkinds_activity.this);
                            ms_allkinds_expandlist.setAdapter(exAdapter);
                            ms_allkinds_expandlist.setGroupIndicator(null);
                            ms_allkinds_expandlist.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                                @Override
                                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                    getDrugsLarge(Integer.parseInt(listK.get(groupPosition).getId()),0,10);//0-10tiao
                                    ms_allkinds_textV_name.setText(listK.get(groupPosition).getName());
                                    activity_my_allkinds.setVisibility(View.GONE);
                                    ms_allkinds_allkinds.setVisibility(View.VISIBLE);
                                    ms_allkinds_image_sort.setSelected(false);
                                    return true;
                                }
                            });
                            for(int i = 0; i < exAdapter.getGroupCount(); i++){
                                ms_allkinds_expandlist.expandGroup(i,false);
                            }
                        }
                        else {
                            toast.toast_gsonEmpty(MS_allkinds_activity.this);
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MS_allkinds_activity.this);
                        Log.e(TAG,e.toString());
                    }
                    break;
                case 2://请求全部药品
                    try{
                        bean_MS_allkinds_alldrugs allDrug=gson.gson.fromJson(resultStr,bean_MS_allkinds_alldrugs.class);
                        listAlldrgus=allDrug.getRows();
                        if (listAlldrgus!=null&&listAlldrgus.size()>0){
                            startIndex=listAlldrgus.size()+startIndex-1;
                            adapter=new MS_allkinds_MyGridViewAdapter(MS_allkinds_activity.this,listAlldrgus);
                            ms_allkinds_myGridview.setAdapter(adapter);
                            ms_allkinds_myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(MS_allkinds_activity.this,""+position,Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent();
                                    intent.setClass(MS_allkinds_activity.this,MS_drugInfo_activity.class);
                                    intent.putExtra(MyIntent.intent_MS_drugInfo,listAlldrgus.get(position).getId());
                                    startActivity(intent);
                                }
                            });
                            ms_allkinds_bottom_loading.setVisibility(View.VISIBLE);
                        }
                        else {
                            toast.toast_gsonEmpty(MS_allkinds_activity.this);
                        }

                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MS_allkinds_activity.this);
                    }

                    break;
                case 3://请求小类药品成功
                    try{
                        bean_MS_allkinds_alldrugs allDrug=gson.gson.fromJson(resultStr,bean_MS_allkinds_alldrugs.class);
                        listAlldrgus.addAll(allDrug.getRows());
                        if (listAlldrgus!=null&&listAlldrgus.size()>0){
                            startIndex=listAlldrgus.size()+startIndex-1;
                            adapter=new MS_allkinds_MyGridViewAdapter(MS_allkinds_activity.this,listAlldrgus);
                            ms_allkinds_myGridview.setAdapter(adapter);
                            ms_allkinds_bottom_loading.setVisibility(View.VISIBLE);
                            ms_allkinds_myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(MS_allkinds_activity.this,""+position,Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent();
                                    intent.setClass(MS_allkinds_activity.this,MS_drugInfo_activity.class);
                                    intent.putExtra(MyIntent.intent_MS_drugInfo,listAlldrgus.get(position).getId());
                                    startActivity(intent);
                                }
                            });
                            ms_allkinds_bottom_loading.setVisibility(View.VISIBLE);
                        }
                        else {
                            toast.toast_gsonEmpty(MS_allkinds_activity.this);
                            adapter=new MS_allkinds_MyGridViewAdapter(MS_allkinds_activity.this,listAlldrgus);
                            ms_allkinds_myGridview.setAdapter(adapter);
                        }

                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MS_allkinds_activity.this);
                    }
                    break;
                case 4://获取大类药品
                    try{
                        bean_MS_allkinds_alldrugs allDrug=gson.gson.fromJson(resultStr,bean_MS_allkinds_alldrugs.class);
                        listAlldrgus=allDrug.getRows();
                        if (listAlldrgus!=null&&listAlldrgus.size()>0){
                            startIndex=listAlldrgus.size()+startIndex-1;
                            adapter=new MS_allkinds_MyGridViewAdapter(MS_allkinds_activity.this,listAlldrgus);
                            ms_allkinds_myGridview.setAdapter(adapter);
                            ms_allkinds_bottom_loading.setVisibility(View.VISIBLE);
                            ms_allkinds_myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Toast.makeText(MS_allkinds_activity.this,""+position,Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent();
                                    intent.setClass(MS_allkinds_activity.this,MS_drugInfo_activity.class);
                                    intent.putExtra(MyIntent.intent_MS_drugInfo,listAlldrgus.get(position).getId());
                                    startActivity(intent);
                                }
                            });

                        }
                        else {
                            toast.toast_gsonEmpty(MS_allkinds_activity.this);
                            adapter=new MS_allkinds_MyGridViewAdapter(MS_allkinds_activity.this,listAlldrgus);
                            ms_allkinds_myGridview.setAdapter(adapter);
                            activity_my_allkinds.setVisibility(View.GONE);
                        }

                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MS_allkinds_activity.this);
                        activity_my_allkinds.setVisibility(View.GONE);
                    }
                    break;
                case 5://加载更多返回的数据
                    try{
                        ms_allkinds_bottom_loading.setClickable(true);
                        bean_MS_allkinds_alldrugs drugsMore=gson.gson.fromJson(resultStr,bean_MS_allkinds_alldrugs.class);
                        ms_allkinds_loadingText.setText("点击加载更多");
                        ms_allkinds_progress.setVisibility(View.GONE);
                        if (drugsMore.getRows()!=null&&drugsMore.getRows().size()>0){
                            startIndex=startIndex+drugsMore.getRows().size();
                            listAlldrgus.addAll(drugsMore.getRows());
                            adapter.notifyDataSetChanged();
                            ms_allkinds_bottom_loading.setVisibility(View.VISIBLE);
                        }
                        else {
                            ms_allkinds_bottom_loading.setVisibility(View.GONE);
                        }

                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MS_allkinds_activity.this);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_allkinds);
        initView();
        activity_my_allkinds= (LinearLayout) findViewById(R.id.activity_my_allkinds);
        listK=new ArrayList<>();
        type = getIntent().getIntExtra(MyIntent.intent_MS_allkinds_type, -1);
        cId = getIntent().getIntExtra(MyIntent.intent_MS_allkinds_id, -1);
        Cname = getIntent().getStringExtra(MyIntent.intent_MS_allkinds_name);//大类的名字
        ms_allkinds_textV_name.setText(Cname);
        switch (type) {
            case Intent_Code.code_MS_allkinds_typeAll://查询全部
                getAllDrugs(0,10);
                break;
            case Intent_Code.code_MS_allkinds_typeSmall://查询小类
                getDrugsSmall(cId, 0, 10);
                break;
            case Intent_Code.code_MS_allkinds_typeLarge://查询大类
                getDrugsLarge(cId, 0, 10);
                break;
        }
        }

    @Override
    protected void onStart() {
        super.onStart();
        //        type;//0：查询全部药品，1：查询小类，2：大类

    }
    private void initView() {
        ms_allkinds_allkinds= (LinearLayout) findViewById(R.id.ms_allkinds_allkinds);
        ms_allkinds_bottom_loading= (RelativeLayout) findViewById(R.id.ms_allkinds_bottom_loading);
        ms_allkinds_bottom_loading.setVisibility(View.GONE);
        ms_allkinds_expandlist= (MyExpanListview) findViewById(R.id.ms_allkinds_expandlist);
        ms_allkinds_image_sort= (ImageView) findViewById(R.id.ms_allkinds_image_sort);
        ms_allkinds_scrollview= (MybottomScro) findViewById(R.id.ms_allkinds_scrollview);
        ms_allkinds_textV_name= (TextView) findViewById(R.id.ms_allkinds_textV_name);
        ms_allkinds_myGridview= (MyGridView) findViewById(R.id.ms_allkinds_myGridview);

        ms_allkinds_bottom_loading.setVisibility(View.GONE);
        ms_allkinds_progress= (ProgressBar) findViewById(R.id.ms_allkinds_progress);
        ms_allkinds_loadingText= (TextView) findViewById(R.id.ms_allkinds_loadingText);
        ms_allkinds_loadingText.setText("点击加载更多");
        ms_allkinds_progress.setVisibility(View.GONE);

        ms_allkinds_bottom_loading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ms_allkinds_bottom_loading.setClickable(false);
                getMoreDrugs(type,startIndex);
            }
        });

    }

    public void getAllKinds(View view) {
        ms_allkinds_bottom_loading.setVisibility(View.GONE);
        if (view.getId()==R.id.ms_allkinds_relative_allkinds){
            if (ms_allkinds_image_sort.isSelected()){//当前显示的时全部分类
                ms_allkinds_bottom_loading.setVisibility(View.VISIBLE);
                activity_my_allkinds.setVisibility(View.GONE);
                ms_allkinds_allkinds.setVisibility(View.VISIBLE);
                ms_allkinds_image_sort.setSelected(false);

            }
            else {
                activity_my_allkinds.setVisibility(View.VISIBLE);
                ms_allkinds_allkinds.setVisibility(View.GONE);
                ms_allkinds_image_sort.setSelected(true);
                getKinds();//请求获取全部分类
                }
        }
    }

    //药品详情页面
    public void getDrugInfo(int drugId){
        Intent intent=new Intent();
        intent.setClass(MS_allkinds_activity.this,MS_drugInfo_activity.class);
        intent.putExtra(MyIntent.intent_MS_drugInfo,drugId);
        startActivity(intent);
    }

    public void goBack(View view) {
        if (view!=null){
            if (view.getId()==R.id.ms_allkinds_return){
                finish();
            }
        }
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


    @Override
    public void onChildSelect(int ChildId, String ChildName) {

        activity_my_allkinds.setVisibility(View.GONE);
        ms_allkinds_allkinds.setVisibility(View.VISIBLE);
        ms_allkinds_image_sort.setSelected(false);


        ms_allkinds_textV_name.setText(ChildName);
        ms_allkinds_myGridview.setVisibility(View.VISIBLE);
        getDrugsSmall(ChildId,0,10);
    }


//     获取某小类下的药品//        http://192.168.1.42:8080/yuyi/drugs/getcid2.do?start=0&limit=10&cid2=14
    public void getDrugsSmall(int drugId,int start,int limit){//获取小类药品，从全部分类返回小类大id
        listAlldrgus=new ArrayList<>();
        cId=drugId;
        startIndex=0;
        type=1;
        Map<String,String>mp=new HashMap<>();
        mp.put("start",0+"");  mp.put("limit",10+"");  mp.put("cid2",drugId+"");
        okhttp.getCall(Ip.url_F+Ip.interface_MS_smallDrugs,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resultStr=response.body().string();
                    handler.sendEmptyMessage(3);//获取小类药品成功
                    Log.i("获取全部小类药品---",resultStr.toString());
            }
        });
    }

    //获取某一大类的药品start=0&limit=10&cid1=1
    public void getDrugsLarge(int drugId,int start ,int limit){//获取大类药品(大类大id)
        listAlldrgus=new ArrayList<>();
        cId=drugId;
        startIndex=0;
        type=2;
        Map<String,String>mp=new HashMap<>();
        mp.put("start",start+"");  mp.put("limit",limit+"");  mp.put("cid1",drugId+"");
        okhttp.getCall(Ip.url_F+Ip.interface_MS_largeDrugs,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resultStr=response.body().string();
                handler.sendEmptyMessage(4);//获取小类药品成功
                Log.i("获取某一大类药品---",resultStr.toString());
            }
        });
    }

    //获取全部药品
    public void getAllDrugs(int start,int limt) {
        listAlldrgus=new ArrayList<>();
//        start=0&limit=10
        cId=-1;
        startIndex=0;
        type=0;
        Map<String,String>mp=new HashMap<>();
        mp.put("start",start+"");
        mp.put("limit",limt+"");
        Log.i(TAG,"获取全部药品--");
        okhttp.getCall(Ip.url_F+Ip.interface_MS_home_allDrugs,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
                Log.e(TAG,e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resultStr=response.body().string();
                handler.sendEmptyMessage(2);
                Log.i(TAG+"----",resultStr.toString());
            }
        });
    }

    public void getMoreDrugs(int type,int start){//每次多取10个
        ms_allkinds_loadingText.setText("正在加载");
        ms_allkinds_progress.setVisibility(View.VISIBLE);
        Map<String,String>mp=new HashMap<>();
        String url="";
            switch (type) {
                case Intent_Code.code_MS_allkinds_typeAll://查询全部
                    mp.put("start",(start+10)+"");
                    mp.put("limit",(10)+"");
                    Log.i(TAG,"获取全部药品--");
                    url=Ip.url_F+Ip.interface_MS_home_allDrugs;
                    break;
                case Intent_Code.code_MS_allkinds_typeSmall://查询小类
                    mp.put("start",(start+10)+"");
                    mp.put("limit",10+"");
                    mp.put("cid2",cId+"");
                    url=Ip.url_F+Ip.interface_MS_smallDrugs;
                    break;
                case Intent_Code.code_MS_allkinds_typeLarge://查询大类
                    mp.put("start",(start+10)+"");  mp.put("limit",10+"");
                    mp.put("cid1",cId+"");
                    url=Ip.url_F+Ip.interface_MS_largeDrugs;
                    break;
        }
        okhttp.getCall(url,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resultStr=response.body().string();
                handler.sendEmptyMessage(5);
                Log.i("获取跟多药品---",resultStr.toString());
            }
        });
    }
}
