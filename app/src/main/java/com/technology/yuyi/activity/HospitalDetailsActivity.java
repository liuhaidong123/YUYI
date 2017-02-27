package com.technology.yuyi.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.technology.yuyi.R;

public class HospitalDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mBgRelative;
    private ImageView mBack;
    private Button mBtn;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private View mAlertView;
    private Button mSpeechBtn;
    private Button mVideoBtn;
    private Button mCharBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);
        initView();
    }

    public void initView() {
        mBack = (ImageView) findViewById(R.id.detail_back);
        mBack.setOnClickListener(this);
        //设置透明度
        mBgRelative = (RelativeLayout) findViewById(R.id.bg_relative);
        mBgRelative.getBackground().setAlpha(125);
        //医患咨询
        mBtn = (Button) findViewById(R.id.ask_btn_sure);
        mBtn.setOnClickListener(this);
        //弹框
        mBuilder = new AlertDialog.Builder(this);
        mAlertDialog = mBuilder.create();
        mAlertView = LayoutInflater.from(this).inflate(R.layout.ask_alert_box, null);
        mAlertDialog.setView(mAlertView);

        //语音
        mSpeechBtn = (Button) mAlertView.findViewById(R.id.btn_speech);
        mVideoBtn = (Button) mAlertView.findViewById(R.id.btn_video);
        mCharBtn = (Button) mAlertView.findViewById(R.id.btn_char);
        mSpeechBtn.setOnClickListener(this);
        mVideoBtn.setOnClickListener(this);
        mCharBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBtn.getId()) {
            mAlertDialog.show();
            setAlertWidth();
        } else if (id == mSpeechBtn.getId()) {
            mAlertDialog.dismiss();
        } else if (id == mVideoBtn.getId()) {
            mAlertDialog.dismiss();
        } else if (id == mCharBtn.getId()) {
            mAlertDialog.dismiss();
        } else if (id == mBack.getId()) {
            finish();
        }
    }

    //设置alert宽度
    public void setAlertWidth() {
        DisplayMetrics dm=new DisplayMetrics();
        WindowManager m = getWindowManager();
       m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = mAlertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int)(dm.widthPixels*(0.7));
        mAlertDialog.getWindow().setAttributes(p);//设置生效
    }
}
