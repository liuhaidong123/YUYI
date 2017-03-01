package com.technology.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.technology.yuyi.R;

public class FamilyUserMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private RelativeLayout mMyData_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_user_message);
        initView();
    }

    public void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.family_user_back);
        mBack.setOnClickListener(this);
        //我的数据分析
        mMyData_rl= (RelativeLayout) findViewById(R.id.my_data_rl);
        mMyData_rl.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        }else if (id==mMyData_rl.getId()){
            startActivity(new Intent(this,MyDataAnalyseActivity.class));
        }
    }
}
