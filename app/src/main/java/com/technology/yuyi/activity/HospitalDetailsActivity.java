package com.technology.yuyi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.Information;
import com.technology.yuyi.bean.bean_DocId;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.RongUSerProvider;
import com.technology.yuyi.lzh_utils.RongUri;
import com.technology.yuyi.lzh_utils.RongUser;
import com.technology.yuyi.lzh_utils.RongUserList;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

public class HospitalDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private RelativeLayout mBgRelative;
    private ImageView mBack;
    private Button mBtn;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private View mAlertView;
    private Button mSpeechBtn;
    private Button mVideoBtn;
    private Button mCharBtn;
    private String DocId;//医生的userID
    private TextView mHospital_name;
    private TextView mGrade_tv;
    private TextView mHospital_message;
    private ImageView mImg;
    private HttpTools mHttptools;
    private String resStr;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 25) {
                Object o = msg.obj;
                if (o != null && o instanceof Information) {
                    Information information = (Information) o;
                    mHospital_name.setText(information.getHospitalName());
                    mGrade_tv.setText(information.getGradeName());
                    mHospital_message.setText(information.getIntroduction());
                    Picasso.with(HospitalDetailsActivity.this).load(UrlTools.BASE + information.getPicture()).error(R.mipmap.error_big).into(mImg);
                }
            }else if (msg.what==207){
                ToastUtils.myToast(HospitalDetailsActivity.this,"获取数据失败");
            }
        }
    };
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(HospitalDetailsActivity.this);
                    break;
                case 1:
                    try{
                        bean_DocId docId= gson.gson.fromJson(resStr,bean_DocId.class);
                        if (docId!=null){
                            DocId=docId.getId()+"";
                            user.targetId=DocId;
//                            RongUserList.addUser(new RongUser(DocId,docId.getTrueName(),Ip.imagePth_F+docId.getAvatar()));
                        }

                        else {
                            Toast.makeText(HospitalDetailsActivity.this,"请求医生信息错误，无法启动聊天程序,请稍后重试",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(HospitalDetailsActivity.this);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);
        initView();
        getDocId();
    }

    public void initView() {
        //获取网络数据
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getAskDataMessage(mHandler, getIntent().getIntExtra("id", -1));

        mHospital_name = (TextView) findViewById(R.id.tv_hospital);//医院名称
        mGrade_tv = (TextView) findViewById(R.id.grade_tv);//等级
        mHospital_message = (TextView) findViewById(R.id.hospital_message);//详情
        mImg = (ImageView) findViewById(R.id.img);

        mBack = (ImageView) findViewById(R.id.detail_back);
        mBack.setOnClickListener(this);
        //设置透明度
        mBgRelative = (RelativeLayout) findViewById(R.id.bg_relative);
        mBgRelative.getBackground().setAlpha(125);
        //医患咨询
        mBtn = (Button) findViewById(R.id.ask_btn_sure);
        mBtn.setOnClickListener(this);
        //弹框
        mBuilder = new AlertDialog.Builder(this);
        mAlertDialog = mBuilder.create();
        mAlertView = LayoutInflater.from(this).inflate(R.layout.ask_alert_box, null);
        mAlertDialog.setView(mAlertView);

        //语音,视频，文字
        mSpeechBtn = (Button) mAlertView.findViewById(R.id.btn_speech);
        mVideoBtn = (Button) mAlertView.findViewById(R.id.btn_video);
        mCharBtn = (Button) mAlertView.findViewById(R.id.btn_char);
        mSpeechBtn.setOnClickListener(this);
        mVideoBtn.setOnClickListener(this);
        mCharBtn.setOnClickListener(this);
}

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == mBtn.getId()) {
            mAlertDialog.show();
            setAlertWidth(0.7f, mAlertDialog);
        } else if (id == mSpeechBtn.getId()) {//语音咨询
            mAlertDialog.dismiss();
            if (DocId!=null&&!"".equals(DocId)){
                RongCallKit.startSingleCall(HospitalDetailsActivity.this,user.targetId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO);
            }
            else {
                Toast.makeText(HospitalDetailsActivity.this,"无法获取医生信息，启动咨询程序失败，请稍后重试",Toast.LENGTH_SHORT).show();
                getDocId();
            }

        } else if (id == mVideoBtn.getId()) {//视频咨询
            mAlertDialog.dismiss();
            if (DocId!=null&&!"".equals(DocId)){
                RongCallKit.startSingleCall(HospitalDetailsActivity.this,user.targetId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);

            }
            else {
                Toast.makeText(HospitalDetailsActivity.this,"无法获取医生信息，启动咨询程序失败，请稍后重试",Toast.LENGTH_SHORT).show();
                getDocId();
            }

        } else if (id == mCharBtn.getId()) {//文字资讯
                mAlertDialog.dismiss();
            if (DocId!=null&&!"".equals(DocId)){
                RongIM.getInstance().startPrivateChat(HospitalDetailsActivity.this,user.targetId,"咨询");

            }
            else {
                Toast.makeText(HospitalDetailsActivity.this,"无法获取医生信息，启动咨询程序失败，请稍后重试",Toast.LENGTH_SHORT).show();
                getDocId();
            }
        } else if (id == mBack.getId()) {//返回
            finish();
        }
    }

    //设置alert宽度
    public void setAlertWidth(float a, AlertDialog alertDialog) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = alertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (dm.widthPixels * (a));
        alertDialog.getWindow().setAttributes(p);//设置生效
    }


    //获取医生id
    public void getDocId() {
        Map<String,String> m=new HashMap<>();
        okhttp.getCall(Ip.url_F+Ip.interface_getDocID,m,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                        resStr=response.body().string();
                        Log.i("请求医生融云信息---",resStr);
                        handler.sendEmptyMessage(1);
            }
        });
    }
}
