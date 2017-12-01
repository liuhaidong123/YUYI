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
import com.technology.yuyi.adapter.InformationListViewAdapter;
import com.technology.yuyi.bean.NewInformationList.Root;
import com.technology.yuyi.bean.NewInformationList.Rows;

import java.util.ArrayList;
import java.util.List;

public class InformationActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mInformationListView;
    private InformationListViewAdapter mInformationAdapter;
    private List<Rows> mList = new ArrayList<>();
    private ImageView mBack;
    private View footer;
    private ProgressBar footerBar;
    private RelativeLayout nodata_rl;
    private int mStart = 0;
    private int mAddNum = 10;
    private SwipeRefreshLayout mSwipeLayout;//刷新
    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 41) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root!=null&&root.getRows()!=null){
                        List<Rows> list = new ArrayList<>();
                        list = root.getRows();
                        mList.addAll(list);
                        mInformationAdapter.setList(mList);
                        mInformationAdapter.notifyDataSetChanged();
                        mSwipeLayout.setRefreshing(false);
                        footerBar.setVisibility(View.INVISIBLE);
                        mInformationListView.removeFooterView(footer);
                        if (list.size() == 10) {
                            mInformationListView.addFooterView(footer);
                        } else {
                            mInformationListView.removeFooterView(footer);
                        }
                        if (mList.size()==0){
                            nodata_rl.setVisibility(View.VISIBLE);
                        }
                    }else {
                        Toast.makeText(InformationActivity.this,"咨询信息错误",Toast.LENGTH_SHORT).show();
                    }

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
        nodata_rl = (RelativeLayout) findViewById(R.id.nodata_rl);
        nodata_rl.setOnClickListener(this);
        //获取资讯页面数据
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getNewInformationList(handler, mStart, mAddNum);
        //刷新
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refreshlayout_id);
        mSwipeLayout.setRefreshing(true);
        mSwipeLayout.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                mStart = 0;
                mInformationAdapter.notifyDataSetChanged();
                mInformationListView.removeFooterView(footer) ;
                mHttptools.getNewInformationList(handler, mStart, mAddNum);


            }
        });
        //资讯adapter
        mInformationListView = (ListView) findViewById(R.id.information_listview_id);
        footer = LayoutInflater.from(this).inflate(R.layout.circle_listview_footer, null);
        footerBar = (ProgressBar) footer.findViewById(R.id.pbLocate);
        mInformationAdapter = new InformationListViewAdapter(this, mList);
        mInformationListView.setAdapter(mInformationAdapter);
        //跳转资讯详情
        mInformationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position==mList.size()){
                    footerBar.setVisibility(View.VISIBLE);
                    mStart += 10;
                    mHttptools.getNewInformationList(handler, mStart, mAddNum);
                }else {
                    Intent intent = new Intent(InformationActivity.this, InformationDetailsActivity.class);
                    intent.putExtra("id", mList.get(position).getId());//将医院的id传过去
                    intent.putExtra("type", "information");
                    startActivity(intent);
                }

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
