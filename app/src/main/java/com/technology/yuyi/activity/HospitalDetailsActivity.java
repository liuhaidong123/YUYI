package com.technology.yuyi.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.PopupSettings.Pop;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.DoctorRoot;
import com.technology.yuyi.bean.Information;
import com.technology.yuyi.bean.PhysicianList;
import com.technology.yuyi.bean.bean_DocId;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyApp;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.RongConnect;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import io.rong.callkit.RongCallKit;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class HospitalDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private AlertDialog.Builder builder,fuwuBuilder;
    private AlertDialog alertDialog,fuwuAlertDialog;
    private View alertView,fuwuAlertView;
    private int flagPosition = -1;//表示点击某个医生的下标
    private boolean flag = false;//true表示已经获取到医院医生咨询列表
    private ListView doctorListView;
    private List<PhysicianList> docList = new ArrayList<>();
    private DocAdapter docAdapter;


    private RelativeLayout mBgRelative;
    private ImageView mBack;
    private String DocId;//医生的userID
    private TextView mHospital_name;//医院名字
    private TextView mGrade_tv;//医院信息
    private TextView mHospital_message;//医院介绍
    private ImageView mImg;//医院图片
    private HttpTools mHttptools;
    private String resStr;

    LinearLayout ask_video, ask_voice, ask_word;//视频，语音，文字的三种咨询方式
    //    ImageView ask_close;//关闭咨询方式
    PopupWindow pop;
    TextView bottomBtn;//咨询按钮
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 25) {
                Object o = msg.obj;
                if (o != null && o instanceof Information) {
                    Information information = (Information) o;
                    mTv_hospital.setText("医院介绍");
                    mHospital_name.setText(information.getHospitalName());
                    mGrade_tv.setText(information.getGradeName());
                    mHospital_message.setText(information.getIntroduction());
                    Picasso.with(HospitalDetailsActivity.this).load(UrlTools.BASE + information.getPicture()).error(R.mipmap.errorpicture).into(mImg);
                }
            } else if (msg.what == 207) {
            }
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MyDialog.stopDia();
                    toast.toast_faild(HospitalDetailsActivity.this);
                    break;
                case 1:
                    MyDialog.stopDia();
                    try {
//                        DocId="17743516301ph";
//                        user.targetId = DocId;
                        Object o = gson.gson.fromJson(resStr, DoctorRoot.class);
                        if (o != null && o instanceof DoctorRoot) {
                            DoctorRoot doctorRoot = (DoctorRoot) o;
                            if (doctorRoot != null && "0".equals(doctorRoot.getCode() + "")) {
                                if (doctorRoot.getPhysicianList() != null) {
                                    docList = doctorRoot.getPhysicianList();
                                    docAdapter.notifyDataSetChanged();
                                    flag = true;
                                }
                            }

                        } else {
                            Toast.makeText(HospitalDetailsActivity.this, "请求医生信息错误，无法启动聊天程序,请稍后重试", Toast.LENGTH_SHORT).show();
                        }

//                        bean_DocId docId = gson.gson.fromJson(resStr, bean_DocId.class);
//                        if (docId != null) {
//                            if (docId.getCode() == 0) {
//                                DocId = docId.getId() + "";
//                                user.targetId = DocId;
//                                MyApp.setUserInfo(DocId, new UserInfo(DocId, "医生", Uri.parse("http://a3.qpic.cn/psb?/V10dl1Mt1s0RoL/qvT5ZwDSegULprXup78nlo3*XNUqCRH8shghIkAnQTs!/b/dLMAAAAAAAAA&bo=ewJ7AgAAAAADByI!&rf=viewer_4")));
//                            } else if (docId.getCode() == -1) {
//                                DocId = "-1";//医院没有设置咨询功能
//                            }
//                        } else {
//                            Toast.makeText(HospitalDetailsActivity.this, "请求医生信息错误，无法启动聊天程序,请稍后重试", Toast.LENGTH_SHORT).show();
//                        }
                    } catch (Exception e) {
                        toast.toast_gsonFaild(HospitalDetailsActivity.this);
                    }
                    break;
            }
        }
    };

    private TextView mTv_hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_details);
        initView();
        getDocId();
    }

    public void initView() {

        //服务弹框
        fuwuBuilder=new AlertDialog.Builder(this);
        fuwuBuilder.setCancelable(false);
        fuwuAlertDialog=fuwuBuilder.create();
        fuwuAlertView=LayoutInflater.from(this).inflate(R.layout.fuwu_alert,null);
        fuwuAlertDialog.setView(fuwuAlertView);
        TextView disagree=fuwuAlertView.findViewById(R.id.fuwu_disagree);//取消
        TextView agree=fuwuAlertView.findViewById(R.id.fuwu_agree);//同意
        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuwuAlertDialog.dismiss();
            }
        });
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fuwuAlertDialog.dismiss();
                user.saveFuWuFlag(HospitalDetailsActivity.this,true);
                alertDialog.show();
            }
        });
        builder = new AlertDialog.Builder(this);
        alertDialog = builder.create();
        alertView = LayoutInflater.from(this).inflate(R.layout.select_doctor, null);
        alertDialog.setView(alertView);
        doctorListView = alertView.findViewById(R.id.doc_listview);
        docAdapter = new DocAdapter();
        doctorListView.setAdapter(docAdapter);
        doctorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DocId = docList.get(i).getId() + "";
                user.targetId = DocId;
                MyApp.setUserInfo(DocId, new UserInfo(DocId, "医生", Uri.parse("http://a3.qpic.cn/psb?/V10dl1Mt1s0RoL/qvT5ZwDSegULprXup78nlo3*XNUqCRH8shghIkAnQTs!/b/dLMAAAAAAAAA&bo=ewJ7AgAAAAADByI!&rf=viewer_4")));
                showWindowAsk();
                alertDialog.dismiss();
            }
        });

        //获取网络数据
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getAskDataMessage(mHandler, getIntent().getIntExtra("id", -1));//医院详情

        mHospital_name = (TextView) findViewById(R.id.tv_hospital);//医院名称
        mGrade_tv = (TextView) findViewById(R.id.grade_tv);//等级
        mHospital_message = (TextView) findViewById(R.id.hospital_message);//详情
        mImg = (ImageView) findViewById(R.id.img);

        mBack = (ImageView) findViewById(R.id.detail_back);
        mBack.setOnClickListener(this);
        //设置透明度
        mTv_hospital = (TextView) findViewById(R.id.tv_hospital_mess);//详情
        mBgRelative = (RelativeLayout) findViewById(R.id.bg_relative);
        //医患咨询
        bottomBtn = (TextView) findViewById(R.id.bottomBtn);
        bottomBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == bottomBtn.getId()) {
            if (user.getFuWuFlag(this)){ //判断用户有没有同意咨询服务协议
                alertDialog.show();
            }else {
                fuwuAlertDialog.show();
            }

        } else if (id == R.id.ask_voice) {//语音咨询
            pop.dismiss();
            if ("-1".equals(DocId)) {
                Toast.makeText(HospitalDetailsActivity.this, "当前医院尚未开通咨询服务,请选择其他医院咨询", Toast.LENGTH_SHORT).show();
                return;
            }
            if (DocId != null && !"".equals(DocId) && !"".equals(user.RonguserId) && !TextUtils.isEmpty(user.RonguserId)) {
                RongCallKit.startSingleCall(HospitalDetailsActivity.this, user.targetId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_AUDIO);
            } else {
                getRongInfo();
            }
        } else if (id == R.id.ask_video) {//视频咨询
            pop.dismiss();
            if ("-1".equals(DocId)) {
                Toast.makeText(HospitalDetailsActivity.this, "当前医院尚未开通咨询服务,请选择其他医院咨询", Toast.LENGTH_SHORT).show();
                return;
            }
            if (DocId != null && !"".equals(DocId) && !"".equals(user.RonguserId) && !TextUtils.isEmpty(user.RonguserId)) {
                RongCallKit.startSingleCall(HospitalDetailsActivity.this, user.targetId, RongCallKit.CallMediaType.CALL_MEDIA_TYPE_VIDEO);

            } else {
                getRongInfo();
            }

        } else if (id == R.id.ask_word) {//文字资讯
            pop.dismiss();
            if ("-1".equals(DocId)) {
                Toast.makeText(HospitalDetailsActivity.this, "当前医院尚未开通咨询服务,请选择其他医院咨询", Toast.LENGTH_SHORT).show();
                return;
            }
            if (DocId != null && !"".equals(DocId) && !"".equals(user.RonguserId) && !TextUtils.isEmpty(user.RonguserId)) {
                RongIM.getInstance().startPrivateChat(HospitalDetailsActivity.this, user.targetId, "咨询");
            } else {
                getRongInfo();
            }
        } else if (id == mBack.getId()) {//返回
            finish();
        }
    }


    //获取医生id
    public void getDocId() {
        MyDialog.showDialog(HospitalDetailsActivity.this);
        //http://192.168.1.37:8080/yuyi/physician/doctory.do?cid=1
        int ids = getIntent().getIntExtra("id", -1);
        if (ids == -1) {
            Log.e("hospitlDetailsActivity", "error：无法获取到医院的id--没有办发发起请求聊天的医生id");
            return;
        }
        Map<String, String> m = new HashMap<>();
        m.put("cid", ids + "");
        okhttp.getCall(Ip.url + Ip.interface_DocInfo, m, okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("请求医生融云信息---", resStr);
                handler.sendEmptyMessage(1);
            }
        });
    }

    public void getRongInfo() {
        if ("".equals(DocId) | DocId == null) {
            Log.e("融云聊天启动--", "DocId为空----");
            getDocId();
        }
        if (user.RonguserId == null | "".equals(user.RonguserId)) {
            Log.e("融云聊天启动--", "RonguserId为空----");
            RongConnect.getRongToken(this);
        }
    }

    //选择咨询方式
    public void showWindowAsk() {

        if (pop == null) {
            pop = new PopupWindow();
        }
        View vi = LayoutInflater.from(this).inflate(R.layout.pop_ask, null);
        ask_video = (LinearLayout) vi.findViewById(R.id.ask_video);
        ask_video.setOnClickListener(this);
        ask_voice = (LinearLayout) vi.findViewById(R.id.ask_voice);
        ask_voice.setOnClickListener(this);
        ask_word = (LinearLayout) vi.findViewById(R.id.ask_word);
        ask_word.setOnClickListener(this);
        View parent = findViewById(R.id.activity_hospital_details);
        Pop.getInstance().getCenterSettings(this, pop, parent, vi, 0.25f, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    //医生列表适配器
    class DocAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return docList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DocHolder docHolder = null;
            if (view == null) {
                view = LayoutInflater.from(HospitalDetailsActivity.this).inflate(R.layout.select_doctor_item, null);
                docHolder = new DocHolder();
                docHolder.doctor = view.findViewById(R.id.doctor1_btn);
                view.setTag(docHolder);
            } else {
                docHolder = (DocHolder) view.getTag();
            }

            docHolder.doctor.setText(docList.get(i).getTrueName());
            return view;
        }

        class DocHolder {
            TextView doctor;
        }
    }
}
