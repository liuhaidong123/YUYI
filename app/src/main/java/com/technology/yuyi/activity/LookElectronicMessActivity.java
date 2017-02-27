package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyi.R;

public class LookElectronicMessActivity extends AppCompatActivity {
private ImageView mBack;
    private TextView mText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_electronic_mess);
        //返回
        mBack= (ImageView) findViewById(R.id.elec_mess_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置textview文字过多时，可以垂直滚动
        mText= (TextView) findViewById(R.id.tv_now_disease);
        mText.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
