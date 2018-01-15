package com.technology.yuyi.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
 * zixun
 */
public class AskFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListview;
    private AskListViewAdapter mAdapter;
    private List<FirstPageInformationTwoData> mList = new ArrayList<>();
    private SwipeRefreshLayout mRefreshLaout,mNODataRefresh;
    private TextView mNoMsg_Tv;
    private View footer;
    private ProgressBar footerBar;
    private HttpTools mHttptools;

    private int mStart = 0;
    private int mAddNum = 10;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 24) {
                mNODataRefresh.setRefreshing(false);
                Object o = msg.obj;
                if (o != null && o instanceof FirstPageInformationTwoDataRoot) {
                    FirstPageInformationTwoDataRoot root = (FirstPageInformationTwoDataRoot) o;
                    if (root!=null&&root.getRows()!=null){

                        List<FirstPageInformationTwoData> list = new ArrayList<>();
                        list = root.getRows();
                        mListview.removeFooterView(footer);
                        footerBar.setVisibility(View.INVISIBLE);
                        mList.addAll(list);
                        mAdapter.setmList(mList);
                        mAdapter.notifyDataSetChanged();
                        mRefreshLaout.setRefreshing(false);
                        if (list.size() == 10) {
                            mListview.addFooterView(footer);
                        } else {
                            mListview.removeFooterView(footer);
                        }

                        if (mList.size()==0){
                            mNODataRefresh.setVisibility(View.VISIBLE);
                            mNoMsg_Tv.setText("暂无医院信息");
                        }else {
                            mNODataRefresh.setVisibility(View.GONE);
                            mNoMsg_Tv.setText("");
                        }
                    }else {//账号异常，重新登录
                        mNODataRefresh.setVisibility(View.VISIBLE);
                        mNoMsg_Tv.setText("账号异常,请重新登录");
                    }

                }
            } else if (msg.what == 205) {
                mNODataRefresh.setRefreshing(false);
                mNODataRefresh.setVisibility(View.VISIBLE);
                mNoMsg_Tv.setText("医院信息错误,请检查账号异常");
                mListview.removeFooterView(footer);
                footerBar.setVisibility(View.INVISIBLE);
                mRefreshLaout.setRefreshing(false);
                Toast.makeText(getActivity(),"医院信息错误",Toast.LENGTH_SHORT).show();
            } else if (msg.what == 206) {
                mNODataRefresh.setRefreshing(false);
                mNODataRefresh.setVisibility(View.VISIBLE);
                mNoMsg_Tv.setText("医院信息错误,请检查账号异常");
                Toast.makeText(getActivity(),"医院信息错误",Toast.LENGTH_SHORT).show();
                mListview.removeFooterView(footer);
                footerBar.setVisibility(View.INVISIBLE);
                mRefreshLaout.setRefreshing(false);
            }
        }
    };

    public AskFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ask, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        Log.e("纬度", user.Latitude + "");
        Log.e("经度", user.Longitude + "");
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getAskData(handler, mStart, mAddNum);

        mListview = (ListView) view.findViewById(R.id.hospital_listview);
        footer = LayoutInflater.from(getActivity()).inflate(R.layout.circle_listview_footer, null);
        footerBar = (ProgressBar) footer.findViewById(R.id.pbLocate);
        mAdapter = new AskListViewAdapter(this.getActivity(), mList);
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(this);
        //刷新
        mRefreshLaout = (SwipeRefreshLayout) view.findViewById(R.id.ask_swipe_id);
        mRefreshLaout.setRefreshing(true);//刚进页面显示刷新的图标，但是这个方法不会执行mRefreshLaout.setOnRefreshListener
        mRefreshLaout.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mRefreshLaout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStart = 0;
                mListview.removeFooterView(footer);
                mList.clear();
                mAdapter.notifyDataSetChanged();
                mHttptools.getAskData(handler,mStart, mAddNum);
            }
        });
        mNODataRefresh= (SwipeRefreshLayout) view.findViewById(R.id.error_data_refresh);
        mNODataRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mNODataRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mStart = 0;
                mListview.removeFooterView(footer);
                mList.clear();
                mAdapter.notifyDataSetChanged();
                mHttptools.getAskData(handler, mStart, mAddNum);
            }
        });
        mNoMsg_Tv= (TextView) view.findViewById(R.id.no_msg_tv);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (position == mList.size()) {
            footerBar.setVisibility(View.VISIBLE);
            mStart += 10;
            mHttptools.getAskData(handler, mStart, mAddNum);
        } else {
            Intent intent = new Intent(this.getActivity(), HospitalDetailsActivity.class);
            intent.putExtra("id", mList.get(position).getId());
            startActivity(intent);
        }
    }
}
