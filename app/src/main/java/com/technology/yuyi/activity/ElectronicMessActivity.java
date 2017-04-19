package com.technology.yuyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.ElectronicMessListViewAdapter;
import com.technology.yuyi.adapter.FamilyUserEleListAdapter;
import com.technology.yuyi.bean.bean_FamilyUserEle;
import com.technology.yuyi.bean.bean_MedicalRecordList;
import com.technology.yuyi.lzh_utils.Ip;
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
//电子病历列表页面

//intent.putExtra("type","1");
//        intent.putExtra("id",""+userInfo.getId());
public class ElectronicMessActivity extends AppCompatActivity implements View.OnClickListener{
private ImageView mBack;
    private MyEmptyListView mLisview;
    private String type;//0用户的电子病历，1家人的电子病历
    private String Fid;//家人用户的id
    private ElectronicMessListViewAdapter mAdapter;
    private View mHeaderView;
    private String resStr;
    private List<bean_FamilyUserEle.ResultBean>listFamilyUser;
    private FamilyUserEleListAdapter adapter;
    private List<bean_MedicalRecordList.ResultBean> list;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(ElectronicMessActivity.this);
                    mLisview.setError();
                    break;
                case 1:
                    try{
                        list=new ArrayList<>();
                        mAdapter=new ElectronicMessListViewAdapter(ElectronicMessActivity.this,list);
                        mLisview.setAdapter(mAdapter);
                        bean_MedicalRecordList recordList= gson.gson.fromJson(resStr,bean_MedicalRecordList.class);
                        if (recordList!=null){
                            if ("0".equals(recordList.getCode())){
                                if (recordList.getResult()!=null&&recordList.getResult().size()>0){
                                    list.addAll(recordList.getResult());
                                    adapter.notifyDataSetChanged();
                                    mLisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                if (position!=0){
                                                    Intent intent=new Intent();
                                                    intent.setClass(ElectronicMessActivity.this,LookElectronicMessActivity.class);
                                                    intent.putExtra("id",list.get(position-1).getId());
                                                    intent.putExtra("type","0");
                                                    startActivity(intent);
                                                }
                                        }
                                    });
                                }
                                else {
                                    toast.toast_gsonEmpty(ElectronicMessActivity.this);
                                }
                            }
                         else{
                                Toast.makeText(ElectronicMessActivity.this,"加载失败：",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            toast.toast_gsonEmpty(ElectronicMessActivity.this);
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(ElectronicMessActivity.this);
                        e.printStackTrace();
                    }
                    mLisview.setEmpty();
                    break;
                case 2://获取家庭用户的所有病历
                    try{
                        listFamilyUser=new ArrayList<>();
                        adapter=new FamilyUserEleListAdapter(ElectronicMessActivity.this,listFamilyUser);
                        mLisview.setAdapter(adapter);
                        bean_FamilyUserEle us=gson.gson.fromJson(resStr,bean_FamilyUserEle.class);
                        if ("0".equals(us.getCode())){
                            if (us.getResult()!=null&&us.getResult().size()>0){
                                listFamilyUser.addAll(us.getResult());
                                adapter.notifyDataSetChanged();
                                mLisview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if (position!=0){
                                            Intent intent=new Intent();
                                            intent.setClass(ElectronicMessActivity.this,LookElectronicMessActivity.class);
                                            intent.putExtra("id",listFamilyUser.get(position-1).getId());
                                            intent.putExtra("type","1");
                                            startActivity(intent);
                                        }
                                    }
                                });
                            }
                            else{
                                toast.toast_gsonEmpty(ElectronicMessActivity.this);
                            }
                        }
                        else {
                            Toast.makeText(ElectronicMessActivity.this,"电子病历获取失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        toast.toast_faild(ElectronicMessActivity.this);
                    }
                    mLisview.setEmpty();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_mess);
        initView();
        type=getIntent().getStringExtra("type");
        Fid=getIntent().getStringExtra("id");
        if ("0".equals(type)){
            getMsg();
        }
           else if ("1".equals(type)) {
            getFamilyUserMsg(Fid);//获取家人的电子病历列表
        }
    }
    public void initView(){
        //返回
        mBack= (ImageView) findViewById(R.id.elec_back);
        mBack.setOnClickListener(this);
        //电子病历数据
        mLisview= (MyEmptyListView) findViewById(R.id.elec_listview);
//        mHeaderView= LayoutInflater.from(this).inflate(R.layout.elec_header,null);
//        mLisview.addHeaderView(mHeaderView);
        list=new ArrayList<>();
        mAdapter=new ElectronicMessListViewAdapter(ElectronicMessActivity.this,list);
        mLisview.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }
    }
    //获取所有电子病历
    public void getMsg(){
        Map<String,String>mp=new HashMap<>();
        mp.put("token", user.token);
        okhttp.getCall(Ip.url+Ip.interface_medicalRecordList,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("电子病历列表---",resStr);
                    handler.sendEmptyMessage(1);
            }
        });

    }
    //获取家人电子病历http://localhost:8080/yuyi/medical/homeuserMedicalTime.do?id=1
    public void getFamilyUserMsg(String ids) {
        Map<String,String>mp=new HashMap<>();
        mp.put("id",ids);
        okhttp.getCall(Ip.url_F+Ip.interface_famiUserEleList,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("获取家庭用户电子病历----"+Fid,resStr);
                handler.sendEmptyMessage(2);
            }
        });

    }
}
