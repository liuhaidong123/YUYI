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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.InformationListViewAdapter;
import com.technology.yuyi.bean.FirstPageInformationTwoData;
import com.technology.yuyi.bean.FirstPageInformationTwoDataRoot;
import com.technology.yuyi.lhd.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mInformationListView;
    private InformationListViewAdapter mInformationAdapter;
    private List<FirstPageInformationTwoData> mList = new ArrayList<>();
    private ImageView mBack;

    private RelativeLayout mMany_more;
    private ProgressBar mBar;
    private int mStart = 0;
    private int mAddNum = 5;
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
                    List<FirstPageInformationTwoData> list = new ArrayList<>();
                    list = root.getRows();
                    mList.addAll(list);
                    mInformationAdapter.setList(mList);
                    mInformationAdapter.notifyDataSetChanged();
                    mSwipeLayout.setRefreshing(false);
                    ToastUtils.myToast(InformationActivity.this, "刷新成功");
                    mBar.setVisibility(View.INVISIBLE);
                    if (list.size() == 5) {
                        mMany_more.setVisibility(View.VISIBLE);
                    } else {
                        mMany_more.setVisibility(View.GONE);

                    }

                }
            } else if (msg.what == 202) {//json错误
                mSwipeLayout.setRefreshing(false);
            }else if (msg.what == 203) {//无法获取网络数据
                mSwipeLayout.setRefreshing(false);
                ToastUtils.myToast(InformationActivity.this, "获取数据失败");
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
        mHttptools.getFirstPageInformationTwoData(handler, 0, 5);

        mMany_more = (RelativeLayout) findViewById(R.id.in_many_relative);
        mBar = (ProgressBar) findViewById(R.id.in_pbLocate);
        //加载更多
        mMany_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBar.setVisibility(View.VISIBLE);
                mStart+=5;
                mHttptools.getFirstPageInformationTwoData(handler, mStart, mAddNum);

            }
        });
        //刷新
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refreshlayout_id);
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                mStart=0;
                mHttptools.getFirstPageInformationTwoData(handler, 0, 5);


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
                intent.putExtra("type", "information");
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
