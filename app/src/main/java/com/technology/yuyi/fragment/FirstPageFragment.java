package com.technology.yuyi.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.technology.yuyi.R;
import com.technology.yuyi.activity.AppointmentActivity;
import com.technology.yuyi.activity.InformationActivity;
import com.technology.yuyi.activity.InformationDetailsActivity;
import com.technology.yuyi.activity.MS_home_Activity;
import com.technology.yuyi.adapter.FirstPageListViewAdapter;
import com.technology.yuyi.adapter.UseDrugGridViewAdapter;
import com.technology.yuyi.adapter.ViewPagerAdAdapter;
import com.technology.yuyi.adapter.ViewPagerBloodTemAdapter;
import com.technology.yuyi.myview.BloodView;
import com.technology.yuyi.myview.InformationListView;
import com.technology.yuyi.myview.TemView;
import com.technology.yuyi.viewpager.impl.AdListenerImpl;
import com.technology.yuyi.viewpager.impl.BloodTemImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstPageFragment extends Fragment implements View.OnClickListener{
    private EditText mEdit;
    private RelativeLayout mScrollRelative;
    private FirstPageListViewAdapter mListViewAdapter;//资讯adapter
    private ListView mFirstPageListView;//资讯ListView

    private GridView mGridview;//常用药品
    private UseDrugGridViewAdapter mUseDrugAdapter;//常用药品adapter

    private ViewPager mViewPagerAD;
    private List<Integer> mListAd = new ArrayList<>();//广告图片集合
    private ImageView mCircleImg;
    private ImageView[] mArrImageView;//存放广告小圆点的数组
    private ViewGroup mGroup;//存放小圆点容器

    private ViewPager mViewPagerBlood;
    private ViewPagerBloodTemAdapter mBloodTemAdapter;
    private BloodView mBloodView;
    private TemView mTemView;
    private LinearLayout drugmall_ll;
    private ArrayList<Integer> YbloodNum = new ArrayList<>();//y轴血压数据
    private ArrayList<Integer> XdateNum = new ArrayList<>();//x轴日期数据
    private ArrayList<Integer> heightBloodData = new ArrayList<>();  //血压高压数据
    private ArrayList<Integer> lowBloodData = new ArrayList<>();//血压低压数据

    private ArrayList<Integer> YTemData = new ArrayList<>();//Y轴温度
    private ArrayList<Integer> XTemdateNum = new ArrayList<>();  //x轴日期数据
    private ArrayList<Float> temData = new ArrayList<>(); //体温
    private ArrayList<View> mViewList = new ArrayList<>();
    private ViewGroup mBloodGroup;
    private ImageView[] mBloodImageViewArr;//存放血压体温小圆点的数组

    private RelativeLayout mInformation_rl;//资讯跳转
    private LinearLayout mRegister_ll;//预约挂号
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (handler.hasMessages(1)) {
                handler.removeMessages(1);
            }
            if (msg.what == 1) {
                mViewPagerAD.setCurrentItem(mViewPagerAD.getCurrentItem() + 1);
                handler.sendEmptyMessageDelayed(1, 3000);
            }

        }
    };


    public FirstPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_page, container, false);

        initView(view);

        return view;
    }

    /**
     * 出事化数据
     *
     * @param view
     */
    public void initView(View view) {
        mEdit = (EditText) view.findViewById(R.id.edit_box);
        mScrollRelative = (RelativeLayout) view.findViewById(R.id.search_rl);
        mScrollRelative.setFocusable(true);
        mScrollRelative.setFocusableInTouchMode(true);
        mScrollRelative.requestFocus();
        //医药商城进入
        drugmall_ll= (LinearLayout) view.findViewById(R.id.drugmall_ll);
        drugmall_ll.setOnClickListener(this);
        //常用药品设置adapter
        mGridview = (GridView) view.findViewById(R.id.firstpage_gridview_id);
        mUseDrugAdapter = new UseDrugGridViewAdapter(this.getContext());
        mGridview.setAdapter(mUseDrugAdapter);
        //给资讯设置adapter
        mFirstPageListView = (InformationListView) view.findViewById(R.id.listview_firstpage);
        mListViewAdapter = new FirstPageListViewAdapter(this.getContext());
        mFirstPageListView.setAdapter(mListViewAdapter);
        //点击跳转资讯详情
        mFirstPageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getContext(),InformationDetailsActivity.class));
            }
        });
        //广告viewpager
        mViewPagerAD = (ViewPager) view.findViewById(R.id.viewpager_title);
        //初始化存放小圆点的容器与viewpager
        mGroup = (ViewGroup) view.findViewById(R.id.viewGroup);
        mListAd.add(R.mipmap.item01);
        mListAd.add(R.mipmap.item02);
        mListAd.add(R.mipmap.item03);
        mListAd.add(R.mipmap.item04);
        mListAd.add(R.mipmap.item05);
        //初始化广告轮播图的小图标，以及设置viewpager滑动监听和自动轮播
        setADCircleImg();

        //血压图和体温图viewpager
        initBloodData();//初始化血压数据
        initTemData();//初始化体温
        mViewPagerBlood = (ViewPager) view.findViewById(R.id.viewpager_blood_tem);
        mBloodView = new BloodView(this.getContext());
        mBloodView.setInfo(YbloodNum, XdateNum, heightBloodData, lowBloodData);
        mTemView = new TemView(this.getContext());
        mTemView.setTemInfo(YTemData, XTemdateNum, temData);

        mViewList.add(mBloodView);
        mViewList.add(mTemView);
        mBloodTemAdapter = new ViewPagerBloodTemAdapter(this.getContext(), mViewList);
        mBloodGroup = (ViewGroup) view.findViewById(R.id.blood_viewGroup);
        setBloodTemImg();

        //跳转资讯
        mInformation_rl= (RelativeLayout) view.findViewById(R.id.relative_information);
        mInformation_rl.setOnClickListener(this);
        //预约挂号
        mRegister_ll= (LinearLayout) view.findViewById(R.id.register_ll);
        mRegister_ll.setOnClickListener(this);
    }
    /**
     * 初始化广告轮播图的小图标，以及设置viewpager滑动监听和自动轮播
     */
    public void setADCircleImg() {
        //只有轮播的图片张数不为0时，才执行下面内容
        if (mListAd.size() != 0) {
            //将小圆点根据条件添加到容器中
            mArrImageView = new ImageView[mListAd.size()];
            for (int i = 0; i < mListAd.size(); i++) {
                mCircleImg = new ImageView(this.getContext());
                //小圆点的布局
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginStart(14);
                //给小圆点设置布局
                mCircleImg.setLayoutParams(layoutParams);
                //为存放小圆点的数组赋值
                mArrImageView[i] = mCircleImg;
                //当轮播的图片为一张时，不用设置小圆圈
                if (mListAd.size() == 1) {
                    break;
                }
                //默认第一页为白色的小圆圈(前提必须是轮播的图片大于1张)
                if (i == 0) {
                    mCircleImg.setBackgroundResource(R.mipmap.select_ad);
                } else {
                    mCircleImg.setBackgroundResource(R.mipmap.no_select_ad);
                }
                //将每一个小圆点添加到容器中
                mGroup.addView(mCircleImg);
            }
            mViewPagerAD.setAdapter(new ViewPagerAdAdapter(this.getContext(), mListAd));
            mViewPagerAD.setCurrentItem(0);
            //当数据大于1条时，才可以滑动监听，才可以延迟发送消息进行轮播
            if (mListAd.size() > 1) {
                mViewPagerAD.addOnPageChangeListener(new AdListenerImpl(mArrImageView, handler, mViewPagerAD, mListAd));
                //开始轮播效果
                handler.sendEmptyMessageDelayed(1, 3000);
            }
        }
    }

    /**
     * 血压和体温底部小图标初始化，viewpager设置监听
     */
    public void setBloodTemImg() {
        //只有轮播的图片张数不为0时，才执行下面内容
        if (mViewList.size() != 0) {
            //将小圆点根据条件添加到容器中
            mBloodImageViewArr = new ImageView[mViewList.size()];
            for (int i = 0; i < mViewList.size(); i++) {
                mCircleImg = new ImageView(this.getContext());
                //小圆点的布局
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginStart(14);
                //给小圆点设置布局
                mCircleImg.setLayoutParams(layoutParams);

                //为存放小圆点的数组赋值
                mBloodImageViewArr[i] = mCircleImg;
                //当轮播的图片为一张时，不用设置小圆圈
                if (mViewList.size() == 1) {
                    break;
                }
                //默认第一页为白色的小圆圈(前提必须是轮播的图片大于1张)
                if (i == 0) {
                    mCircleImg.setBackgroundResource(R.mipmap.select_ad);
                } else {
                    mCircleImg.setBackgroundResource(R.mipmap.no_select_ad);
                }
                //将每一个小圆点添加到容器中
                mBloodGroup.addView(mCircleImg);
            }

            mViewPagerBlood.setAdapter(mBloodTemAdapter);
            mViewPagerBlood.setCurrentItem(0);
            //当数据大于1条时，才可以滑动监听
            if (mViewList.size() > 1) {
                mViewPagerBlood.addOnPageChangeListener(new BloodTemImpl(mBloodImageViewArr));
            }
        }
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
    public void onClick(View v) {
        int id=v.getId();
        if (id==mInformation_rl.getId()){//资讯跳转
            startActivity(new Intent(this.getContext(),InformationActivity.class));
        }else if (id==mRegister_ll.getId()){//预约挂号
            startActivity(new Intent(this.getContext(),AppointmentActivity.class));
        }
        else if (id==R.id.drugmall_ll){
            Intent intent=new Intent();
            intent.setClass(getActivity(),MS_home_Activity.class);
            startActivity(intent);
        }
    }
}
