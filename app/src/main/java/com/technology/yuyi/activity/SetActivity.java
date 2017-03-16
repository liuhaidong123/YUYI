package com.technology.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.technology.yuyi.R;
import com.technology.yuyi.lzh_utils.user;

public class SetActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        initView();
    }

    //初始化数据
    public void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.set_mess_back);
        mBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }
    }

    public void settingUp(View v) {
        if (v != null) {
            switch (v.getId()) {
                    case R.id.tv_contact://联系我们
                        startActivity(new Intent(SetActivity.this,My_settings_contactOur_Activity.class));
                        break;

                    case R.id.tv_suggestion://意见反馈
                        startActivity(new Intent(SetActivity.this,My_settings_feedbackIdea_Activity.class));
                        break;

                    case R.id.tv_about_ours://关于我们
                        startActivity(new Intent(SetActivity.this,My_settings_aboutOurs_Activity.class));
                        break;

                    case R.id.tv_exit://退出
                        user.clearLogin(this);
                        startActivity(new Intent(SetActivity.this,My_userLogin_Activity.class));
                        finish();
                        break;
            }
        }
    }
}