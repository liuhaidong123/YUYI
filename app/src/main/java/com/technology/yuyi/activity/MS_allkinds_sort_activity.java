package com.technology.yuyi.activity;

import android.app.Activity;
import android.os.Bundle;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MS_allkindssort_Adapter;
import com.technology.yuyi.lzh_utils.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/2/27.
 */
//药品所有的类别
public class MS_allkinds_sort_activity extends Activity{
    private MyGridView ms_allkindssort_gridview1,ms_allkindssort_gridview2,ms_allkindssort_gridview3
            ,ms_allkindssort_gridview4,ms_allkindssort_gridview5;
    private List<String> list1,list2,list3,list4,list5;
    private String[]str1=new String[]{"中药调理","肠胃用药","保健滋补","眼鼻喉耳","皮肤用药","感冒发烧","调节免疫","维生素钙片"};
    private String[]str2=new String[]{"胃炎胃疼","胃肠溃疡","胃肠不适","腹泻","便秘","消化不良","小儿肠胃病","减肥药"};
    private String[]str3=new String[]{"补肾","补气补血","安神补脑","心脏疾病"};
    private String[]str4=new String[]{"避孕","月经不调","更年期","痛经","产后用药","保胎促孕"};
    private String[]str5=new String[]{"跌打损伤","关节炎","保健滋补","骨质增生","颈肩腰腿疼"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_allkinds_sort);

        initView();
        initData();
    }

    private void initData() {
        list1=new ArrayList<>(); list2=new ArrayList<>(); list3=new ArrayList<>();
        list4=new ArrayList<>(); list5=new ArrayList<>();
        for (int i=0;i<str1.length;i++){
            list1.add(str1[i]);
        }
        for (int i=0;i<str2.length;i++){
            list2.add(str2[i]);
        }
        for (int i=0;i<str3.length;i++){
            list3.add(str3[i]);
        }
        for (int i=0;i<str4.length;i++){
            list4.add(str4[i]);
        }
        for (int i=0;i<str5.length;i++){
            list5.add(str5[i]);
        }
        ms_allkindssort_gridview1.setAdapter(new MS_allkindssort_Adapter(list1,MS_allkinds_sort_activity.this));
        ms_allkindssort_gridview2.setAdapter(new MS_allkindssort_Adapter(list2,MS_allkinds_sort_activity.this));
        ms_allkindssort_gridview3.setAdapter(new MS_allkindssort_Adapter(list3,MS_allkinds_sort_activity.this));
        ms_allkindssort_gridview4.setAdapter(new MS_allkindssort_Adapter(list4,MS_allkinds_sort_activity.this));
        ms_allkindssort_gridview5.setAdapter(new MS_allkindssort_Adapter(list5,MS_allkinds_sort_activity.this));
    }

    private void initView() {
        ms_allkindssort_gridview1= (MyGridView) findViewById(R.id.ms_allkindssort_gridview1);
        ms_allkindssort_gridview2= (MyGridView) findViewById(R.id.ms_allkindssort_gridview2);
        ms_allkindssort_gridview3= (MyGridView) findViewById(R.id.ms_allkindssort_gridview3);
        ms_allkindssort_gridview4= (MyGridView) findViewById(R.id.ms_allkindssort_gridview4);
        ms_allkindssort_gridview5= (MyGridView) findViewById(R.id.ms_allkindssort_gridview5);
    }
}
