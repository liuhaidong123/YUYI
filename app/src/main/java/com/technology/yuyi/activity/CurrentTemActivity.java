package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.CurrentTemListViewAdapter;

public class CurrentTemActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mListview;
    private CurrentTemListViewAdapter mAdapter;
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_tem);
        initView();
    }

    public void initView() {
       View view= LayoutInflater.from(this).inflate(R.layout.add_user_foot,null);
        mListview = (ListView) findViewById(R.id.userdata_listview);
        mListview.addFooterView(view);
        mAdapter = new CurrentTemListViewAdapter(this);
        mListview.setAdapter(mAdapter);
        mListview.setSelector(R.color.color_bg);
        mBack = (ImageView) findViewById(R.id.current_back);
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
