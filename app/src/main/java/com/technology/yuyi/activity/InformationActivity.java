package com.technology.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.InformationListViewAdapter;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ListView mInformationListView;
    private InformationListViewAdapter mInformationAdapter;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        initView();
    }

    /**
     * 初始化数据
     */
    public void initView() {
        //资讯adapter
        mInformationListView = (ListView) findViewById(R.id.information_listview_id);
        mInformationAdapter = new InformationListViewAdapter(this);
        mInformationListView.setAdapter(mInformationAdapter);
        //跳转资讯详情
        mInformationListView.setOnItemClickListener(this);
        //返回键
        mBack = (ImageView) findViewById(R.id.information_back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this, InformationDetailsActivity.class));
    }
}
