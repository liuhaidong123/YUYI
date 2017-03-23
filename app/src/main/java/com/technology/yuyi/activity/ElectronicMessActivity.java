package com.technology.yuyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.technology.yuyi.bean.bean_MedicalRecordList;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElectronicMessActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
private ImageView mBack;
    private ListView mLisview;
    private ElectronicMessListViewAdapter mAdapter;
    private View mHeaderView;
    private String resStr;
    private List<bean_MedicalRecordList.ResultBean> list;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(ElectronicMessActivity.this);
                    break;
                case 1:
                    try{
                        bean_MedicalRecordList recordList= gson.gson.fromJson(resStr,bean_MedicalRecordList.class);
                        if (recordList!=null){
                            if ("0".equals(recordList.getCode())){
                                list=recordList.getResult();
                                if (list!=null&&list.size()>0){
                                    mAdapter=new ElectronicMessListViewAdapter(ElectronicMessActivity.this,list);
                                    mLisview.setAdapter(mAdapter);
                                    mLisview.setOnItemClickListener(ElectronicMessActivity.this);
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
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_mess);
        initView();
        getMsg();
    }

    public void initView(){
        //返回
        mBack= (ImageView) findViewById(R.id.elec_back);
        mBack.setOnClickListener(this);

        //电子病历数据
        mLisview= (ListView) findViewById(R.id.elec_listview);
        mHeaderView= LayoutInflater.from(this).inflate(R.layout.elec_header,null);
        mLisview.addHeaderView(mHeaderView);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0){
            Intent intent=new Intent();
            intent.setClass(ElectronicMessActivity.this,LookElectronicMessActivity.class);
            intent.putExtra("id",list.get(position-1).getId());
            startActivity(intent);
        }
    }
    //获取所有电子病历
    public void getMsg(){
        Map<String,String>mp=new HashMap<>();
        mp.put("token","1EE359830D68AF676396B06029CCFA61");
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
}
