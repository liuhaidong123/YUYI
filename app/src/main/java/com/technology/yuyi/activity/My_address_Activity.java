package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_UserAddress;
import com.technology.yuyi.bean.bean_UserEditAddress;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyActivity;
import com.technology.yuyi.lzh_utils.ResCode;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.lzh_view.ArrayWheelAdapter;
import com.technology.yuyi.lzh_view.MyWheelAdapter;
import com.technology.yuyi.lzh_view.OnWheelChangedListener;
import com.technology.yuyi.lzh_view.WheelView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
//收货地址列表

/**
 * Created by wanyu on 2017/3/2.
 */

public class My_address_Activity extends Activity implements OnWheelChangedListener{
    private PopupWindow popupW;
    private WheelView my_address_pop_wheelV_Provice,my_address_pop_wheelV_City,my_address_pop_wheelV_Areas;
    private List<String> mProvince;//省
    private Map<String,List<String>>mCity;
    private Map<String,List<String>>mArea;
    private List<String>listCity;
    private List<String>listArea;
    private String provice;
    private String city;
    private int currentPro,currentCity;
    private MyWheelAdapter adapterPro,adapterCity,adapterAres;
    private EditText my_address_name,my_address_phoneNum,my_address_addressinfo,my_address_postCode;//xingming,dianhua,填写的地址,邮编
    private TextView my_address_addressSelect;//选择的地址
    private TextView activity_address_title;

    private String selectAddress;//被选中等address（省市县）
    private String pro,cit,area;
    private String resultStr;
    private String id;

    String name;
    String addressSelect;
    String addressInfo;
    String postCode;
    String phonenum;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(My_address_Activity.this);
                    break;
                case 1://请求用户信息的接口返回
                    if (!"".equals(resultStr)&&!TextUtils.isEmpty(resultStr)){//用户已经添加了收货地址
                        try{
                            bean_UserAddress ad= gson.gson.fromJson(resultStr,bean_UserAddress.class);
                            id=ad.getId()+"";
                            my_address_name.setText(ad.getTrueName());
                            my_address_addressSelect.setText(ad.getAreaName());
                            my_address_phoneNum.setText(ad.getTelephone());
                            my_address_addressinfo.setText(ad.getAddress());
                            my_address_postCode.setText(ad.getPostCode());
                        }
                        catch (Exception e){
                            toast.toast_gsonFaild(My_address_Activity.this);
                        }
                        activity_address_title.setText("地址编辑");
                    }

