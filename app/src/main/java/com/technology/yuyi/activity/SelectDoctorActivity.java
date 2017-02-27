package com.technology.yuyi.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.SelectDoctorListViewAdapter;

import java.util.ArrayList;

public class SelectDoctorActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private ListView mListView;
    private SelectDoctorListViewAdapter mAdapter;

    private TextView mDate1;//日期
    private TextView mDate1Morning;//上午
    private TextView mDate1Afternoon;//下午
    private TextView mDate2;
    private TextView mDate2Morning;
    private TextView mDate2Afternoon;
    private TextView mDate3;
    private TextView mDate3Morning;
    private TextView mDate3Afternoon;
    private TextView mDate4;
    private TextView mDate4Morning;
    private TextView mDate4Afternoon;
    private TextView mDate5;
    private TextView mDate5Morning;
    private TextView mDate5Afternoon;
    private TextView mDate6;
    private TextView mDate6Morning;
    private TextView mDate6Afternoon;
    private TextView mDate7;
    private TextView mDate7Morning;
    private TextView mDate7Afternoon;
    private final String mGreen = "#25f368";
    private final String mGay = "#6a6a6a";
    private final String mWhite = "#ffffff";

    private ArrayList<TextView> mDate = new ArrayList<>();
    private ArrayList<TextView> mMorninng = new ArrayList<>();
    private ArrayList<TextView> mAfternoon = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_doctor);
        initView();
    }

    public void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.doctor_back);
        mBack.setOnClickListener(this);

        //选择医生ListView
        mListView = (ListView) findViewById(R.id.doctor_listview);
        mAdapter = new SelectDoctorListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        //日期，上午，下午
        initDate();


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
            //第一个日期上午
        } else if (id == mDate1Morning.getId()) {
            setColor(id);
        } else if (id == mDate1Afternoon.getId()) {
            setColor(id);
        } else if (id == mDate2Morning.getId()) {
            setColor(id);
        } else if (id == mDate2Afternoon.getId()) {
            setColor(id);
        } else if (id == mDate3Morning.getId()) {
            setColor(id);
        } else if (id == mDate3Afternoon.getId()) {
            setColor(id);
        } else if (id == mDate4Morning.getId()) {
            setColor(id);
        } else if (id == mDate4Afternoon.getId()) {
            setColor(id);
        } else if (id == mDate5Morning.getId()) {
            setColor(id);
        } else if (id == mDate5Afternoon.getId()) {
            setColor(id);
        } else if (id == mDate6Morning.getId()) {
            setColor(id);
        } else if (id == mDate6Afternoon.getId()) {
            setColor(id);
        } else if (id == mDate7Morning.getId()) {
            setColor(id);
        } else if (id == mDate7Afternoon.getId()) {
            setColor(id);
        }

    }

    //初始化日期，上午，下午
    public void initDate() {
        mDate1 = (TextView) findViewById(R.id.date1);
        mDate1Morning = (TextView) findViewById(R.id.date1_morning);
        mDate1Afternoon = (TextView) findViewById(R.id.date1_afternoon);
        mDate1Morning.setOnClickListener(this);
        mDate1Afternoon.setOnClickListener(this);
        mDate.add(mDate1);
        mMorninng.add(mDate1Morning);
        mAfternoon.add(mDate1Afternoon);

        mDate2 = (TextView) findViewById(R.id.date2);
        mDate2Morning = (TextView) findViewById(R.id.date2_morning);
        mDate2Afternoon = (TextView) findViewById(R.id.date2_afternoon);
        mDate2Morning.setOnClickListener(this);
        mDate2Afternoon.setOnClickListener(this);
        mDate.add(mDate2);
        mMorninng.add(mDate2Morning);
        mAfternoon.add(mDate2Afternoon);

        mDate3 = (TextView) findViewById(R.id.date3);
        mDate3Morning = (TextView) findViewById(R.id.date3_morning);
        mDate3Afternoon = (TextView) findViewById(R.id.date3_afternoon);
        mDate3Morning.setOnClickListener(this);
        mDate3Afternoon.setOnClickListener(this);
        mDate.add(mDate3);
        mMorninng.add(mDate3Morning);
        mAfternoon.add(mDate3Afternoon);

        mDate4 = (TextView) findViewById(R.id.date4);
        mDate4Morning = (TextView) findViewById(R.id.date4_morning);
        mDate4Afternoon = (TextView) findViewById(R.id.date4_afternoon);
        mDate4Morning.setOnClickListener(this);
        mDate4Afternoon.setOnClickListener(this);
        mDate.add(mDate4);
        mMorninng.add(mDate4Morning);
        mAfternoon.add(mDate4Afternoon);

        mDate5 = (TextView) findViewById(R.id.date5);
        mDate5Morning = (TextView) findViewById(R.id.date5_morning);
        mDate5Afternoon = (TextView) findViewById(R.id.date5_afternoon);
        mDate5Morning.setOnClickListener(this);
        mDate5Afternoon.setOnClickListener(this);
        mDate.add(mDate5);
        mMorninng.add(mDate5Morning);
        mAfternoon.add(mDate5Afternoon);

        mDate6 = (TextView) findViewById(R.id.date6);
        mDate6Morning = (TextView) findViewById(R.id.date6_morning);
        mDate6Afternoon = (TextView) findViewById(R.id.date6_afternoon);
        mDate6Morning.setOnClickListener(this);
        mDate6Afternoon.setOnClickListener(this);
        mDate.add(mDate6);
        mMorninng.add(mDate6Morning);
        mAfternoon.add(mDate6Afternoon);

        mDate7 = (TextView) findViewById(R.id.date7);
        mDate7Morning = (TextView) findViewById(R.id.date7_morning);
        mDate7Afternoon = (TextView) findViewById(R.id.date7_afternoon);
        mDate7Morning.setOnClickListener(this);
        mDate7Afternoon.setOnClickListener(this);
        mDate.add(mDate7);
        mMorninng.add(mDate7Morning);
        mAfternoon.add(mDate7Afternoon);

    }

    //点击上午下午设置颜色
    public void setColor(int id) {

        for (int i = 0; i < mMorninng.size(); i++) {
            //如果点击的是上午
            if (id == mMorninng.get(i).getId()) {
                //所有的下午颜色都是灰色，背景都是白色
                for (int j = 0; j < mAfternoon.size(); j++) {
                    mAfternoon.get(j).setTextColor(Color.parseColor(mGay));
                    mAfternoon.get(j).setBackgroundColor(Color.parseColor(mWhite));
                }

                for (int k = 0; k < mMorninng.size(); k++) {
                    //设置选中的上午颜色及日期颜色，和背景都是绿色
                    if (k == i) {
                        mDate.get(k).setTextColor(Color.parseColor(mGreen));
                        mMorninng.get(k).setTextColor(Color.parseColor(mWhite));
                        mMorninng.get(k).setBackgroundColor(Color.parseColor(mGreen));
                        //其他的都是灰色，白色
                    } else {
                        mDate.get(k).setTextColor(Color.parseColor(mGay));
                        mMorninng.get(k).setTextColor(Color.parseColor(mGay));
                        mMorninng.get(k).setBackgroundColor(Color.parseColor(mWhite));
                    }
                }

            }
        }


        for (int j = 0; j < mAfternoon.size(); j++) {
            //如果点击的是下午
            if (id == mAfternoon.get(j).getId()) {
                //所有的上午颜色都是灰色，背景是白色
                for (int i = 0; i < mMorninng.size(); i++) {
                    mMorninng.get(i).setTextColor(Color.parseColor(mGay));
                    mMorninng.get(i).setBackgroundColor(Color.parseColor(mWhite));
                }

                //设置选中的下午
                for (int k = 0; k < mAfternoon.size(); k++) {
                    //当前点击的下午所有颜色
                    if (k == j) {
                        //设置选中的下午颜色及日期颜色，和背景
                        mDate.get(k).setTextColor(Color.parseColor(mGreen));
                        mAfternoon.get(k).setTextColor(Color.parseColor(mWhite));
                        mAfternoon.get(k).setBackgroundColor(Color.parseColor(mGreen));
                    } else {
                        //设置选中的下午颜色及日期颜色，和背景
                        mDate.get(k).setTextColor(Color.parseColor(mGay));
                        mAfternoon.get(k).setTextColor(Color.parseColor(mGay));
                        mAfternoon.get(k).setBackgroundColor(Color.parseColor(mWhite));
                    }
                }

            }
        }

    }
}
