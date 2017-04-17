package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.ViewPagerBloodTemAdapter;
import com.technology.yuyi.bean.FirstPageUserDataBean.BloodpressureList;
import com.technology.yuyi.bean.FirstPageUserDataBean.TemperatureList;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.BloodView;
import com.technology.yuyi.myview.RoundImageView;
import com.technology.yuyi.myview.TemView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyDataAnalyseActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private ViewPager mViewPager;
    private ViewPagerBloodTemAdapter mAdapter;
    private List<View> mList = new ArrayList<>(); //血压图和体温图
    private BloodView mBloodView;
    private TemView mTemView;
    private ArrayList<Integer> YbloodNum = new ArrayList<>();//y轴血压数据
    private ArrayList<String> XdateNum = new ArrayList<>();//x轴日期数据
    private ArrayList<Integer> heightBloodData = new ArrayList<>();  //血压高压数据
    private ArrayList<Integer> lowBloodData = new ArrayList<>();//血压低压数据

    private ArrayList<Integer> YTemData = new ArrayList<>();//Y轴温度
    private ArrayList<String> XTemdateNum = new ArrayList<>();  //x轴日期数据
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
    private String redColor = "#ec2e2e";
    private ImageView mBack;
    private HttpTools mHttptools;
    private SimpleDateFormat simpleDateFormat;
    private ImageView mPromptImg;
    private TextView mPromptTv;
    private TextView mHeightBloodTv;
    private TextView mLowBloodTv;

    private ImageView mTemPromptImg;
    private TextView mTemPromptTv;
    private TextView mTem;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==39){
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.FirstPageClickUserBean.Root) {
                    MyDialog.stopDia();
                    com.technology.yuyi.bean.FirstPageClickUserBean.Root root = (com.technology.yuyi.bean.FirstPageClickUserBean.Root) o;
                    XdateNum.clear();
                    heightBloodData.clear();
                    lowBloodData.clear();
                    XTemdateNum.clear();
                    temData.clear();
                    int month = 0;
                    int day = 0;

                    List<com.technology.yuyi.bean.FirstPageClickUserBean.BloodpressureList> bloodlist = root.getResult().getBloodpressureList();
                    List<com.technology.yuyi.bean.FirstPageClickUserBean.TemperatureList> temlist = root.getResult().getTemperatureList();
                    //血压
                    if (bloodlist.size() != 0) {
                        for (int i = 0; i < bloodlist.size(); i++) {
                            try {
                                Date date = simpleDateFormat.parse(bloodlist.get(i).getCreateTimeString());
                                month = date.getMonth() + 1;
                                day = date.getDate();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String date = month + "月" + day + "日";
                            XdateNum.add(date);
                            heightBloodData.add(bloodlist.get(i).getSystolic());//高
                            lowBloodData.add(bloodlist.get(i).getDiastolic());
                        }

                    }

                    //填补血压日期
                    if (XdateNum.size()!=7){
                        Calendar calendarBlood=Calendar.getInstance();
                        int dayNum=7-XdateNum.size();
                        if (XdateNum.size()==0){
                            for (int i=0;i<dayNum;i++){
                                int month2= calendarBlood.get(Calendar.MONTH)+1;
                                int day2=calendarBlood.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XdateNum.add(date2);
                                calendarBlood.add(Calendar.DAY_OF_MONTH,1);
                            }
                        }else {
                            for (int i=0;i<dayNum;i++){
                                calendarBlood.add(Calendar.DAY_OF_MONTH,1);
                                int month2= calendarBlood.get(Calendar.MONTH)+1;
                                int day2=calendarBlood.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XdateNum.add(date2);
                            }
                        }

                    }

                    // 体温
                    if (temlist.size() != 0) {
                        for (int i = 0; i < temlist.size(); i++) {
                            try {
                                Date date = simpleDateFormat.parse(temlist.get(i).getCreateTimeString());
                                month = date.getMonth() + 1;
                                day = date.getDate();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String date = month + "月" + day + "日";
                            XTemdateNum.add(date);
                            temData.add(temlist.get(i).getTemperaturet());//体温

                        }

                    }


                    //填补体温日期
                    if (XTemdateNum.size()!=7){
                        Calendar calendarTem=Calendar.getInstance();
                        int dayNum=7-XTemdateNum.size();
                        //没有数据时，从当前日期开始
                        if (XTemdateNum.size()==0){
                            for (int i=0;i<dayNum;i++){
                                int month2= calendarTem.get(Calendar.MONTH)+1;
                                int day2=calendarTem.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XTemdateNum.add(date2);
                                calendarTem.add(Calendar.DAY_OF_MONTH,1);
                            }
                            //有数据时，从当前日期的下一天开始
                        }else {
                            for (int i=0;i<dayNum;i++){
                                calendarTem.add(Calendar.DAY_OF_MONTH,1);
                                int month2= calendarTem.get(Calendar.MONTH)+1;
                                int day2=calendarTem.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XTemdateNum.add(date2);
                            }
                        }

                    }
                    mBloodView.setInfo(YbloodNum, XdateNum, heightBloodData, lowBloodData);
                    mBloodView.invalidate();
                    mTemView.setTemInfo(YTemData, XTemdateNum, temData);
                    mTemView.invalidate();

                    //判断数据是否正常，设置文字图片提示
                    if (bloodlist.size() != 0 && temlist.size() != 0) {
                        checkBlood(bloodlist.get(bloodlist.size() - 1).getSystolic(), bloodlist.get(bloodlist.size() - 1).getDiastolic(), temlist.get(temlist.size() - 1).getTemperaturet());
                    } else if (bloodlist.size() == 0 && temlist.size() != 0) {
                        checkBlood(0, 0, temlist.get(temlist.size() - 1).getTemperaturet());
                    } else if (bloodlist.size() != 0 && temlist.size() == 0) {
                        checkBlood(bloodlist.get(bloodlist.size() - 1).getSystolic(), bloodlist.get(bloodlist.size() - 1).getDiastolic(), 0);
                    } else {
                        checkBlood(0, 0, 0);
                    }

                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_data_analyse);
        initView();
    }

    public void initView() {
        mHttptools=HttpTools.getHttpToolsInstance();
        mHttptools.getClickUserDataData(mHandler, user.token,getIntent().getIntExtra("id",-1));
        //时间
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //血压图和体温图viewpager
        initBloodData();//初始化血压数据
        initTemData();//初始化体温


        mBloodView = new BloodView(this);
        mTemView = new TemView(this);
        mList.add(mBloodView);
        mList.add(mTemView);
        mViewPager = (ViewPager) findViewById(R.id.my_data_viewpager);
        mAdapter = new ViewPagerBloodTemAdapter(this, mList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

        //用户最后一条数据显示在标题那
        mPromptImg = (ImageView)findViewById(R.id.normal_btn_img);
        mPromptTv = (TextView)findViewById(R.id.normal_tv);
        mHeightBloodTv = (TextView)findViewById(R.id.height_blood_num_tv);
        mLowBloodTv = (TextView) findViewById(R.id.low_blood_num_tv);

        mTemPromptImg = (ImageView)findViewById(R.id.normal_btn_img);
        mTemPromptTv = (TextView)findViewById(R.id.tem_normal_tv);
        mTem = (TextView) findViewById(R.id.tem_num_tv);

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

    }

    /**
     * 初始化体温折线图数据
     */

    public void initTemData() {

        for (int i = 35; i < 43; i++) {
            YTemData.add(i);
        }
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

    /**
     * 判断血压,体温设置提示
     * height 高压
     * low 低压
     * tem 体温
     */
    public void checkBlood(int height, int low, float tem) {
        mPromptImg = (ImageView)findViewById(R.id.normal_btn_img);
        mPromptTv = (TextView)findViewById(R.id.normal_tv);
        mHeightBloodTv = (TextView)findViewById(R.id.height_blood_num_tv);
        mLowBloodTv = (TextView) findViewById(R.id.low_blood_num_tv);

        mTemPromptImg = (ImageView)findViewById(R.id.tem_btn_img);
        mTemPromptTv = (TextView)findViewById(R.id.tem_normal_tv);
        mTem = (TextView) findViewById(R.id.tem_num_tv);

        //血压
        if (height == 0 && low == 0 ) {
            mPromptImg.setImageResource(R.mipmap.normal);
            mPromptTv.setText("待测");
            mPromptTv.setTextColor(Color.parseColor(mGayColor));

        }else {
            if (height > 139 ) {
                mPromptImg.setImageResource(R.mipmap.height);
                mPromptTv.setText("偏高");
                mPromptTv.setTextColor(Color.parseColor(redColor));

            } else if(height <90){
                mPromptImg.setImageResource(R.mipmap.low);
                mPromptTv.setText("偏低");
                mPromptTv.setTextColor(Color.parseColor(redColor));
            }else {
                mPromptImg.setImageResource(R.mipmap.normal);
                mPromptTv.setText("正常");
                mPromptTv.setTextColor(Color.parseColor(mGayColor));
            }
        }

        //体温
        if(tem == 0){
            mTemPromptImg.setImageResource(R.mipmap.normal);
            mTemPromptTv.setText("待测");
            mTemPromptTv.setTextColor(Color.parseColor(mGayColor));
        }else {
            if (tem>37.5){
                mTemPromptImg.setImageResource(R.mipmap.height);
                mTemPromptTv.setText("偏高");
                mTemPromptTv.setTextColor(Color.parseColor(redColor));
            }else if (tem<36){
                mTemPromptImg.setImageResource(R.mipmap.low);
                mTemPromptTv.setText("偏低");
                mTemPromptTv.setTextColor(Color.parseColor(redColor));
            }else {
                mTemPromptImg.setImageResource(R.mipmap.normal);
                mTemPromptTv.setText("正常");
                mTemPromptTv.setTextColor(Color.parseColor(mGayColor));
            }
        }


        mHeightBloodTv.setText(height + "");
        mLowBloodTv.setText(low + "");
        mTem.setText(tem + "°C");
    }



}
