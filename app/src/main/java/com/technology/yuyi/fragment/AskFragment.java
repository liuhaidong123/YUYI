package com.technology.yuyi.fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.technology.yuyi.R;
import com.technology.yuyi.activity.HospitalDetailsActivity;
import com.technology.yuyi.adapter.AskListViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AskFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView mListview;
    private AskListViewAdapter mAdapter;
    private SwipeRefreshLayout mRefreshLaout;

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
        mListview = (ListView) view.findViewById(R.id.hospital_listview);
        mAdapter = new AskListViewAdapter(this.getContext());
        mListview.setAdapter(mAdapter);
        mListview.setOnItemClickListener(this);
        //刷新
        mRefreshLaout= (SwipeRefreshLayout) view.findViewById(R.id.ask_swipe_id);
        mRefreshLaout.setColorSchemeColors(Color.parseColor("#e00610"),Color.parseColor("#5ee708"),Color.parseColor("#8bfad4"),Color.parseColor("#5562d6"));
        mRefreshLaout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(),"刷新完成",Toast.LENGTH_SHORT).show();
                        mRefreshLaout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this.getContext(), HospitalDetailsActivity.class));
    }
}
