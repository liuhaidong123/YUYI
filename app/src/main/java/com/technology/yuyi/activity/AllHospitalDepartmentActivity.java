package com.technology.yuyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.LeftListViewAdapter;
import com.technology.yuyi.adapter.RightListViewAdapter;

import java.util.ArrayList;

public class AllHospitalDepartmentActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mBack;
    //左边listview
    private LeftListViewAdapter mLeftAdapter;
    private ListView mLeftListView;
    private String[] str = {"内科", "外科", "妇产科", "儿科", "眼科", "耳鼻喉科", "口腔科", "中医科"};
    private ArrayList<String> mLeftData = new ArrayList();

    //右边listview
    private ListView mRightListView;
    private RightListViewAdapter mRightAdapter;
    private String[] str2 = {"呼吸内科门诊", "消化内科门诊", "肾内科门诊", "心血管内科门诊", "神经内科门诊", "感染科门诊", "普内科门诊", "发热门诊", "康复理疗科门诊"};
    private ArrayList<String> mRightData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hospital_department);
        initView();
    }

    private void initView() {
        mBack= (ImageView) findViewById(R.id.depart_back);
        mBack.setOnClickListener(this);
        //左边listview
        for (int i = 0; i < str.length; i++) {
            mLeftData.add(str[i]);
        }
        mLeftListView = (ListView) findViewById(R.id.left_listview);
        mLeftAdapter = new LeftListViewAdapter(this, mLeftData);
        mLeftListView.setAdapter(mLeftAdapter);
        mLeftListView.setSelector(R.color.color_white);
        mLeftListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                TextView tv = (TextView) view.findViewById(R.id.tv_left);
//                tv.setTextColor(Color.parseColor("#25f368"));
            }
        });

        //右边listview

        for (int i = 0; i < str2.length; i++) {
            mRightData.add(str2[i]);
        }
        mRightListView = (ListView) findViewById(R.id.right_listview);
        mRightAdapter = new RightListViewAdapter(this, mRightData);
        mRightListView.setAdapter(mRightAdapter);
        mRightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               startActivity(new Intent(AllHospitalDepartmentActivity.this,SelectDoctorActivity.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }
    }
}