                    else {//用户还没有添加地址,不做处理
                        activity_address_title.setText("收货地址");
                    }
                    break;
                case 2://添加／修改地址成功
                    try{
                       bean_UserEditAddress edi=gson.gson.fromJson(resultStr,bean_UserEditAddress.class);
                       String code=edi.getCode();
                        if ("-1".equals(code)){
                            Toast.makeText(My_address_Activity.this,"用户登陆状态失效：当前用户在其他设备登陆",Toast.LENGTH_SHORT).show();
                            user.clearLogin(My_address_Activity.this);
                        }
                        else if ("0".equals(code)){
                            Toast.makeText(My_address_Activity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent();
                            Bundle b=new Bundle();
                            b.putString("name",name);
                            b.putString("address",addressSelect+addressInfo);
                            b.putString("phonenum",phonenum);
                            intent.putExtra("bundle",b);
                            setResult(ResCode.Response_drugbuy,intent);
                            finish();

                        }
                        else {
                            Toast.makeText(My_address_Activity.this,"修改失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(My_address_Activity.this);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        initView();
        getUserAddress();
    }

    private void initView() {
        activity_address_title= (TextView) findViewById(R.id.activity_address_title);
        my_address_name= (EditText) findViewById(R.id.my_address_name);
        my_address_phoneNum= (EditText) findViewById(R.id.my_address_phoneNum);
        my_address_addressinfo= (EditText) findViewById(R.id.my_address_addressinfo);
        my_address_postCode= (EditText) findViewById(R.id.my_address_postCode);
        my_address_name= (EditText) findViewById(R.id.my_address_name);
        my_address_name= (EditText) findViewById(R.id.my_address_name);
        my_address_name= (EditText) findViewById(R.id.my_address_name);
        my_address_addressSelect= (TextView) findViewById(R.id.my_address_addressSelect);
}


    public void reBack(View view) {
        if (view.getId()==R.id.activity_address_imageReturn){
            finish();
        }
    }
    //选择地址的view
    public void selectAddress(View view) {
        if (view!=null){
            if (pullXml()){
                showWindowSelect();//弹窗显示选择器
            }

        }
    }


    //地址选择器
    private void showWindowSelect() {

        View vi=getLayoutInflater().inflate(R.layout.my_address_popup, null);
        my_address_pop_wheelV_Provice= (WheelView) vi.findViewById(R.id.my_address_pop_wheelV_Provice);
        my_address_pop_wheelV_City= (WheelView) vi.findViewById(R.id.my_address_pop_wheelV_City);
        my_address_pop_wheelV_Areas= (WheelView) vi.findViewById(R.id.my_address_pop_wheelV_Areas);


        adapterPro=new MyWheelAdapter(My_address_Activity.this,mProvince);
        my_address_pop_wheelV_Provice.setViewAdapter(adapterPro);
        my_address_pop_wheelV_Provice.setVisibleItems(5);

        adapterCity=new MyWheelAdapter(My_address_Activity.this,mCity.get(mProvince.get(0)));
        my_address_pop_wheelV_City.setViewAdapter(adapterCity);
        my_address_pop_wheelV_City.setVisibleItems(5);

        List<String>li=mCity.get(mProvince.get(0));
        for (int i=0;i<li.size();i++){
        Log.i("-------------",li.get(i));
        }
        adapterAres=new MyWheelAdapter(My_address_Activity.this,mArea.get(li.get(0)));
        my_address_pop_wheelV_Areas.setViewAdapter(adapterAres);
        my_address_pop_wheelV_Areas.setVisibleItems(5);

        TextView  my_address_pop_cancle= (TextView) vi.findViewById(R.id.my_address_pop_cancle);
        TextView    my_address_pop_submit= (TextView) vi.findViewById(R.id.my_address_pop_submit);

        my_address_pop_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupW.dismiss();
            }
        });

        my_address_pop_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAddress=pro+" "+cit+" "+area;
                my_address_addressSelect.setText(selectAddress);
                popupW.dismiss();
            }
        });


        my_address_pop_wheelV_Provice.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentPro=newValue;
                my_address_pop_wheelV_City.setViewAdapter(new MyWheelAdapter(My_address_Activity.this,mCity.get(mProvince.get(currentPro))));
                my_address_pop_wheelV_City.setCurrentItem(0);
                List<String>lit=mCity.get(mProvince.get(currentPro));
                my_address_pop_wheelV_Areas.setViewAdapter(new MyWheelAdapter(My_address_Activity.this,mArea.get(lit.get(0))));
                my_address_pop_wheelV_Areas.setCurrentItem(0);

                pro=mProvince.get(newValue);
                cit=mCity.get(mProvince.get(newValue)).get(0);

                List<String>li=mCity.get(mProvince.get(currentPro));
                area=mArea.get(li.get(0)).get(0);

            }
        });
        my_address_pop_wheelV_City.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                currentCity=newValue;
                List<String>li=mCity.get(mProvince.get(currentPro));
                my_address_pop_wheelV_Areas.setViewAdapter(new MyWheelAdapter(My_address_Activity.this,mArea.get(li.get(newValue))));
                my_address_pop_wheelV_Areas.setCurrentItem(0);

                cit=li.get(newValue);
                area=mArea.get(li.get(newValue)).get(0);
            }
        });

        my_address_pop_wheelV_Areas.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                List<String>li=mCity.get(mProvince.get(currentPro));
                area=mArea.get(li.get(currentCity)).get(newValue);
            }
        });

        popupW=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        popupW.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupW.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupW.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupW.setContentView(vi);
        popupW.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popupW.setTouchable(true);
        popupW.setFocusable(true);
        popupW.setOutsideTouchable(true);
        LinearLayout parent= (LinearLayout) findViewById(R.id.my_address_parentView);
        popupW.setAnimationStyle(R.style.popup_anim);
        popupW.showAtLocation(parent, Gravity.BOTTOM, 0,0);
        popupW.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });



    }

    //解析数据
    public boolean pullXml(){
        try{
            InputStream is=getResources().getAssets().open("city.xml");

            //    创建XmlPullParserFactory解析工厂
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            //    通过XmlPullParserFactory工厂类实例化一个XmlPullParser解析类
            XmlPullParser parser = factory.newPullParser();
            //    根据指定的编码来解析xml文档
            parser.setInput(is, "utf-8");
            //    得到当前的事件类型
            int eventType = parser.getEventType();
            //    只要没有解析到xml的文档结束，就一直解析
            while(eventType != XmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    //    解析到文档开始的时候
                    case XmlPullParser.START_DOCUMENT:
                        mProvince = new ArrayList<>();//省份集合
                        mCity=new HashMap<>();//市区集合
                        mArea=new HashMap<>();//县区集合
                        break;
                    //    解析到xml标签的时候
                    case XmlPullParser.START_TAG:
                        switch (parser.getName()){
                            case "province":
                                mProvince.add(parser.getAttributeValue(0));
                                listCity=new ArrayList<>();
                                provice=parser.getAttributeValue(0);
                                break;
                            case "city":
                                listCity.add(parser.getAttributeValue(0));
                                city=parser.getAttributeValue(0);
                                listArea=new ArrayList<>();
                                break;
                            case "area":
                                listArea.add(parser.getAttributeValue(0));
                                break;
                        }
                        break;
                    //    解析到xml标签结束的时候
                    case XmlPullParser.END_TAG:
                        switch (parser.getName()){
                            case "province":
                                mCity.put(provice,listCity);
                                break;
                            case "city":
                                mArea.put(city,listArea);
                                break;
                        }
                        break;
                }
                //    通过next()方法触发下一个事件
                eventType = parser.next();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
       if (wheel==my_address_pop_wheelV_Provice){

       }
        else if (wheel==my_address_pop_wheelV_City){

       }
        else if (wheel==my_address_pop_wheelV_Areas){

       }
    }

    public void submit(View view) {
        if (view.getId()==R.id.my_address_submit) {//提交地址信息
            name = my_address_name.getText().toString();
           addressSelect = my_address_addressSelect.getText().toString();
            addressInfo = my_address_addressinfo.getText().toString();
            postCode = my_address_postCode.getText().toString();
           phonenum=my_address_phoneNum.getText().toString();
            if (IsSuccess(name,addressInfo,addressSelect,postCode,phonenum)){

                submitAddress();//提交信息

            }
            else {
                Toast.makeText(My_address_Activity.this,"信息不完整",Toast.LENGTH_SHORT).show();
            }
        }
    }
    //提交信息修改／新增
    private void submitAddress() {
        Map<String,String>mp=new HashMap<>();
        if (!"".equals(id)&&!TextUtils.isEmpty(id)){
            mp.put("id",id);
        }
        mp.put("token",user.userPsd);
        mp.put("trueName",my_address_name.getText().toString()); //说货人
        mp.put("address",my_address_addressinfo.getText().toString());//说货地址
        mp.put("telephone",my_address_phoneNum.getText().toString());//电话
        mp.put("postCode",my_address_postCode.getText().toString());//邮编
        mp.put("areaCode",""); //地区编码
        mp.put("oid","0"); //序号--序号为0的作为默认地址
        mp.put("areaName",my_address_addressSelect.getText().toString());//地区名称，选择的地址
//        private Long id;
//        //收货人
//        private String trueName;
//        //收货地址
//        private String address;
//        //联系电话
//        private String telephone;
//        //邮编
//        private String postCode;
//        //用户ID--个人基本信息表personal--外键
//        private Long personalId;
//        //地区编码
//        private Long areaCode;
//        //序号--序号为0的作为默认地址
//        private Integer oid;
//        //地区名称，选择地址
//        private String areaName;
        okhttp.getCall(Ip.url+Ip.interface_User_editAddress,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resultStr=response.body().string();
                handler.sendEmptyMessage(2);
                Log.i("----修改／上传收说地址---",resultStr.toString());
            }
        });
    }

    private boolean IsSuccess(String st1,String st2,String st3,String st4,String str5){
        if (!"".equals(st1)&&!TextUtils.isEmpty(st1)){
            if (!"".equals(st2)&&!TextUtils.isEmpty(st2)){
                if (!"".equals(st3)&&!TextUtils.isEmpty(st3)){
                    if (!"".equals(st4)&&!TextUtils.isEmpty(st4)){
                           if (!"".equals(str5)&&!TextUtils.isEmpty(str5)){
                               return true;
                           }
                    }
                }
            }

        }
          return false;
    }






    //获取用户的信息
    public void getUserAddress(){
        if (user.userPsd!=null&&!"0".equals(user.userPsd)){
            Map<String,String>mp=new HashMap<>();
            mp.put("token",user.userPsd);
            okhttp.getCall(Ip.url+Ip.interface_User_Address,mp,okhttp.OK_GET).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    resultStr=response.body().string();
                    handler.sendEmptyMessage(1);
                    Log.i("去获取用户默认地址---","---"+resultStr.toString());
                }
            });
        }
    }
}
