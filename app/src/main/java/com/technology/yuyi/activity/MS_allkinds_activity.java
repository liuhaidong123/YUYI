package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private ImageView ms_allkinds_image_sort;
    private ScrollView ms_allkinds_scrollview;
    private TextView ms_allkinds_textV_name;//当前的分类
    private List<String> listKinds;
    private MyGridView ms_allkinds_myGridview;
    private String[]str=new String[]{"中药调理","肠胃用药","保健滋补","眼鼻喉耳","皮肤用药","全部","常用药品","保健滋补"};
    private MS_allkinds_MyGridViewAdapter adapter;
    private List<Map<String,String>>list;//adapter数据源
    private int[] Id={R.mipmap.c1,R.mipmap.c2,R.mipmap.c3,R.mipmap.z1,R.mipmap.z2,R.mipmap.z3};
    private String[] Name=new String[]{"地塞米松注射液","健胃颗粒","健胃消食片","牛黄蛇胆川贝液","氨芬曲马多片","天马胶囊"};
    private String[]Price=new String[]{"35","21","17","77","37","99"};

    private  RelativeLayout activity_ms_allkinds_rela;//切换药品与分类的按钮

    //以下时所有药品分类相关数据
    private LinearLayout activity_my_allkinds;//所有药品的布局
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
        setContentView(R.layout.activity_ms_allkinds);

        initData();
        initView();

        activity_my_allkinds= (LinearLayout) findViewById(R.id.activity_my_allkinds);
        initViewKinds();//所有药品分类布局
        initDataKinds();//所有药品分类布局的测试数据
        setListenerKinds();//
    }

    private void setListenerKinds() {
        ms_allkindssort_gridview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ms_allkinds_textV_name.setText(list1.get(position));
                activity_my_allkinds.setVisibility(View.GONE);
                ms_allkinds_myGridview.setVisibility(View.VISIBLE);
                ms_allkinds_image_sort.setSelected(false);
            }
        });
        ms_allkindssort_gridview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ms_allkinds_textV_name.setText(list2.get(position));
                activity_my_allkinds.setVisibility(View.GONE);
                ms_allkinds_myGridview.setVisibility(View.VISIBLE);
                ms_allkinds_image_sort.setSelected(false);
            }
        });
        ms_allkindssort_gridview3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ms_allkinds_textV_name.setText(list3.get(position));
                activity_my_allkinds.setVisibility(View.GONE);
                ms_allkinds_myGridview.setVisibility(View.VISIBLE);
                ms_allkinds_image_sort.setSelected(false);
            }
        });
        ms_allkindssort_gridview4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ms_allkinds_textV_name.setText(list4.get(position));
                activity_my_allkinds.setVisibility(View.GONE);
                ms_allkinds_myGridview.setVisibility(View.VISIBLE);
                ms_allkinds_image_sort.setSelected(false);
            }
        });
        ms_allkindssort_gridview5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ms_allkinds_textV_name.setText(list5.get(position));
                activity_my_allkinds.setVisibility(View.GONE);
                ms_allkinds_myGridview.setVisibility(View.VISIBLE);
                ms_allkinds_image_sort.setSelected(false);
            }
        });
    }

    //所有药品分类布局
    private void initViewKinds() {
        ms_allkindssort_gridview1= (MyGridView) findViewById(R.id.ms_allkindssort_gridview1);
        ms_allkindssort_gridview2= (MyGridView) findViewById(R.id.ms_allkindssort_gridview2);
        ms_allkindssort_gridview3= (MyGridView) findViewById(R.id.ms_allkindssort_gridview3);
        ms_allkindssort_gridview4= (MyGridView) findViewById(R.id.ms_allkindssort_gridview4);
        ms_allkindssort_gridview5= (MyGridView) findViewById(R.id.ms_allkindssort_gridview5);
    }
//所有药品分类的测试数据
    private void initDataKinds() {
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
        ms_allkindssort_gridview1.setAdapter(new MS_allkindssort_Adapter(list1,MS_allkinds_activity.this));
        ms_allkindssort_gridview2.setAdapter(new MS_allkindssort_Adapter(list2,MS_allkinds_activity.this));
        ms_allkindssort_gridview3.setAdapter(new MS_allkindssort_Adapter(list3,MS_allkinds_activity.this));
        ms_allkindssort_gridview4.setAdapter(new MS_allkindssort_Adapter(list4,MS_allkinds_activity.this));
        ms_allkindssort_gridview5.setAdapter(new MS_allkindssort_Adapter(list5,MS_allkinds_activity.this));

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
        ms_allkinds_image_sort= (ImageView) findViewById(R.id.ms_allkinds_image_sort);
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
            if (ms_allkinds_image_sort.isSelected()){//当前显示的时全部分类
                activity_my_allkinds.setVisibility(View.GONE);
                ms_allkinds_myGridview.setVisibility(View.VISIBLE);
                ms_allkinds_image_sort.setSelected(false);
            }
            else {
                activity_my_allkinds.setVisibility(View.VISIBLE);
                ms_allkinds_myGridview.setVisibility(View.GONE);
                ms_allkinds_image_sort.setSelected(true);
                }
//
//            Intent intent=new Intent();
//            intent.setClass(MS_allkinds_activity.this, MS_allkinds_sort_activity.class);
//            startActivity(intent);
        }
    }

    //药品详情页面
    public void getDrugInfo(){
        Intent intent=new Intent();
        intent.setClass(MS_allkinds_activity.this,MS_drugInfo_activity.class);
        startActivity(intent);
    }

    public void goBack(View view) {
        if (view!=null){
            if (view.getId()==R.id.ms_allkinds_return){
                finish();
            }
        }
    }
}
