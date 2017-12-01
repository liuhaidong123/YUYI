package com.technology.yuyi.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.LookElecAdapter;
import com.technology.yuyi.bean.bean_FamilyUserEle;
import com.technology.yuyi.bean.bean_FamilyUserEleMsg;
import com.technology.yuyi.bean.bean_MedicalRecordList;
import com.technology.yuyi.bean.bean_MedicalRecordMsg;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyGridView;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//intent.putExtra("type", "0");0自己的病例，1家人的病例
public class LookElectronicMessActivity extends AppCompatActivity {
    bean_MedicalRecordList.ResultBean beanUser;//用户自己的电子病例
    bean_FamilyUserEle.ResultBean beanFamilyUser;//家人的电子病历
    private ImageView mBack;
    TextView eleMsg_text_creatTime,eleMsg_text_hospitalName,eleMsg_text_KS,eleMsg_text_DocName;//时间，医院，科室，医生
    MyGridView ele_gridView;//传入的图片
    LookElecAdapter adapter;
    TextView ele_message;//病例内容
    private int id;
    private String type;//0我的病历，1家人病历
    List<String> list;//存放图片的url（不加ip）
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_electronic_mess);
        //返回
        mBack = (ImageView) findViewById(R.id.elec_mess_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //设置textview文字过多时，可以垂直滚动
        ele_message = (TextView) findViewById(R.id.ele_message);
        ele_message.setMovementMethod(ScrollingMovementMethod.getInstance());
        initView();
        initIntentData();
    }

    private void initIntentData() {
        type = getIntent().getStringExtra("type");
        list=new ArrayList<>();
        switch (type){
            case "0"://自己的电子病例
                beanUser= (bean_MedicalRecordList.ResultBean) getIntent().getSerializableExtra("id");
                if (beanUser!=null){
                    eleMsg_text_creatTime.setText(beanUser.getCreateTimeString());
                    eleMsg_text_hospitalName.setText(beanUser.getHospitalName());
                    eleMsg_text_KS.setText(beanUser.getDepartmentName());
                    eleMsg_text_DocName.setText(beanUser.getPhysicianName());
                    ele_message.setText(beanUser.getMedicalrecord());
                    String url=beanUser.getPicture();
                    if (url!=null&&!"".equals(url)){
                        String[]str=url.split(";");
                        adapter=new LookElecAdapter(this,str);
                        ele_gridView.setAdapter(adapter);
                    }
                }
                break;
            case "1"://家人的电子病例
                beanFamilyUser= (bean_FamilyUserEle.ResultBean) getIntent().getSerializableExtra("id");
                if (beanFamilyUser!=null){
                    eleMsg_text_creatTime.setText(beanFamilyUser.getCreateTimeString());
                    eleMsg_text_hospitalName.setText(beanFamilyUser.getHospitalName());
                    eleMsg_text_KS.setText(beanFamilyUser.getDepartmentName());
                    eleMsg_text_DocName.setText(beanFamilyUser.getPhysicianName());
                    ele_message.setText(beanFamilyUser.getMedicalrecord());
                    String url=beanFamilyUser.getPicture();
                    if (url!=null&&!"".equals(url)){
                        String[]str=url.split(";");
                        adapter=new LookElecAdapter(this,str);
                        ele_gridView.setAdapter(adapter);
                    }
                }
                break;
        }
    }

    private void initView() {
        eleMsg_text_creatTime= (TextView) findViewById(R.id.eleMsg_text_creatTime);
        eleMsg_text_hospitalName= (TextView) findViewById(R.id.eleMsg_text_hospitalName);
        eleMsg_text_KS= (TextView) findViewById(R.id.eleMsg_text_KS);
        eleMsg_text_DocName= (TextView) findViewById(R.id.eleMsg_text_DocName);
        ele_gridView= (MyGridView) findViewById(R.id.ele_gridView);
    }


    //http://192.168.1.44:8080/yuyi/medical/get.do?id=5
//    public void getData() {
//        Map<String, String> mp = new HashMap<>();
//        mp.put("id", id + "");
//        okhttp.getCall(Ip.url_F + Ip.interface_medicalRecordMsg, mp, okhttp.OK_GET).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                handler.sendEmptyMessage(0);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                resStr = response.body().string();
//                Log.i("电子病历详情", resStr);
//                handler.sendEmptyMessage(1);
//            }
//        });
//    }


    //获取家人的病历详细信息http://localhost:8080/yuyi/medical/homeuserMedicalDetails.do?mid=10
//    public void getFamilyData(String pos) {
//        Log.i("id---", pos);
//        Map<String, String> mp = new HashMap<>();
//        mp.put("mid", pos);
//        okhttp.getCall(Ip.url_F + Ip.interface_famiUserEleMsg, mp, okhttp.OK_GET).enqueue(new Callback() {
//            @Override
//            public void onFailure(Request request, IOException e) {
//                handler.sendEmptyMessage(0);
//            }
//
//            @Override
//            public void onResponse(Response response) throws IOException {
//                resStr = response.body().string();
//                Log.i("获取家人电子病历信息---" + id, resStr);
//                handler.sendEmptyMessage(2);
//            }
//        });
//    }
}
