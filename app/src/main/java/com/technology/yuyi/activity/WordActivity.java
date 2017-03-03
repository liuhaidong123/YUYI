package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.technology.yuyi.R;

public class WordActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        initView();
    }

    public void initView() {
        mBack= (ImageView) findViewById(R.id.word_back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBack.getId()){
            finish();
        }

    }
}
