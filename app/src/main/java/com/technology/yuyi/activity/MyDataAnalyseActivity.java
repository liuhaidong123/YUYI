package com.technology.yuyi.activity;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.ViewPagerBloodTemAdapter;
import com.technology.yuyi.myview.BloodView;
import com.technology.yuyi.myview.TemView;

import java.util.ArrayList;
import java.util.List;

public class MyDataAnalyseActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mViewPager;
    private ViewPagerBloodTemAdapter mAdapter;
    private List<View> mList = new ArrayList<>(); //血压图和体温图
    private BloodView mBloodView;
    private TemView mTemView;
    private ArrayList<Integer> YbloodNum = new ArrayList<>();//y轴血压数据
    private ArrayList<Integer> XdateNum = new ArrayList<>();//x轴日期数据
    private ArrayList<Integer> heightBloodData = new ArrayList<>();  //血压高压数据
    private ArrayList<Integer> lowBloodData = new ArrayList<>();//血压低压数据

    private ArrayList<Integer> YTemData = new ArrayList<>();//Y轴温度
    private ArrayList<Integer> XTemdateNum = new ArrayList<>();  //x轴日期数据
    private ArrayList<Float> temData = new ArrayList<>(); //体温

    private TextView mBloodTv;//血压文字（默认绿色）
    private TextView mTemTv;//体温文字
    private View mBloodLine;//血压下面的线；（默认绿色）
    private View mTemLine;//体温下面的线；
    private LinearLayout mBloodMess_ll;//显示血压的数据（默认显示）
    private LinearLayout mTemMess_ll;//显示体温的数据
    private RelativeLayout mBloodBottomMess_rl;//底部描述血压的文字（默认显示）
    private RelativeLayout mTemBottomMess_rl;//底部描述体温的文字

    public final String mBlueColor = "#25f368";
    public final String mGayColor = "#6a6a6a";
    public final String mGayLine = "#f2f2f2";

    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data_analyse);
        initView();
    }

    public void initView() {
        //血压图和体温图viewpager
        initBloodData();//初始化血压数据
        initTemData();//初始化体温
        mBloodView = new BloodView(this);
        mBloodView.setInfo(YbloodNum, XdateNum, heightBloodData, lowBloodData);
        mTemView = new TemView(this);
        mTemView.setTemInfo(YTemData, XTemdateNum, temData);
        mList.add(mBloodView);
        mList.add(mTemView);
        mViewPager = (ViewPager) findViewById(R.id.my_data_viewpager);
        mAdapter = new ViewPagerBloodTemAdapter(this, mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

        //切换viewpager时需要初始的数据
        mBloodTv = (TextView) findViewById(R.id.tv_blood);
        mBloodTv.setOnClickListener(this);
        mTemTv = (TextView) findViewById(R.id.tv_tem);
        mTemTv.setOnClickListener(this);
        mBloodLine = findViewById(R.id.left_line);
        mTemLine = findViewById(R.id.right_line);
        mBloodMess_ll = (LinearLayout) findViewById(R.id.blood_data_ll);
        mTemMess_ll = (LinearLayout) findViewById(R.id.tem_data_ll);
        mBloodBottomMess_rl = (RelativeLayout) findViewById(R.id.blood_data_mess_rl);
        mTemBottomMess_rl = (RelativeLayout) findViewById(R.id.tem_data_mess_rl);

        //返回
        mBack= (ImageView) findViewById(R.id.analyse_back);
        mBack.setOnClickListener(this);
    }

    /**
     * 初始化血压折线图数据
     */
    public void initBloodData() {
        //y轴血压数据
        for (int i = 40; i <= 180; i += 20) {
            YbloodNum.add(i);
        }

        //x轴日期数据
        for (int i = 1; i <= 7; i += 1) {
            XdateNum.add(i);
        }

        //血压高压数据
        for (int i = 1; i <= 7; i += 1) {
            int data = (int) (Math.random() * 100) + 50;
            heightBloodData.add(data);
            Log.e("高压血压数据", data + "");
        }

        //血压低压数据
        for (int i = 1; i <= 7; i += 1) {
            int data = (int) (Math.random() * 20) + 50;
            lowBloodData.add(data);
            Log.e("低压血压数据", data + "");
        }
    }

    /**
     * 初始化体温折线图数据
     */

    public void initTemData() {

        for (int i = 35; i < 43; i++) {
            YTemData.add(i);
        }

        for (int i = 1; i <= 7; i += 1) {
            XTemdateNum.add(i);
        }


        temData.add(40.0f);
        temData.add(36.5f);
        temData.add(42f);
        temData.add(38.7f);
        temData.add(36.9f);
        temData.add(40.5f);
        temData.add(35.7f);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            showBloodData();
        } else {
            showTemData();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //滑动viewpager显示血压的数据
    public void showBloodData() {
        //血压的数据变化
        mBloodTv.setTextColor(Color.parseColor(mBlueColor));//血压文字绿色
        mBloodLine.setBackgroundColor(Color.parseColor(mBlueColor));//血压线绿色
        mBloodMess_ll.setVisibility(View.VISIBLE);//显示血压的数据（默认显示）
        mBloodBottomMess_rl.setVisibility(View.VISIBLE); //显示底部描述血压的文字（默认显示）

        //体温的数据变化
        mTemTv.setTextColor(Color.parseColor(mGayColor));//体温文字灰色
        mTemLine.setBackgroundColor(Color.parseColor(mGayLine));//体温线灰色
        mTemMess_ll.setVisibility(View.INVISIBLE);//隐藏体温数据
        mTemBottomMess_rl.setVisibility(View.INVISIBLE); //隐藏底部描述体温的文字（默认显示）
    }

    //滑动viewpager显示体温的数据
    public void showTemData() {

        //血压的数据变化
        mBloodTv.setTextColor(Color.parseColor(mGayColor));//血压文字灰色
        mBloodLine.setBackgroundColor(Color.parseColor(mGayLine));//血压线灰色
        mBloodMess_ll.setVisibility(View.INVISIBLE);//隐藏血压的数据（
        mBloodBottomMess_rl.setVisibility(View.INVISIBLE); //隐藏底部描述血压的文字

        //体温的数据变化
        mTemTv.setTextColor(Color.parseColor(mBlueColor));//体温文字绿色
        mTemLine.setBackgroundColor(Color.parseColor(mBlueColor));//体温线绿色
        mTemMess_ll.setVisibility(View.VISIBLE);//显示体温数据
        mTemBottomMess_rl.setVisibility(View.VISIBLE); //显示底部描述体温的文字（
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //点击血压显示血压数据，隐藏体温数据
        if (id == mBloodTv.getId()) {
            showBloodData();
            mViewPager.setCurrentItem(0);
            //点击体温，显示体温数据，隐藏血压数据
        } else if (id == mTemTv.getId()) {
            showTemData();
            mViewPager.setCurrentItem(1);
        }else if (id==mBack.getId()){
            finish();
        }
    }
}
