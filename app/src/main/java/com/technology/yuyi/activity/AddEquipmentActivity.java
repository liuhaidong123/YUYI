package com.technology.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.technology.yuyi.R;

public class AddEquipmentActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;//返回
    private Button mScanningZXBtn;//扫描二维码（没有做扫描二维码呢，先跳到连接WiFi页面）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_equipment);
        initView();
    }


    public void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.equip_back);
        mBack.setOnClickListener(this);
        //扫描二维码
        mScanningZXBtn = (Button) findViewById(R.id.btn_zx);
        mScanningZXBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        } else if (id == mScanningZXBtn.getId()) {
            //扫描二维码（没有做扫描二维码呢，先跳到连接WiFi页面）
            startActivity(new Intent(this, ConnectWifiActivity.class));
        }
    }
}
