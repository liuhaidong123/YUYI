package com.technology.yuyi.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.lzh_utils.MyActivity;

/**
 * Created by wanyu on 2017/3/10.
 * String result = writer.toString();
 * Intent intent=new Intent(mContext, Exception_catch_Activity.class);
 * intent.putExtra("exception",result);
 * mContext.startActivity(intent);
 */

public class Exception_catch_Activity extends MyActivity {
    private TextView exception_textv, title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception_catch);
        title.setText("程序异常信息");
        String exception = getIntent().getStringExtra("exception");
        exception_textv.setText(exception);
    }
}
