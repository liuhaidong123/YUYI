package com.technology.yuyi.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.lzh_utils.MyActivity;

public class My_settings_contactOur_Activity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings_contact_our_);
        initView();
    }

    private void initView() {
        titleTextView= (TextView) findViewById(R.id.activity_include_title);
        setTitleText("联系我们");
    }

    //拨打电话的按钮
    public void callPhone(View view) {
        if (view!=null){
            if (view.getId()==R.id.my_settings_contactOur_callPhone){
                Intent intent = new Intent();//创建一个意图对象，用来激发拨号的Activity
                intent.setAction("android.intent.action.DIAL");//android.intent.action.CALL
                intent.setData(Uri.parse("tel:"+getResources().getString(R.string.my_settings_contactOur_phoneNum)));
                startActivity(intent);//方法内部会自动添加类别,android.intent.category.DEFAULT
        }
        }
    }
}
