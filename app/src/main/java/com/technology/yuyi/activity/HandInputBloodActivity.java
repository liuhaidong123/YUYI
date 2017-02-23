package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.HandInputBloodListViewAdapter;

public class HandInputBloodActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView mListView;
    private HandInputBloodListViewAdapter mAdapter;
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_input_blood);
        initView();
    }

    public void initView(){
        //选择用户
        mListView= (ListView) findViewById(R.id.hand_listview_id);
        View viewFoot= LayoutInflater.from(this).inflate(R.layout.add_user_foot,null);
        mListView.addFooterView(viewFoot);
        mAdapter=new HandInputBloodListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setSelector(R.color.color_bg);
        //返回
        mBack= (ImageView) findViewById(R.id.hand_back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }
    }
}
