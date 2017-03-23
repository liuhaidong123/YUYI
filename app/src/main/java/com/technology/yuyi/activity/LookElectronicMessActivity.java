package com.technology.yuyi.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_MedicalRecordMsg;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LookElectronicMessActivity extends AppCompatActivity {
private ImageView mBack;
    private TextView mText;
    private int id;
    private TextView tv_username,tv_userSex,tv_t_userAge,user_textv_jiguan,tv_userHunYin,tv_userTime,tv_now_disease;//姓名，性别,年龄,籍贯,采集时间,描述
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(LookElectronicMessActivity.this);
                    break;
                case 1:
                    try{
//                        "gender": 0, ：0女1男 。origin：籍贯  marital：婚姻状况0未婚，1已婚，2离异，3丧偶
                        bean_MedicalRecordMsg recordMsg= gson.gson.fromJson(resStr,bean_MedicalRecordMsg.class);
                        if (recordMsg!=null){
//                            tv_username,tv_userSex,tv_userAge,tv_userZhiYe,tv_userHunYin,tv_userTime,tv_now_disease
                            tv_username.setText(recordMsg.getTrueName());
                            if ("0".equals(recordMsg.getGender())){
                                tv_userSex.setText("女");
                            }
                            else if ("1".equals(recordMsg.getGender())){
                                tv_userSex.setText("男");
                            }
                            Log.i("--recordMsg.getAge()---",""+recordMsg.getAge());
                            tv_t_userAge.setText(recordMsg.getAge()+"");
                            user_textv_jiguan.setText(recordMsg.getOrigin());
                            int tp=recordMsg.getMarital();
                            String mar="未填写";
                            switch (tp){
                                case 0:
                                    mar="未婚";
                                    break;
                                case 1:
                                    mar="已婚";
                                    break;
                                case 2:
                                    mar="离异";
                                    break;
                                case 3:
                                    mar="丧偶";
                                    break;
                            }
                            tv_userHunYin.setText(mar);
                            tv_userTime.setText(recordMsg.getCreateTimeString());
                            tv_now_disease.setText(recordMsg.getMedicalrecord());
                        }
                        else {
                            toast.toast_gsonEmpty(LookElectronicMessActivity.this);
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(LookElectronicMessActivity.this);
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };
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
        initView();

        id=getIntent().getIntExtra("id",-1);
        if (id!=-1){
            getData();
        }

    }

    private void initView() {
        tv_username= (TextView) findViewById(R.id.tv_username);
        tv_userSex= (TextView) findViewById(R.id.tv_userSex);
        tv_t_userAge= (TextView) findViewById(R.id.tv_tx_userAge);
        user_textv_jiguan= (TextView) findViewById(R.id.user_textv_jiguan);
        tv_userHunYin= (TextView) findViewById(R.id.tv_userHunYin);
//        tv_username,tv_userSex,tv_userAge,tv_userZhiYe,tv_userHunYin,tv_userTime,tv_now_disease
        tv_userTime= (TextView) findViewById(R.id.tv_userTime);
        tv_now_disease= (TextView) findViewById(R.id.tv_now_disease);
    }


    //http://192.168.1.44:8080/yuyi/medical/get.do?id=5
    public void getData() {
        Map<String,String> mp=new HashMap<>();
        mp.put("id",id+"");
        okhttp.getCall(Ip.url_F+Ip.interface_medicalRecordMsg,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                Log.i("电子病历详情",resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }
}
