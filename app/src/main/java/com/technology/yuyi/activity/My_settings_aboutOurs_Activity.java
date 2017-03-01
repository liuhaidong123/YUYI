package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.lzh_utils.MyActivity;
import com.technology.yuyi.lzh_utils.XCRoundRectImageView;

//关于我们
public class My_settings_aboutOurs_Activity extends MyActivity {
    private XCRoundRectImageView my_settings_aboutOurs_imageview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings_about_ours);
        initView();
    }

    private void initView() {
        titleTextView= (TextView) findViewById(R.id.activity_include_title);
        setTitleText("关于我们");
        my_settings_aboutOurs_imageview= (XCRoundRectImageView) findViewById(R.id.my_settings_aboutOurs_imageview);
        my_settings_aboutOurs_imageview.setRadios(12.5f);
    }
}
