package com.technology.yuyi.activity;

import android.graphics.ImageFormat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.technology.yuyi.R;

public class ConnectWifiActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private Button mConfigBtn;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_wifi);
        initView();
    }

    public void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.wifi_back);
        mBack.setOnClickListener(this);
        //配置按钮
        mConfigBtn = (Button) findViewById(R.id.config_btn);
        mConfigBtn.setOnClickListener(this);
        //弹框

        mBuilder=new AlertDialog.Builder(this);
        mAlertDialog=mBuilder.create();
        mAlertDialog.setView(LayoutInflater.from(this).inflate(R.layout.wifi_fail_alert,null));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        } else if (id == mConfigBtn.getId()) {
            mAlertDialog.show();
            setAlertWidth();
        }
    }

    //设置alert宽度
    public void setAlertWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = mAlertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (dm.widthPixels * (0.7));
        mAlertDialog.getWindow().setAttributes(p);//设置生效
    }
}
