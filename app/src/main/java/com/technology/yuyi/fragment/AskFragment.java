package com.technology.yuyi.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.activity.HospitalDetailsActivity;
import com.technology.yuyi.adapter.AskListViewAdapter;
import com.technology.yuyi.bean.FirstPageInformationTwoData;
import com.technology.yuyi.bean.FirstPageInformationTwoDataRoot;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.InformationListView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends Fragment implements AdapterView.OnItemClickListener {

    private InformationListView mListview;
    private AskListViewAdapter mAdapter;
    private List<FirstPageInformationTwoData> mList = new ArrayList<>();
    private SwipeRefreshLayout mRefreshLaout;

    private RelativeLayout mMany_more;
    private ProgressBar mProgress;
    private HttpTools mHttptools;

    private int mStart=0;
    private int mAddNum=10;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 24) {
                Object o = msg.obj;
                if (o != null && o instanceof FirstPageInformationTwoDataRoot) {
                    FirstPageInformationTwoDataRoot root = (FirstPageInformationTwoDataRoot) o;
                    List<FirstPageInformationTwoData> list=new ArrayList<>();
                    list = root.getRows();
                    mList.addAll(list);
                    mAdapter.setmList(mList);
                    mAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                    mRefreshLaout.setRefreshing(false);
                    mProgress.setVisibility(View.INVISIBLE);
                    if (list.size()==10){
                        mMany_more.setVisibility(View.VISIBLE);
                    }else {
                        mMany_more.setVisibility(View.GONE);
                    }
                }
            } else if (msg.what == 101) {
                Toast.makeText(getContext(), "无法获取数据", Toast.LENGTH_SHORT).show();
                mRefreshLaout.setRefreshing(false);
            }
        }
    };

    public AskFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {

        //获取网络数据
        Toast.makeText(getContext(), "纬度" + user.Latitude, Toast.LENGTH_SHORT).show();
        Toast.makeText(getContext(), "经度" + user.Longitude, Toast.LENGTH_SHORT).show();
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getAskData(handler,0,10);

        mMany_more= (RelativeLayout) view.findViewById(R.id.many_relative);
        mProgress= (ProgressBar) view.findViewById(R.id.pbLocate);
        //点击加载更多显示数据
        mMany_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress.setVisibility(View.VISIBLE);
                mStart+=10;
                mHttptools.getAskData(handler,mStart,mAddNum);
            }
        });
        mListview = (InformationListView) view.findViewById(R.id.hospital_listview);
        mAdapter = new AskListViewAdapter(this.getContext(), mList);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(this);
        //刷新
        mRefreshLaout = (SwipeRefreshLayout) view.findViewById(R.id.ask_swipe_id);
        mRefreshLaout.setRefreshing(true);//刚进页面显示刷新的图标，但是这个方法不会执行mRefreshLaout.setOnRefreshListener
        mRefreshLaout.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mRefreshLaout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStart=0;
                mList.clear();
                mHttptools.getAskData(handler,0,10);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(this.getContext(), HospitalDetailsActivity.class);
            intent.putExtra("id", mList.get(position).getId());
            startActivity(intent);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

}
