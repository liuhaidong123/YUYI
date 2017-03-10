package com.technology.yuyi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.technology.yuyi.R;


/**
 * Created by wanyu on 2017/3/1.
 */

public class My_settings_feedbackIdea_Activity extends Activity {
    private EditText my_settings_idea_editIdea,my_settings_idea_editContact;
    private TextView my_settings_idea_textNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_settings_feedbackidea);
        initView();

    }

    private void initView() {

        my_settings_idea_editIdea= (EditText) findViewById(R.id.my_settings_idea_editIdea);
        my_settings_idea_editContact= (EditText) findViewById(R.id.my_settings_idea_editContact);
        my_settings_idea_textNum= (TextView) findViewById(R.id.my_settings_idea_textNum);

        my_settings_idea_editIdea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text=s.toString();
                if (!"".equals(s)&&!TextUtils.isEmpty(text)){
                    int length=text.length();
                    my_settings_idea_textNum.setText(length+"/"+200);
                }
                else {
                    my_settings_idea_textNum.setText(0+"/"+200);
                }
            }
        });

    }
    public void goBack(View view){
        if (view!=null){
            if (view.getId()==R.id.activity_idea_include_imageReturn){
                finish();
            }
        }
    }
//提交意见和联系方式
    public void submitIdea(View view) {
        if (view!=null){
            if (view.getId()==R.id.my_settings_idea_submit){
                String idea=my_settings_idea_editIdea.getText().toString();
                String contact=my_settings_idea_editContact.getText().toString();
            }
        }

    }
}
