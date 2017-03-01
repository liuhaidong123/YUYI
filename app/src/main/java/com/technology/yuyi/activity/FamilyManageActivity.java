package com.technology.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.FamilyManageListViewAdapter;

public class FamilyManageActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private ListView mLisView;
    private FamilyManageListViewAdapter mAdapter;
    private TextView mAddFamily;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_manage);
        initView();
    }

    public void initView() {
        //用户管理listview
        mLisView = (ListView) findViewById(R.id.family_listview);
        mAdapter = new FamilyManageListViewAdapter(this);
        mLisView.setAdapter(mAdapter);
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
            startActivity(new Intent(this, AddFamilyUserActivity.class));
            //返回
        } else if (id == mBack.getId()) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
      startActivity(new Intent(this,FamilyUserMessageActivity.class));
    }
}
