package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.CurrentBloodAdapter;

public class CurrentBloodActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView mListView;
    private CurrentBloodAdapter mAdapter;
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_blood);
        initView();
    }
    public void initView(){
        mListView= (ListView) findViewById(R.id.userdata_listview_id);
        mAdapter=new CurrentBloodAdapter(this);
        mListView.setSelector(R.color.color_bg);
        View view= LayoutInflater.from(this).inflate(R.layout.add_user_foot,null);
        mListView.addFooterView(view);
        mListView.setAdapter(mAdapter);

        mBack= (ImageView) findViewById(R.id.m_current_back);
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
