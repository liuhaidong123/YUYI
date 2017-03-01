package com.technology.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MyOrderListViewAdapter;

public class MyOrderActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView mListView;
    private MyOrderListViewAdapter mAdapter;
private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        initView();
    }

    public void initView(){
        //订单数据
        mListView= (ListView) findViewById(R.id.my_order_listview_id);
        mAdapter=new MyOrderListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MyOrderActivity.this,OrderMessageActivity.class));
            }
        });
       //返回
        mBack= (ImageView) findViewById(R.id.my_order_back);
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
