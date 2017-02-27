package com.technology.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.ElectronicMessListViewAdapter;

public class ElectronicMessActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
private ImageView mBack;
    private ListView mLisview;
    private ElectronicMessListViewAdapter mAdapter;
    private View mHeaderView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electronic_mess);
        initView();
    }

    public void initView(){
        //返回
        mBack= (ImageView) findViewById(R.id.elec_back);
        mBack.setOnClickListener(this);

        //电子病历数据
        mLisview= (ListView) findViewById(R.id.elec_listview);
        mHeaderView= LayoutInflater.from(this).inflate(R.layout.elec_header,null);
        mLisview.addHeaderView(mHeaderView);
        mAdapter=new ElectronicMessListViewAdapter(this);
        mLisview.setAdapter(mAdapter);
        mLisview.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position!=0){
            startActivity(new Intent(this,LookElectronicMessActivity.class));
        }

    }
}
