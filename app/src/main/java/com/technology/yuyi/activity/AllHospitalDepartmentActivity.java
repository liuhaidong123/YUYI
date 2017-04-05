package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.LeftListViewAdapter;
import com.technology.yuyi.adapter.RightListViewAdapter;
import com.technology.yuyi.bean.HospitalDepartmentMessage;
import com.technology.yuyi.bean.HospitalDepartmentRoot;
import com.technology.yuyi.bean.HospitalOutPatient;
import com.technology.yuyi.bean.MyEntity;
import com.technology.yuyi.lhd.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllHospitalDepartmentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mHospital_name;
    //左边listview
    private LeftListViewAdapter mLeftAdapter;
    private ListView mLeftListView;
    private List<HospitalDepartmentMessage> mLeftData = new ArrayList();

    //右边listview
    private ListView mRightListView;
    private RightListViewAdapter mRightAdapter;
    private List mRightData = new ArrayList<>();//右边所有门诊集合

    private SwipeRefreshLayout mRefresh;
    //网络数据
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 29) {
                Object o = msg.obj;
                if (o != null && o instanceof HospitalDepartmentRoot) {
                    HospitalDepartmentRoot root = (HospitalDepartmentRoot) o;
                    if (root.getCode().equals("0")) {
                        mLeftData = root.getResult();
                        mLeftAdapter.setmList(mLeftData);
                        mLeftAdapter.notifyDataSetChanged();
                        for (int i = 0; i < mLeftData.size(); i++) {
                            mRightData.add(mLeftData.get(i).getClinicList());
                        }

                        //刚进页面，将第一个科室的第一个数据显示
                        if (mRightData.size()!=0){
                            mRightAdapter.setmList((List<HospitalOutPatient>) mRightData.get(0));
                            mRightAdapter.notifyDataSetChanged();

                        }
                        mRefresh.setRefreshing(false);
                        mRefresh.setEnabled(false);
                    }
                }
            }else if (msg.what==215){
                mRefresh.setRefreshing(false);
                mRefresh.setEnabled(false);
            }else if (msg.what==216){
                mRefresh.setRefreshing(false);
                mRefresh.setEnabled(false);
                ToastUtils.myToast(AllHospitalDepartmentActivity.this,"请求失败");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hospital_department);
        initView();
    }

    private void initView() {
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getHospitalDepartmentData(mHandler, getIntent().getIntExtra("hid", -1));
        //刷新
        mRefresh= (SwipeRefreshLayout) findViewById(R.id.left_right_refresh);
        mRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mRefresh.setRefreshing(true);

        mHospital_name= (TextView) findViewById(R.id.hospital_name);
        mHospital_name.setText(getIntent().getStringExtra("hospital_name"));
        mBack = (ImageView) findViewById(R.id.depart_back);
        mBack.setOnClickListener(this);
        //左边listview
        mLeftListView = (ListView) findViewById(R.id.left_listview);
        mLeftAdapter = new LeftListViewAdapter(this, mLeftData);

        mLeftListView.setAdapter(mLeftAdapter);
        mLeftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                mRightAdapter.setmList((List<HospitalOutPatient>) mRightData.get(position));
                mRightAdapter.notifyDataSetChanged();
                TextView textView = (TextView) view.findViewById(R.id.tv_left);
                //判断点击的当前的item中的对象与mLeftData集合中获取的对象是否是同一个对象
                for (HospitalDepartmentMessage h : mLeftData) {
                    if (h == mLeftAdapter.getItem(position)) {
                        h.setOpen(true);
                        Toast.makeText(AllHospitalDepartmentActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                    } else {
                        h.setOpen(false);
                    }

                    //设置点击时的文字颜色
                    if (h.isOpen()) {
                        textView.setBackgroundColor(Color.parseColor("#ffffff"));
                        textView.setTextColor(Color.parseColor("#25f368"));
                    } else {
                        textView.setBackgroundColor(Color.parseColor("#f2f2f2"));
                        textView.setTextColor(Color.parseColor("#333333"));
                    }

                }


                mLeftAdapter.notifyDataSetChanged();

            }
        });


        mRightListView = (ListView) findViewById(R.id.right_listview);
        mRightAdapter = new RightListViewAdapter(this, mRightData);
        mRightListView.setAdapter(mRightAdapter);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }
    }


}
