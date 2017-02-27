package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MS_home_DailyGridViewAdapter;
import com.technology.yuyi.adapter.MS_home_GridViewAdapter;
import com.technology.yuyi.lzh_utils.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MS_home_Activity extends AppCompatActivity {
    private List<String> listMedicineKind;//药品种类的数据源
    private MyGridView ms_home_gridview;//药品种类展示的view
    private String[]Name=new String[]{"中药调理","肠胃用药","保健滋补","眼鼻喉耳","皮肤用药","全部"};
    private MS_home_GridViewAdapter adapter;
//    常用药的adapter与数据源
    private MyGridView ms_home_gridview_dailyM;
    private MS_home_DailyGridViewAdapter adapterDailly;
    private List<Map<String,String>>listDailly;
    private int[] daillyId={R.mipmap.c1,R.mipmap.c2,R.mipmap.c3};
    private String[] daillyName=new String[]{"地塞米松注射液","健胃颗粒","健胃消食片"
    };
    private String[]daillyPrice=new String[]{"35","21","17"};

    //   滋补药的适配器与数据源
    private MyGridView ms_home_gridview_Nu;
    private MS_home_DailyGridViewAdapter adapterNu;
    private List<Map<String,String>>listNu;
    private int[] nuId={R.mipmap.z1,R.mipmap.z2,R.mipmap.z3};
    private String[] nuName=new String[]{"牛黄蛇胆川贝液","氨芬曲马多片","天马胶囊"
    };
    private String[]nuPrice=new String[]{"77","37","99"};
    //常用药的gridview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_home);
        initData();
        initView();
    }



    //初始化view
    private void initView() {
        ms_home_gridview= (MyGridView) findViewById(R.id.ms_home_gridview);
        ms_home_gridview.setAdapter(adapter);
        ms_home_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IntentClick(position);
            }
        });
        //常用
        ms_home_gridview_dailyM= (MyGridView) findViewById(R.id.ms_home_gridview_dailyM);//常用药的gridview（只显示1行，3个选项）
        ms_home_gridview_dailyM.setAdapter(adapterDailly);
        ms_home_gridview_dailyM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDrugInfo();
            }
        });
       //滋补
        ms_home_gridview_Nu= (MyGridView) findViewById(R.id.ms_home_gridview_Nutritious);//滋补药的gridview（只显示1行，3个选项）
        ms_home_gridview_Nu.setAdapter(adapterNu);
        ms_home_gridview_Nu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getDrugInfo();
            }
        });

    }

    //初始化数据
    private void initData() {
        listMedicineKind=new ArrayList<>();
        for (int i=0;i<Name.length;i++){
            listMedicineKind.add(Name[i]);
        }
        adapter=new MS_home_GridViewAdapter(listMedicineKind,MS_home_Activity.this);
//常用药
        listDailly=new ArrayList<>();
        for (int i=0;i<3;i++){
            Map<String,String>mp=new HashMap<>();
            mp.put("image",daillyId[i]+"");
            mp.put("name",daillyName[i]);
            mp.put("price",daillyPrice[i]);
            listDailly.add(mp);
        }
        adapterDailly=new MS_home_DailyGridViewAdapter(listDailly,MS_home_Activity.this);

//滋补药

        listNu=new ArrayList<>();
        for (int i=0;i<3;i++){
            Map<String,String>mp=new HashMap<>();
            mp.put("image",nuId[i]+"");
            mp.put("name",nuName[i]);
            mp.put("price",nuPrice[i]);
            listNu.add(mp);
        }
        adapterNu=new MS_home_DailyGridViewAdapter(listNu,MS_home_Activity.this);
    }


    //    view的点击事件
    public void ms_homeClick(View v){
        if (v!=null){
            switch (v.getId()){
                case R.id.ms_home_return://首页返回按钮
                    break;
            }
        }

    }




    public void IntentClick(int pos){
        Intent intent=new Intent();
        intent.putExtra("type",pos);
        intent.setClass(MS_home_Activity.this,MS_allkinds_activity.class);
        startActivity(intent);


    }

    public void selectMs(View view) {
        if (view!=null){
            switch (view.getId()){
                case R.id.ms_home_rela1:
                    IntentClick(6);
                    break;
                case R.id.ms_home_rela2:
                    IntentClick(7);
                    break;
            }
        }
    }
    //药品详情页面
    public void getDrugInfo(){
        Intent intent=new Intent();
        intent.setClass(MS_home_Activity.this,MS_drugInfo_activity.class);
        startActivity(intent);
    }
}


