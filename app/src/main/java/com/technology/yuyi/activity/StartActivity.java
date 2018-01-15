package com.technology.yuyi.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.technology.yuyi.R;
import com.technology.yuyi.lzh_utils.user;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtn;
    private int mTime=3;//跳转时间
    private String usid;
        private Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==1){
                    if(mTime>=0){
                        mBtn.setText(mTime--+"S后跳转");
                        handler.sendEmptyMessageDelayed(1,1000);
                    }else {
                        if (user.isLogin(StartActivity.this)){
                            Intent intent=new Intent(StartActivity.this,MainActivity.class);
                            startActivity(intent);
                            handler.removeMessages(1);
                            finish();
                        }else {
                            Intent intent=new Intent(StartActivity.this,My_userLogin_Activity.class);
                            startActivity(intent);
                            finish();
                            handler.removeMessages(1);
                        }

                    }
                }
            }
        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mBtn= (Button) findViewById(R.id.intent_id_btnt);
        mBtn.setOnClickListener(this);
        mBtn.getBackground().setAlpha(55);
        handler.sendEmptyMessageDelayed(1,200);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBtn.getId()){
            handler.removeMessages(1);
            if (user.isLogin(this)){
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent=new Intent(StartActivity.this,My_userLogin_Activity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeMessages(1);
    }
}
