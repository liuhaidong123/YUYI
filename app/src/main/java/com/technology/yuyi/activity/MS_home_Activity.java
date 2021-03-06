package com.technology.yuyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MS_home_ExAdapter;
import com.technology.yuyi.adapter.MS_home_GridViewAdapter;
import com.technology.yuyi.bean.bean_MS_home;
import com.technology.yuyi.bean.bean_MyDrugState;
import com.technology.yuyi.lzh_utils.Intent_Code;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.MyExpanListview;
import com.technology.yuyi.lzh_utils.MyGridView;
import com.technology.yuyi.lzh_utils.MyIntent;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MS_home_Activity extends AppCompatActivity {
    private final String TAG=getClass().getSimpleName();
    private MyGridView ms_home_gridview;//药品种类展示的view
    private MS_home_GridViewAdapter adapter;

    private List<bean_MS_home.CategoryBean>listCategory;//分类（大类）
    private List<bean_MS_home.DrugsBean>listDrugs;//获取到的所有药品
    private MyExpanListview ms_home_exlistview;
    private List<Map<String,String>>listCat;//大类的集合
    private String resultStr;
    private RelativeLayout ms_home_ms_state;//wode yaopin zhuangtai
    private List<Map<String,Object>>listInfo;//存放expanlistview数据源

    private TextView ms_home_myDrugState_title,ms_home_myDrugState_nowState,ms_home_myDrugState_time;//我的药品状态标题,当前状态，创建时间
    private bean_MyDrugState MyDrugState;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://请求失败：网络异常，服务器异常
                    MyDialog.stopDia();
                    toast.toast_faild(MS_home_Activity.this);
                    break;
                case 1://查询首页信息返回的数据
                    MyDialog.stopDia();
                    try{
                        bean_MS_home homeSource=gson.gson.fromJson(resultStr,bean_MS_home.class);
                        listCategory=homeSource.getCategory();
                        listDrugs=homeSource.getDrugs();
                        final List<Integer>listId=new ArrayList<>();//存放所有大类的id
                        if (listCategory!=null&&listCategory.size()>0&&listDrugs!=null&&listDrugs.size()>0){
                            listCat=new ArrayList<>();
                            for (int i=0;i<listCategory.size();i++){
                                Map<String,String>mp=new HashMap<>();
                                mp.put("name",listCategory.get(i).getName());
                                mp.put("id",listCategory.get(i).getId()+"");
                                listCat.add(mp);

                                listId.add(listCategory.get(i).getId());
                            }
                            Map<String,String>mp=new HashMap<>();
                            mp.put("name","全部");
                            mp.put("id","-1");
                            listCat.add(mp);
                            adapter=new MS_home_GridViewAdapter(listCat,MS_home_Activity.this);
                            ms_home_gridview.setAdapter(adapter);
                            ms_home_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent=new Intent();
                                    intent.setClass(MS_home_Activity.this,MS_allkinds_activity.class);
                                    if (position==adapter.getCount()-1){
                                        intent.putExtra(MyIntent.intent_MS_allkinds_type,Intent_Code.code_MS_allkinds_typeAll);
                                        String ids=listCat.get(position).get("id");
                                        intent.putExtra(MyIntent.intent_MS_allkinds_name,listCat.get(position).get("name"));
                                        intent.putExtra(MyIntent.intent_MS_allkinds_id,Integer.parseInt(listCat.get(position).get("id")));
                                    }
                                    else {
                                        intent.putExtra(MyIntent.intent_MS_allkinds_type,Intent_Code.code_MS_allkinds_typeLarge);
                                        intent.putExtra(MyIntent.intent_MS_allkinds_name,listCat.get(position).get("name"));
                                        String ids=listCat.get(position).get("id");
                                        intent.putExtra(MyIntent.intent_MS_allkinds_id,Integer.parseInt(listCat.get(position).get("id")));
                                    }
                                    startActivity(intent);
                                }
                            });
                            }
                        else {
                            toast.toast_gsonEmpty(MS_home_Activity.this);
                        }
                            listInfo=new ArrayList<>();
                        for (int i=0;i<listCategory.size();i++){//大类
                            Map<String,Object>mp=new HashMap<>();
                            mp.put("name",listCategory.get(i).getName());
                            mp.put("id",listCategory.get(i).getId());
                            List<Map<String,String>>lit=new ArrayList<>();
                            for (int j=0;j<listDrugs.size();j++){//药品
                                if (listCategory.get(i).getId()==listDrugs.get(j).getCategoryId1()){
                                   Map<String,String>m=new HashMap<>();
                                    m.put("childname",listDrugs.get(j).getDrugsName());
                                    m.put("childid",listDrugs.get(j).getId()+"");
                                    m.put("childurl",listDrugs.get(j).getPicture());
                                    m.put("childprice",listDrugs.get(j).getPrice()+"");
                                    lit.add(m);
                                }
                            }
                            mp.put("child",lit);
                            listInfo.add(mp);
                        }
                        MS_home_ExAdapter adapter=new MS_home_ExAdapter(listInfo,MS_home_Activity.this);
                        ms_home_exlistview.setGroupIndicator(null);
                        ms_home_exlistview.setAdapter(adapter);
                        ms_home_exlistview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                            @Override
                            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                                Intent intent=new Intent(MS_home_Activity.this,MS_allkinds_activity.class);
                                intent.putExtra(MyIntent.intent_MS_allkinds_type,Intent_Code.code_MS_allkinds_typeLarge);
                                intent.putExtra(MyIntent.intent_MS_allkinds_name,listInfo.get(groupPosition).get("name")+"");
                                intent.putExtra(MyIntent.intent_MS_allkinds_id,Integer.parseInt(listInfo.get(groupPosition).get("id")+""));
                                startActivity(intent);
                                return true;
                            }
                        });
                        for(int i = 0; i < adapter.getGroupCount(); i++){
                            ms_home_exlistview.expandGroup(i,false);
                        }

                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MS_home_Activity.this);
                    }

                    break;
                case 2://我的药品状态返回
                    try{
                        bean_MyDrugState drugState=gson.gson.fromJson(resultStr,bean_MyDrugState.class);
                        if ("0".equals(drugState.getCode())){
                            bean_MyDrugState.ResultBean drugBean=drugState.getResult();
                            if (drugBean!=null){
                                ms_home_ms_state.setVisibility(View.VISIBLE);
                                if (!"".equals(drugBean.getTitle())&&!TextUtils.isEmpty(drugBean.getTitle())){
                                    ms_home_myDrugState_title.setText(drugBean.getTitle());
                                }
                                else {
                                    ms_home_myDrugState_title.setText("我的药品状态");
                                }
                                ms_home_myDrugState_time.setText(drugBean.getCreateTimeString());
                                List<bean_MyDrugState.ResultBean.BoilMedicineListBean>li =drugBean.getBoilMedicineList();
                                if (li!=null&&li.size()>0){
                                    ms_home_myDrugState_nowState.setText("当前状态："+li.get(li.size()-1).getStateText());
                                    MyDrugState=drugState;
                                    }
                                else {
                                    ms_home_ms_state.setClickable(false);
                                }

                            }
                            else {
                                ms_home_ms_state.setVisibility(View.GONE);
                            }
                        }
                        else {
                            ms_home_ms_state.setVisibility(View.GONE);
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MS_home_Activity.this);
                        ms_home_ms_state.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_home);
        initView();
        getMyDrugState();//获取我的药品状态
    }

    //初始化view
    private void initView() {
        ms_home_gridview= (MyGridView) findViewById(R.id.ms_home_gridview);
        ms_home_exlistview= (MyExpanListview) findViewById(R.id.ms_home_exlistview);
        ms_home_ms_state= (RelativeLayout) findViewById(R.id.ms_home_ms_state);
        ms_home_ms_state.setVisibility(View.GONE);
        ms_home_myDrugState_title= (TextView) findViewById(R.id.ms_home_myDrugState_title);
        ms_home_myDrugState_nowState= (TextView) findViewById(R.id.ms_home_myDrugState_nowState);
        ms_home_myDrugState_time= (TextView) findViewById(R.id.ms_home_myDrugState_time);
    }

    //view的点击事件
    public void ms_homeClick(View v){
        if (v!=null){
            switch (v.getId()){
                case R.id.ms_home_return://首页返回按钮
                    finish();
                    break;
                case R.id.ms_home_ms_state://煎药状态
                    if (MyDrugState!=null){
                        Intent intent=new Intent();
                        intent.setClass(MS_home_Activity.this,MS_drugState.class);
                        intent.putExtra("drug",MyDrugState);
                        startActivity(intent);
                    }
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

    //---------------请求数据---------------
    @Override
    protected void onStart() {
        super.onStart();
        if (listCategory!=null&&listCategory.size()>0&&listDrugs!=null&&listDrugs.size()>0){
            return;
        }
      else {
            getHomeSource();//获取首页数据信息
        }
    }

    //获取首页信息数据
    public void getHomeSource() {
        MyDialog.showDialog(MS_home_Activity.this);
        Map<String,String>mp=new HashMap<>();
       okhttp.getCall(Ip.url+Ip.inteface_MS_home_date,mp,okhttp.OK_GET).enqueue(new Callback() {
           @Override
           public void onFailure(Request request, IOException e) {
               handler.sendEmptyMessage(0);
           }
           @Override
           public void onResponse(Response response) throws IOException {
                    resultStr=response.body().string();
                    handler.sendEmptyMessage(1);
               Log.i(TAG,"药品商城首页返回的数据----"+resultStr.toString());
           }
       });
    }

    //搜索按钮
    public void SearchDrugs(View view) {
        Intent inten=new Intent();
        inten.setClass(MS_home_Activity.this,SearchActivity.class);
        inten.putExtra("type","drug");
        startActivity(inten);
    }

    //获取我的药品状态
    public void getMyDrugState() {
        Map<String,String>mp=new HashMap<>();
        mp.put("token", user.token);
        okhttp.getCall(Ip.url+Ip.interface_MyDrugState,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resultStr=response.body().string();
                    Log.i("获取药品状态---",resultStr);
                    handler.sendEmptyMessage(2);
            }
        });
    }
}


