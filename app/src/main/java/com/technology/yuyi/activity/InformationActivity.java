package com.technology.yuyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.InformationListViewAdapter;
import com.technology.yuyi.bean.FirstPageInformationTwoData;
import com.technology.yuyi.bean.FirstPageInformationTwoDataRoot;

import java.util.ArrayList;
import java.util.List;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mInformationListView;
    private InformationListViewAdapter mInformationAdapter;
    private List<FirstPageInformationTwoData> mList = new ArrayList<>();
    private ImageView mBack;
    private SwipeRefreshLayout mSwipeLayout;//刷新
    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 22) {
                Object o = msg.obj;
                if (o != null && o instanceof FirstPageInformationTwoDataRoot) {
                    FirstPageInformationTwoDataRoot root = (FirstPageInformationTwoDataRoot) o;
                    mList = root.getRows();
                    mInformationAdapter.setList(mList);
                    mInformationAdapter.notifyDataSetChanged();
                }
            }

        }
    };

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
        //获取资讯页面数据
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getFirstPageInformationTwoData(handler);

        //刷新
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refreshlayout_id);
        mSwipeLayout.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(InformationActivity.this, "刷新完成", Toast.LENGTH_SHORT).show();
                        mHttptools.getFirstPageInformationTwoData(handler);
                        mSwipeLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        //资讯adapter
        mInformationListView = (ListView) findViewById(R.id.information_listview_id);
        mInformationAdapter = new InformationListViewAdapter(this, mList);
        mInformationListView.setAdapter(mInformationAdapter);
        //跳转资讯详情
        mInformationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InformationActivity.this, InformationDetailsActivity.class);
                intent.putExtra("id", mList.get(position).getId());//将医院的id传过去
                Log.e("id=",mList.get(position).getId()+"");
                startActivity(intent);
            }
        });
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

}
