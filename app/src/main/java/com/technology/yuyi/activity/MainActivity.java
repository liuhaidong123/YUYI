package com.technology.yuyi.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.R;
import com.technology.yuyi.fragment.AskFragment;
import com.technology.yuyi.fragment.FirstPageFragment;
import com.technology.yuyi.fragment.MeasureFragment;
import com.technology.yuyi.fragment.MyFragment;
import com.technology.yuyi.lzh_utils.user;

import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout mFirstPage_ll;
    private LinearLayout mMeasure_ll;
    private LinearLayout mAsk_ll;
    private LinearLayout mMy_ll;
    private TextView mFirstPage_tv;
    private TextView mMeasure_tv;
    private TextView mAsk_tv;
    private TextView mMy_tv;
    private ImageView mFirstPafe_img;
    private ImageView mMeasure_img;
    private ImageView mAsk_img;
    private ImageView mMy_img;
    private RelativeLayout mFragment_rl;
    private FragmentManager mFragmentManager;
    public final String firstPageTag = "firstPageFragment";
    public final String measureTag = "measureFragment";
    public final String askTag = "askFragment";
    public final String myTag = "myFragment";

    public final String pressColor = "#25f368";
    public final String noPressColor = "#666666";
    private long time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showFirstPageFragment();

    }

    //初始化数据
    public void initView() {
        mFragment_rl = (RelativeLayout) findViewById(R.id.fragment_relative);
        mFragmentManager = getSupportFragmentManager();
        //底部按钮
        mFirstPage_ll = (LinearLayout) findViewById(R.id.firstpage_btn_ll);
        mMeasure_ll = (LinearLayout) findViewById(R.id.measure_btn_ll);
        mAsk_ll = (LinearLayout) findViewById(R.id.ask_btn_ll);
        mMy_ll = (LinearLayout) findViewById(R.id.my_btn_ll);
        mFirstPage_ll.setOnClickListener(this);
        mMeasure_ll.setOnClickListener(this);
        mAsk_ll.setOnClickListener(this);
        mMy_ll.setOnClickListener(this);
        //底部文字
        mFirstPage_tv = (TextView) findViewById(R.id.firstpage_tv);
        mMeasure_tv = (TextView) findViewById(R.id.measure_tv);
        mAsk_tv = (TextView) findViewById(R.id.ask_tv);
        mMy_tv = (TextView) findViewById(R.id.my_tv);
        //底部图片
        mFirstPafe_img = (ImageView) findViewById(R.id.firstpage_btn_img);
        mMeasure_img = (ImageView) findViewById(R.id.measure_btn_img);
        mAsk_img = (ImageView) findViewById(R.id.ask_btn_img);
        mMy_img = (ImageView) findViewById(R.id.my_btn_img);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mFirstPage_ll.getId()) {
            showFirstPageFragment();
        } else if (id == mMeasure_ll.getId()) {
            showMeasureFragment();
        } else if (id == mAsk_ll.getId()) {
            showAskFragment();
        } else if (id == mMy_ll.getId()) {
            showMyFragment();
        }
    }

    //显示首页
    public void showFirstPageFragment() {
        clickFirstBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment firstPageFragment = mFragmentManager.findFragmentByTag(firstPageTag);
        Fragment measureFragment = mFragmentManager.findFragmentByTag(measureTag);
        Fragment askFragment = mFragmentManager.findFragmentByTag(askTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (firstPageFragment != null) {//显示首页
            fragmentTransaction.show(firstPageFragment);
        } else {
            FirstPageFragment firstPageF = new FirstPageFragment();
            fragmentTransaction.add(mFragment_rl.getId(), firstPageF, firstPageTag);
        }
        if (measureFragment != null) {//隐藏测量
            fragmentTransaction.hide(measureFragment);
        }
        if (askFragment != null) {//隐藏咨询
            fragmentTransaction.hide(askFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();
    }

    //显示测量页面
    public void showMeasureFragment() {
        clickMeasureBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment firstPageFragment = mFragmentManager.findFragmentByTag(firstPageTag);
        Fragment measureFragment = mFragmentManager.findFragmentByTag(measureTag);
        Fragment askFragment = mFragmentManager.findFragmentByTag(askTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (measureFragment != null) {//显示测量
            fragmentTransaction.show(measureFragment);
        } else {
            MeasureFragment measureF = new MeasureFragment();
            fragmentTransaction.add(mFragment_rl.getId(), measureF, measureTag);
        }
        if (firstPageFragment != null) {//隐藏首页
            fragmentTransaction.hide(firstPageFragment);
        }
        if (askFragment != null) {//隐藏咨询
            fragmentTransaction.hide(askFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();

    }

    //显示咨询页面
    public void showAskFragment() {
        clickAskBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment firstPageFragment = mFragmentManager.findFragmentByTag(firstPageTag);
        Fragment measureFragment = mFragmentManager.findFragmentByTag(measureTag);
        Fragment askFragment = mFragmentManager.findFragmentByTag(askTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (askFragment != null) {//显示咨询
            fragmentTransaction.show(askFragment);
        } else {
            AskFragment askF = new AskFragment();
            fragmentTransaction.add(mFragment_rl.getId(), askF, askTag);
        }
        if (firstPageFragment != null) {//隐藏首页
            fragmentTransaction.hide(firstPageFragment);
        }
        if (measureFragment != null) {//隐藏测量
            fragmentTransaction.hide(measureFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();

    }

    //显示我的页面
    public void showMyFragment() {
        clickMyBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment firstPageFragment = mFragmentManager.findFragmentByTag(firstPageTag);
        Fragment measureFragment = mFragmentManager.findFragmentByTag(measureTag);
        Fragment askFragment = mFragmentManager.findFragmentByTag(askTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (myFragment != null) {//显示我的
            fragmentTransaction.show(myFragment);
        } else {
            MyFragment myF = new MyFragment();
            fragmentTransaction.add(mFragment_rl.getId(), myF, myTag);
        }
        if (firstPageFragment != null) {//隐藏首页
            fragmentTransaction.hide(firstPageFragment);
        }
        if (measureFragment != null) {//隐藏测量
            fragmentTransaction.hide(measureFragment);
        }
        if (askFragment != null) {//隐藏咨询
            fragmentTransaction.hide(askFragment);
        }
        fragmentTransaction.commit();
    }

    //点击首页文字图片变化
    public void clickFirstBtnChangeTvColor() {
        mFirstPage_tv.setTextColor(Color.parseColor(pressColor));
        mFirstPafe_img.setImageResource(R.mipmap.first_page_press);

        mMeasure_tv.setTextColor(Color.parseColor(noPressColor));
        mMeasure_img.setImageResource(R.mipmap.measure_no_press);

        mAsk_tv.setTextColor(Color.parseColor(noPressColor));
        mAsk_img.setImageResource(R.mipmap.ask_no_press);

        mMy_tv.setTextColor(Color.parseColor(noPressColor));
        mMy_img.setImageResource(R.mipmap.my_no_press);

    }

    //点击测量文字图片变化
    public void clickMeasureBtnChangeTvColor() {
        mFirstPage_tv.setTextColor(Color.parseColor(noPressColor));
        mFirstPafe_img.setImageResource(R.mipmap.first_page_no_press);

        mMeasure_tv.setTextColor(Color.parseColor(pressColor));
        mMeasure_img.setImageResource(R.mipmap.measure_press);

        mAsk_tv.setTextColor(Color.parseColor(noPressColor));
        mAsk_img.setImageResource(R.mipmap.ask_no_press);

        mMy_tv.setTextColor(Color.parseColor(noPressColor));
        mMy_img.setImageResource(R.mipmap.my_no_press);
    }

    //点击咨询文字图片变化
    public void clickAskBtnChangeTvColor() {
        mFirstPage_tv.setTextColor(Color.parseColor(noPressColor));
        mFirstPafe_img.setImageResource(R.mipmap.first_page_no_press);

        mMeasure_tv.setTextColor(Color.parseColor(noPressColor));
        mMeasure_img.setImageResource(R.mipmap.measure_no_press);

        mAsk_tv.setTextColor(Color.parseColor(pressColor));
        mAsk_img.setImageResource(R.mipmap.ask_press);

        mMy_tv.setTextColor(Color.parseColor(noPressColor));
        mMy_img.setImageResource(R.mipmap.my_no_press);
    }

    //点击我的文字图片变化
    public void clickMyBtnChangeTvColor() {
        mFirstPage_tv.setTextColor(Color.parseColor(noPressColor));
        mFirstPafe_img.setImageResource(R.mipmap.first_page_no_press);

        mMeasure_tv.setTextColor(Color.parseColor(noPressColor));
        mMeasure_img.setImageResource(R.mipmap.measure_no_press);

        mAsk_tv.setTextColor(Color.parseColor(noPressColor));
        mAsk_img.setImageResource(R.mipmap.ask_no_press);

        mMy_tv.setTextColor(Color.parseColor(pressColor));
        mMy_img.setImageResource(R.mipmap.my_press);

    }

    @Override
    public void onBackPressed() {
        if (time > 0) {
            if (System.currentTimeMillis() - time < 2000) {
                super.onBackPressed();
            } else {
                time = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }

        } else {
            time = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(MainActivity.this);
    }
}
