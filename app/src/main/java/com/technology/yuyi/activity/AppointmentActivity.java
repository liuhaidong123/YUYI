package com.technology.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.AppointmentListViewAdapter;

public class AppointmentActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ListView mListviewApp;
    private AppointmentListViewAdapter mAdapter;
    private ImageView mBack;
    private EditText mEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        initView();
    }

    public void initView(){
        //预约挂号listview
        mListviewApp= (ListView) findViewById(R.id.app_listview_id);
        mListviewApp.setOnItemClickListener(this);
        mAdapter=new AppointmentListViewAdapter(this);
        mListviewApp.setAdapter(mAdapter);
        //返回键
        mBack= (ImageView) findViewById(R.id.appointment_back);
        mBack.setOnClickListener(this);
        //输入框
        mEditText= (EditText) findViewById(R.id.app_edit_box);
        mEditText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){//返回
            finish();
        }else if (id==mEditText.getId()){//点击edittext跳转
            startActivity(new Intent(this,SearchActivity.class));
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(this,AllHospitalDepartmentActivity.class));
    }
}
