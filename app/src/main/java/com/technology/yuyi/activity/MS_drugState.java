package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MS_DrugStateAdapter;
import com.technology.yuyi.bean.bean_MyDrugState;
import com.technology.yuyi.lzh_utils.MyActivity;
import com.technology.yuyi.lzh_view.MyTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//intent.putExtra("drug",MyDrugState);
public class MS_drugState extends MyActivity {
    private MyTextView myTextView;
    private ListView ms_drugstate_listview;
    private int MAX=5;
    private TextView title;
    private bean_MyDrugState drugState;
    private TextView ms_drugstate_my_state,ms_drugstate_my_time,ms_drugstate_my_nowState;//我的药品状态标题，创建时间，当前状态
    private List<bean_MyDrugState.ResultBean.BoilMedicineListBean>list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_drug_state);
        initView();
        drugState= (bean_MyDrugState) getIntent().getSerializableExtra("drug");
//        initData();
        ms_drugstate_my_time.setText(drugState.getResult().getCreateTimeString());
        ms_drugstate_my_state.setText(drugState.getResult().getTitle());
        list=drugState.getResult().getBoilMedicineList();
        if (list!=null&&list.size()>0){
            ms_drugstate_my_nowState.setText("当前状态："+list.get(list.size()-1).getStateText());
            myTextView.setWidth(list.size()*1.0f/MAX);
            ms_drugstate_listview.setAdapter(new MS_DrugStateAdapter(MS_drugState.this,list));
        }
    }


    private void initView() {
        myTextView= (MyTextView) findViewById(R.id.ms_drugstate_myTextView);
        ms_drugstate_listview= (ListView) findViewById(R.id.ms_drugstate_listview);
        title= (TextView) findViewById(R.id.activity_include_title);
        title.setText("我的药品状态");
        ms_drugstate_my_state= (TextView) findViewById(R.id.ms_drugstate_my_state);
        ms_drugstate_my_time= (TextView) findViewById(R.id.ms_drugstate_my_time);
        ms_drugstate_my_nowState= (TextView) findViewById(R.id.ms_drugstate_my_nowState);
    }
}
