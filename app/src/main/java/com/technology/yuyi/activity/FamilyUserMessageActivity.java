package com.technology.yuyi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyi.R;

public class FamilyUserMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;//返回
    private RelativeLayout mMyData_rl;//我的数据分析
    private RelativeLayout mMyFiles_rl;//我的病历档案
    private ImageView mEditImg;
    private Button mDelete_btn;//删除用户
    private AlertDialog.Builder mBuilder;//删除设备的弹框
    private AlertDialog mAlertDialog;
    private View mAlertView;
    private TextView mSure_btn;
    private  TextView mCancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_user_message);
        initView();
    }

    public void initView() {
        //返回
        mBack = (ImageView) findViewById(R.id.family_user_back);
        mBack.setOnClickListener(this);
        //我的数据分析
        mMyData_rl = (RelativeLayout) findViewById(R.id.my_data_rl);
        mMyData_rl.setOnClickListener(this);

        //我的病历档案
        mMyFiles_rl = (RelativeLayout) findViewById(R.id.my_files_rl);
        mMyFiles_rl.setOnClickListener(this);
        //编辑图片
        mEditImg = (ImageView) findViewById(R.id.edit_img);
        mEditImg.setOnClickListener(this);
        //删除用户
        mDelete_btn = (Button) findViewById(R.id.delete_user);
        mDelete_btn.setOnClickListener(this);

        //删除提示弹框
        mAlertView = LayoutInflater.from(this).inflate(R.layout.alert_delete_user_box, null);
        //确定按钮
        mSure_btn= (TextView) mAlertView.findViewById(R.id.alert_sure);
        mSure_btn.setOnClickListener(this);
        //取消按钮
        mCancel_btn= (TextView) mAlertView.findViewById(R.id.alert_cancel);
        mCancel_btn.setOnClickListener(this);
        //长安设备的弹框
        mBuilder = new AlertDialog.Builder(this);
        mAlertDialog = mBuilder.create();
        mAlertDialog.setView(mAlertView);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        } else if (id == mMyData_rl.getId()) {//我的数据分析
            startActivity(new Intent(this, MyDataAnalyseActivity.class));
        } else if (id == mEditImg.getId()) {//编辑图片(将用户信息传过去)
          Intent intent=  new Intent(this, AddFamilyUserActivity.class);
            intent.putExtra("title","修改家庭用户");
            startActivity(intent);

        } else if (id == mMyFiles_rl.getId()) {//病历档案(将用户信息传过去)
            startActivity(new Intent(this, LookElectronicMessActivity.class));
        } else if (id == mDelete_btn.getId()) {//删除用户
            mAlertDialog.show();
        }else if (id==mSure_btn.getId()){//确定按钮
            mAlertDialog.dismiss();
        }else if (id==mCancel_btn.getId()){//取消按钮
            mAlertDialog.dismiss();
        }

    }
}
