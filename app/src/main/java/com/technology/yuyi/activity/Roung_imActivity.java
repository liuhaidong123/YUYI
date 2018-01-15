package com.technology.yuyi.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;


import com.technology.yuyi.R;


import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;


/**
 * Created by wanyu on 2017/3/20.
 */

public class Roung_imActivity extends FragmentActivity{
    private String title;
    private TextView im_rong_title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_rong);
        im_rong_title= (TextView) findViewById(R.id.im_rong_title);
        im_rong_title.setText(getIntent().getData().getQueryParameter("title"));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }



    public void goBack(View view){
        if (view!=null){
            finish();
        }
    }
}
