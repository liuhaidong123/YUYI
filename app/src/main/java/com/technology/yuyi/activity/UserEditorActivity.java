package com.technology.yuyi.activity;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.technology.yuyi.R;

public class UserEditorActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mSex;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private View mSexAlertView;
    private ImageView mBack;
    private EditText mAgeEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editor);
        initView();

    }

    private void initView() {
        //选择性别
        mSex = (RelativeLayout) findViewById(R.id.user_sex_rl);
        mSex.setOnClickListener(this);
        mBuilder = new AlertDialog.Builder(this);
        mAlertDialog = mBuilder.create();
        mSexAlertView = LayoutInflater.from(this).inflate(R.layout.sex_alert_box, null);
        mAlertDialog.setView(mSexAlertView);
        //返回
        mBack = (ImageView) findViewById(R.id.editor_back);
        mBack.setOnClickListener(this);
        //年龄编辑框
        mAgeEdit = (EditText) findViewById(R.id.age_edit);
        mAgeEdit.setSelection(mAgeEdit.getText().length());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mSex.getId()) {//点击个人编辑性别
            mAlertDialog.show();
            setAlertWidth();
        } else if (id == mBack.getId()) {//返回
            finish();
        }
    }

    //设置alert宽度
    public void setAlertWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = mAlertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (dm.widthPixels * (0.7));
        mAlertDialog.getWindow().setAttributes(p);//设置生效
    }
}
