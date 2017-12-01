package com.technology.yuyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.AppointmentListViewAdapter;
import com.technology.yuyi.bean.FirstPageInformationTwoData;
import com.technology.yuyi.bean.FirstPageInformationTwoDataRoot;
import com.technology.yuyi.myview.InformationListView;

import java.util.ArrayList;
import java.util.List;

public class AppointmentActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private SwipeRefreshLayout mRefresh;
    private ListView mListviewApp;
    private View footer;
    private ProgressBar footerBar;
    private RelativeLayout nodata_rl;
    private AppointmentListViewAdapter mAdapter;
    private List<FirstPageInformationTwoData> mList = new ArrayList<>();
    private ImageView mBack;//返回
    private RelativeLayout mSearch_rl;//搜索
    private int mStart = 0;
    private int mAddNum = 10;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 28) {//预约挂号数据
                Object o = msg.obj;
                if (o != null && o instanceof FirstPageInformationTwoDataRoot) {
                    FirstPageInformationTwoDataRoot root = (FirstPageInformationTwoDataRoot) o;
                    List<FirstPageInformationTwoData> list = new ArrayList<>();
                    list = root.getRows();
                    mList.addAll(list);
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();
                    mRefresh.setRefreshing(false);
                    mListviewApp.removeFooterView(footer);
                    footerBar.setVisibility(View.INVISIBLE);
                    if (list.size() == 10) {
                        mListviewApp.addFooterView(footer);
                    } else {
                        mListviewApp.removeFooterView(footer);
                    }

                    if (mList.size() == 0) {
                        nodata_rl.setVisibility(View.VISIBLE);
                    }
                }
            } else if (msg.what == 213) {
                Toast.makeText(AppointmentActivity.this, "医院信息错误", Toast.LENGTH_SHORT).show();
                mListviewApp.removeFooterView(footer);
                footerBar.setVisibility(View.GONE);
                mRefresh.setRefreshing(false);
            } else if (msg.what == 214) {
                Toast.makeText(AppointmentActivity.this, "医院信息错误", Toast.LENGTH_SHORT).show();
                mListviewApp.removeFooterView(footer);
                footerBar.setVisibility(View.GONE);
                mRefresh.setRefreshing(false);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        initView();
    }

    public void initView() {
        nodata_rl = (RelativeLayout) findViewById(R.id.nodata_rl);
        nodata_rl.setOnClickListener(this);

        mRefresh = (SwipeRefreshLayout) findViewById(R.id.app_refresh);
        mRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mRefresh.setRefreshing(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStart = 0;
                mList.clear();
                mAdapter.notifyDataSetChanged();
                mHttptools.getAppintmentData(mHandler, mStart, mAddNum);
            }
        });
        //获取首页数据
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getAppintmentData(mHandler, mStart, mAddNum);
        //预约挂号listview
        mListviewApp = (ListView) findViewById(R.id.app_listview_id);
        mListviewApp.setOnItemClickListener(this);
        footer = LayoutInflater.from(this).inflate(R.layout.circle_listview_footer, null);
        footerBar = (ProgressBar) footer.findViewById(R.id.pbLocate);
        mAdapter = new AppointmentListViewAdapter(this, mList);
        mListviewApp.setAdapter(mAdapter);
        //返回键
        mBack = (ImageView) findViewById(R.id.appointment_back);
        mBack.setOnClickListener(this);

        //点击搜索布局，跳转到搜索页面
        mSearch_rl = (RelativeLayout) findViewById(R.id.app_edit_rl);
        mSearch_rl.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        } else if (id == mSearch_rl.getId()) {//点击edittext跳转
            Intent intent = new Intent(this, SearchActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == mList.size()) {
            footerBar.setVisibility(View.VISIBLE);
            mStart += 10;
            mHttptools.getAppintmentData(mHandler, mStart, mAddNum);
        } else {
            Intent intent = new Intent(this, AllHospitalDepartmentActivity.class);
            intent.putExtra("hid", mList.get(position).getId());
            intent.putExtra("hospital_name", mList.get(position).getHospitalName());
            startActivity(intent);
        }

    }
}
