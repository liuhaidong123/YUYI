package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MS_allkinds_MyGridViewAdapter;
import com.technology.yuyi.adapter.MS_allkindssort_Adapter;
import com.technology.yuyi.adapter.MS_home_DailyGridViewAdapter;
import com.technology.yuyi.lzh_utils.MyGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//所有药品页面
//显示选中类别的药品Intent intent=new Intent();intent.putInt("type",1);type:药品类别：0全部
/**
 * Created by wanyu on 2017/2/27.
 */

public class MS_allkinds_activity extends Activity{
    private int type;
    private ScrollView ms_allkinds_scrollview;
    private TextView ms_allkinds_textV_name;//当前的分类
    private List<String> listKinds;
    private MyGridView ms_allkinds_myGridview;
    private String[]str=new String[]{"中药调理","肠胃用药","保健滋补","眼鼻喉耳","皮肤用药","全部","常用药品","保健滋补"};
    private MS_allkinds_MyGridViewAdapter adapter;
    private List<Map<String,String>>list;//adapter数据源
    private int[] Id={R.mipmap.c1,R.mipmap.c2,R.mipmap.c3,R.mipmap.z1,R.mipmap.z2,R.mipmap.z3};
    private String[] Name=new String[]{"地塞米松注射液","健胃颗粒","健胃消食片","牛黄蛇胆川贝液","氨芬曲马多片","天马胶囊"
    };
    private String[]Price=new String[]{"35","21","17","77","37","99"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_allkinds);

        initData();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        type=getIntent().getIntExtra("type",-1);
        if(type!=-1){
            ms_allkinds_textV_name.setText(str[type]);
        }

    }

    //测试用数据
    private void initData() {
        listKinds=new ArrayList<>();
        for(int i=0;i<str.length;i++){
            listKinds.add(str[i]);
        }
        list=new ArrayList<>();
        for (int i=0;i<Id.length;i++){
            Map<String,String>mp=new HashMap<>();
            mp.put("image",Id[i]+"");
            mp.put("name",Name[i]);
            mp.put("price",Price[i]);
            list.add(mp);
        }

    }

    private void initView() {
        ms_allkinds_scrollview= (ScrollView) findViewById(R.id.ms_allkinds_scrollview);
        ms_allkinds_textV_name= (TextView) findViewById(R.id.ms_allkinds_textV_name);
        ms_allkinds_myGridview= (MyGridView) findViewById(R.id.ms_allkinds_myGridview);
        adapter=new MS_allkinds_MyGridViewAdapter(MS_allkinds_activity.this,list);
        ms_allkinds_myGridview.setAdapter(adapter);
        ms_allkinds_myGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MS_allkinds_activity.this,""+position,Toast.LENGTH_SHORT).show();
                getDrugInfo();
            }
        });
    }

    public void getAllKinds(View view) {
        if (view.getId()==R.id.ms_allkinds_relative_allkinds){
            Intent intent=new Intent();
            intent.setClass(MS_allkinds_activity.this, MS_allkinds_sort_activity.class);
            startActivity(intent);
        }
    }

    //药品详情页面
    public void getDrugInfo(){
        Intent intent=new Intent();
        intent.setClass(MS_allkinds_activity.this,MS_drugInfo_activity.class);
        startActivity(intent);
    }
}
