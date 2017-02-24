package com.technology.yuyi.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.technology.yuyi.R;

public class MS_home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_home);
    }



//    view的点击事件
    public void ms_homeClick(View v){
        if (v!=null){
            switch (v.getId()){
                case R.id.ms_home_return://首页返回按钮
                    break;
            }
        }

    }
}
