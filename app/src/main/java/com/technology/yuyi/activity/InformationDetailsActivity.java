package com.technology.yuyi.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.ADmessageBean.Root;
import com.technology.yuyi.bean.FirstPageInformationTwoDataRoot;
import com.technology.yuyi.bean.Information;
import com.technology.yuyi.lhd.utils.ToastUtils;

public class InformationDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mHospitalMess;
    private ImageView mBack;

    private ImageView mHospital_img;
    private TextView mHospital_name;
    private TextView mHospital_grade;
    private TextView mHospital_message;
    private TextView tv_jj;

    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 23) {//医院
                Object o = msg.obj;
                if (o != null && o instanceof Information) {
                    Information information = (Information) o;
                    Picasso.with(InformationDetailsActivity.this).setIndicatorsEnabled(true);
                    Picasso.with(InformationDetailsActivity.this).load(UrlTools.BASE + information.getPicture()).into(mHospital_img);

                    mHospital_name.setText(information.getHospitalName());
                    mHospital_grade.setText(information.getGradeName());
                    mHospital_message.setText(information.getIntroduction());
                }
            } else if (msg.what == 204) {//医院详情失败
                ToastUtils.myToast(InformationDetailsActivity.this, "请求失败");
            } else if (msg.what == 34) {//广告
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    Picasso.with(InformationDetailsActivity.this).load(UrlTools.BASE + root.getPicture()).error(R.mipmap.error_big).into(mHospital_img);
                    mHospital_name.setText(root.getTitle());
                    mHospital_grade.setText(root.getSmalltitle());
                    mHospital_message.setText(root.getArticleText());
                    tv_jj.setText(root.getType());
                }
            } else if (msg.what == 224) {//广告详情失败
                ToastUtils.myToast(InformationDetailsActivity.this, "请求失败");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_details);
        initView();
    }

    public void initView() {
        //根据传过来的id,查询对应的医院详情
        mHttptools = HttpTools.getHttpToolsInstance();
        //医院资讯
        if (getIntent().getStringExtra("type").equals("information")) {
            mHttptools.getFirstPageInformationTwoDataMessage(handler, getIntent().getIntExtra("id", -1));
        }
        //广告资讯
        if (getIntent().getStringExtra("type").equals("ad")) {
            mHttptools.getAdMessageData(handler, getIntent().getIntExtra("id", -1));
        }


        mHospitalMess = (TextView) findViewById(R.id.hospitals_mess);
        mBack = (ImageView) findViewById(R.id.details_back);
        mBack.setOnClickListener(this);

        mHospital_img = (ImageView) findViewById(R.id.details_img);
        mHospital_name = (TextView) findViewById(R.id.hospital_name);
        mHospital_grade = (TextView) findViewById(R.id.hospital_grade);
        mHospital_message = (TextView) findViewById(R.id.hospitals_mess);
        tv_jj = (TextView) findViewById(R.id.hospitals_jj);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }
    }
}
