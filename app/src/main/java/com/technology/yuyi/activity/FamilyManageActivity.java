package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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
import com.technology.yuyi.lzh_utils.MyEmptyListView;
import com.technology.yuyi.lzh_utils.UserInfo;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//家庭用户列表
public class FamilyManageActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private MyEmptyListView mLisView;
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
                    mLisView.setError();
                    break;
                case 1:
                try
                    {
                    bean_ListFamilyUser user= gson.gson.fromJson(resStr,bean_ListFamilyUser.class);
                    if ("0".equals(user.getCode())){
                        if (user.getResult()!=null&&user.getResult().size()>0){
                            list.addAll(user.getResult());
                            mAdapter.notifyDataSetChanged();
                            }
                        }
                    else {
                        //Toast.makeText(FamilyManageActivity.this,"获取家庭用户成员失败",Toast.LENGTH_SHORT).show();
                        }
                }
                catch (Exception e){
                    toast.toast_gsonFaild(FamilyManageActivity.this);
                    Log.e("json--",e.toString());
                }
                    mLisView.setEmpty();
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
        mLisView = (MyEmptyListView) findViewById(R.id.family_listview);
        mLisView.setOnItemClickListener(this);
        //添加用户
        mAddFamily = (TextView) findViewById(R.id.family_add);
        mAddFamily.setOnClickListener(this);

        //返回
        mBack = (ImageView) findViewById(R.id.family_back);
        mBack.setOnClickListener(this);


        list=new ArrayList<>();
        mAdapter=new FamilyManageListViewAdapter(FamilyManageActivity.this,list);
        mLisView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        //添加
        if (id == mAddFamily.getId()) {
            if (list!=null&&list.size()>0){
                if (UserInfo.isUserInfoCompletion(list.get(0).getTrueName(),list.get(0).getAge()+"",list.get(0).getGender()+"")){
                    Intent intent = new Intent(this, AddFamilyUserActivity.class);
                    intent.putExtra("title", "添加家庭用户");
                    startActivity(intent);
                    //返回
                }
                else {
                    AlertDialog dialog=new AlertDialog.Builder(FamilyManageActivity.this).setTitle("无法添加")
                            .setMessage("您的个人信息不完整，需要您填写完整的信息后才能添加家庭用户").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent();
                                    intent.putExtra("type","0");
                                    intent.setClass(FamilyManageActivity.this,UserEditorActivity.class);
                                    startActivity(intent);
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            }

          else {
                Toast.makeText(FamilyManageActivity.this,"获取用户信息失败，无法添加",Toast.LENGTH_SHORT).show();
            }
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
