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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.FamilyManageListViewAdapter;
import com.technology.yuyi.bean.bean_ListFamilyUser;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//家庭用户列表
public class FamilyManageActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView mLisView;
    private FamilyManageListViewAdapter mAdapter;
    private TextView mAddFamily;
    private ImageView mBack;
    private String resStr;
    private List<bean_ListFamilyUser.ResultBean> list;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(FamilyManageActivity.this);
                    break;
                case 1:
                try
                {
                    bean_ListFamilyUser user= gson.gson.fromJson(resStr,bean_ListFamilyUser.class);
                    if ("0".equals(user.getCode())){
                        list=user.getResult();
                        mAdapter=new FamilyManageListViewAdapter(FamilyManageActivity.this,list);
                        mLisView.setAdapter(mAdapter);
                    }
                    else {
                        Toast.makeText(FamilyManageActivity.this,"获取家庭用户成员失败",Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    toast.toast_gsonFaild(FamilyManageActivity.this);
                    Log.e("json--",e.toString());
                }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_manage);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart","");
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserList();
    }

    public void initView() {
        //用户管理listview
        mLisView = (ListView) findViewById(R.id.family_listview);
//        mAdapter = new FamilyManageListViewAdapter(this);
//        mLisView.setAdapter(mAdapter);
        mLisView.setOnItemClickListener(this);
        //添加用户
        mAddFamily = (TextView) findViewById(R.id.family_add);
        mAddFamily.setOnClickListener(this);

        //返回
        mBack = (ImageView) findViewById(R.id.family_back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //添加
        if (id == mAddFamily.getId()) {
            Intent intent = new Intent(this, AddFamilyUserActivity.class);
            intent.putExtra("title", "添加家庭用户");
            startActivity(intent);
            //返回
        } else if (id == mBack.getId()) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        oken=6DD620E22A92AB0AED590DB66F84D064&id=123
        Intent intent=new Intent();
        if (position==0){
            intent.putExtra("type","0");
        }
     else {
            intent.putExtra("type","1");
        }
        intent.setClass(this,FamilyUserMessageActivity.class);
        Bundle b=new Bundle();
        b.putSerializable("family",list.get(position));
        intent.putExtra("family",b);
        startActivity(intent);
    }


    //获取家庭用户列表http://192.168.1.55:8080/yuyi/homeuser/findList.do?token=6DD620E22A92AB0AED590DB66F84D064
    public void getUserList() {
        Map<String,String>mp=new HashMap<>();
//        mp.put("token", user.userPsd);
        mp.put("token",user.userPsd);
        okhttp.getCall(Ip.url+Ip.interfce_ListFamilyUser,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                handler.sendEmptyMessage(1);
                Log.i("获取家庭用户列表--",resStr);
            }
        });
    }


}
