package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.EquitmentManageListViewAdapter;

public class EquipmentManageActivity extends AppCompatActivity implements View.OnClickListener{
    private ListView mListView;
    private EquitmentManageListViewAdapter mAdapter;
private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_manage);
        initView();
    }

    public void initView(){
        mListView= (ListView) findViewById(R.id.equitment_listview_id);
        mAdapter=new EquitmentManageListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        //返回
        mBack= (ImageView) findViewById(R.id.equit_back);
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
