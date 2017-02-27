package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyi.R;

public class InformationDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mHospitalMess;
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_details);
        initView();
    }
    public void initView(){
        mHospitalMess= (TextView) findViewById(R.id.hospitals_mess);
        mHospitalMess.setMovementMethod(ScrollingMovementMethod.getInstance());
        mBack= (ImageView) findViewById(R.id.details_back);
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
