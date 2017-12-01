package com.technology.yuyi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.ADmessageBean.Root;
import com.technology.yuyi.bean.UpdatedFirstPageTwoDataBean.UpdatedInformation;
import com.technology.yuyi.lhd.utils.ToastUtils;

public class InformationDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mHospitalMess;
    private ImageView mBack;

    private ImageView mHospital_img;
    private TextView mHospital_name;
    private TextView mHospital_grade;
    private TextView mHospital_message;
    private TextView tv_jj;

    private HttpTools mHttptools;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 43) {//医院
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.NewInforAdDetails.Root) {
                    com.technology.yuyi.bean.NewInforAdDetails.Root information = (com.technology.yuyi.bean.NewInforAdDetails.Root) o;

                   if (information.getCode().equals("0")){

                       if ("".equals(information.getResult().getPicture())){
                           mHospital_img.setImageResource(R.mipmap.errorpicture);
                       }else {
                           String[]strings= information.getResult().getPicture().split(";");
                           Picasso.with(getApplicationContext()).load(UrlTools.BASE+strings[0]).error(R.mipmap.errorpicture).into(mHospital_img);
                       }
                       mHospital_name.setText(information.getResult().getTitle());
                       mHospital_grade.setText(information.getResult().getSmallTitle());
                       mHospital_message.setText(information.getResult().getContent());
                   }else {
                       ToastUtils.myToast(InformationDetailsActivity.this, "获取详情错误");
                   }


                }
            } else if (msg.what == 204) {//医院详情失败
                ToastUtils.myToast(InformationDetailsActivity.this, "请求失败");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_details);
        initView();
    }

    public void initView() {
        //根据传过来的id,查询对应的医院详情
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getNewInformationAdDetails(handler,getIntent().getLongExtra("id", -1));


        mHospitalMess = (TextView) findViewById(R.id.hospitals_mess);
        mBack = (ImageView) findViewById(R.id.details_back);
        mBack.setOnClickListener(this);

        mHospital_img = (ImageView) findViewById(R.id.details_img);
        mHospital_name = (TextView) findViewById(R.id.hospital_name);
        mHospital_grade = (TextView) findViewById(R.id.hospital_grade);
        mHospital_message = (TextView) findViewById(R.id.hospitals_mess);
        tv_jj = (TextView) findViewById(R.id.hospitals_jj);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        }
    }
}
