package com.technology.yuyi.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.technology.yuyi.R;

public class VoiceActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mVoice_img;
    private RelativeLayout mBg_rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        mVoice_img = (ImageView) findViewById(R.id.img_voice);
        mVoice_img.setOnClickListener(this);

        mBg_rl = (RelativeLayout) findViewById(R.id.activity_voice);
        mBg_rl.getBackground().setAlpha(125);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mVoice_img.getId()) {
            startActivity(new Intent(this, VoiceSuccessActivity.class));
            finish();
        }
    }
}
