package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.Ms_druginfo_popAdapter;
import com.technology.yuyi.bean.bean_MS_drguInfo;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyGridView;
import com.technology.yuyi.lzh_utils.MyIntent;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//intent.putExtra(MyIntent.intent_MS_drugInfo,listAlldrgus.get(position).getId());
//药品详细信息页面
public class MS_drugInfo_activity extends Activity {
    private int DrugId;
    private PopupWindow popupWindow;
    private final String TAG=getClass().getSimpleName();
    private ImageView ms_druginfo_pop_numAdd,ms_druginfo_pop_numMinus;//弹窗中加减的view
    private TextView ms_druginfo_pop_num;//pop中显示购买数量的view
    private TextView ms_druginfo_pop_price;//pop中显示单价的view

    private int type=-1;
    private String resultStr="";
    private ImageView imageView;
    private TextView ms_drug_info_name,ms_drug_info_price;
    private TextView ms_drug_info_b_name,ms_drug_info_b_kind,ms_drug_info_b_size,ms_drug_info_b_goodsName,ms_drug_info_b_Tname;//名称，药品分类,包装大小,商品名称，通用名称
    private TextView ms_drug_info_b_code,ms_drug_info_b_provider,ms_drug_info_b_pinpai,ms_drug_info_b_leixing,ms_drug_info_b_jixing;//国药准字,生产企业，品牌,类型,剂型
    private TextView ms_drug_info_b_shiyongren;//适用人群
    private bean_MS_drguInfo info;
    private MyGridView ms_drug_info_pop_gridveiw;
    private Ms_druginfo_popAdapter adapter;
    private List<bean_MS_drguInfo.SpecificationsdListBean>listSpi;//属性的list
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
           switch (msg.what){
               case 0:
                   toast.toast_faild(MS_drugInfo_activity.this);
                   break;
               case 1:
                    try{
//                        "createTimeString": "",
//                                "productSpecification": "产品规格",
//                                "drugsName": "999感冒灵1",
//                                "specificationsd": "一盒3包",
//                                "oid": 1,
//                                "packing": "10g x3",
//                                "drugsCurrencyName": "999感冒灵1",
//                                "localId": "",
//                                "drugsFunction": "适用症/功能主治",
//                                "number": 20,
//                                "price": 20,
//                                "drugsType": "颗粒",
//                                "drugsDosage": "用法用量",
//                                "specificationsdList": [
//                        {
//                            "createTimeString": "",
//                                "updateTimeString": "",
//                                "specificationsdNumber": 3,
//                                "updateTime": null,
//                                "oid": 1,
//                                "localId": "",
//                                "unit": "包",
//                                "specificationsdName": "1盒3包",
//                                "createTime": null,
//                                "drugsId": 1,
//                                "price": 45,
//                                "id": 1,
//                                "info": "",
//                                "status": 1
//                        },
//                        {},
//                        {}
//                        ],
//                        "details": "专治感冒",
//                                "id": 1,
//                                "brand": "999",
//                                "businesses": "华润三九医药公司",
//                                "info": "",
//                                "updateTimeString": "",
//                                "dosageForm": "颗粒",
//                                "updateTime": null,
//                                "approvalNumber": "国药准字Z2017021423434",
//                                "picture": "/static/image/999.jpg",
//                                "categoryId1": 1,
//                                "categoryId2": 11,
//                                "createTime": null,
//                                "commodityName": "999感冒灵1",
//                                "status": 1
                        info= gson.gson.fromJson(resultStr,bean_MS_drguInfo.class);
                        Picasso.with(MS_drugInfo_activity.this).load(Ip.imagePth_F+info.getPicture()).into(imageView);
                        ms_drug_info_name.setText(info.getDrugsName());
                        ms_drug_info_price.setText("￥"+info.getPrice());
                        ms_drug_info_b_name.setText("产品名称："+info.getDrugsName());
                        ms_drug_info_b_kind.setVisibility(View.GONE);
                        listSpi=info.getSpecificationsdList();
                        if (!"".equals(info.getPacking())&&!TextUtils.isEmpty(info.getPacking())){//包装大小ms_drug_info_b_size
                            ms_drug_info_b_size.setText("包装大小："+info.getPacking());
                        }
                        else{
                            ms_drug_info_b_size.setVisibility(View.GONE);
                        }

//
                        if (!"".equals(info.getCommodityName())&&!TextUtils.isEmpty(info.getCommodityName())){//药品商品名ms_drug_info_b_goodsName
                            ms_drug_info_b_goodsName.setText("药品商用名："+info.getCommodityName());
                        }
                        else{
                            ms_drug_info_b_goodsName.setVisibility(View.GONE);
                        }


                        if (!"".equals(info.getDrugsCurrencyName())&&!TextUtils.isEmpty(info.getDrugsCurrencyName())){//药品通用名ms_drug_info_b_Tname
                            ms_drug_info_b_Tname.setText("药品通用名："+info.getDrugsCurrencyName());
                        }
                        else{
                            ms_drug_info_b_Tname.setVisibility(View.GONE);
                        }



                        if (!"".equals(info.getApprovalNumber())&&!TextUtils.isEmpty(info.getApprovalNumber())){//批准文号ms_drug_info_b_code
                            ms_drug_info_b_code.setText("批准文号："+info.getApprovalNumber());
                        }
                        else{
                            ms_drug_info_b_code.setVisibility(View.GONE);
                        }



                        if (!"".equals(info.getBusinesses())&&!TextUtils.isEmpty(info.getBusinesses())){//生产企业ms_drug_info_b_provider
                            ms_drug_info_b_provider.setText("生产企业："+info.getBusinesses());
                        }
                        else{
                            ms_drug_info_b_provider.setVisibility(View.GONE);
                        }



                        if (!"".equals(info.getBrand())&&!TextUtils.isEmpty(info.getBrand())){//品牌ms_drug_info_b_pinpai
                            ms_drug_info_b_pinpai.setText("品牌："+info.getBrand());
                        }
                        else{
                            ms_drug_info_b_pinpai.setVisibility(View.GONE);
                        }


                        if (!"".equals(info.getDrugsType())&&!TextUtils.isEmpty(info.getDrugsType())){//药品类型ms_drug_info_b_leixing
                            ms_drug_info_b_leixing.setText("药品类型："+info.getDrugsType());
                        }
                        else{
                            ms_drug_info_b_leixing.setVisibility(View.GONE);
                        }




                        if (!"".equals(info.getDosageForm())&&!TextUtils.isEmpty(info.getDosageForm())){//剂型ms_drug_info_b_jixing
                            ms_drug_info_b_jixing.setText("剂型："+info.getDosageForm());
                        }
                        else{
                            ms_drug_info_b_jixing.setVisibility(View.GONE);
                        }




                        if (!"".equals(info.getDrugsFunction())&&!TextUtils.isEmpty(info.getDrugsFunction())){//适用人群ms_drug_info_b_shiyongren
                            ms_drug_info_b_shiyongren.setText("功能主治："+info.getDrugsFunction());
                        }
                        else{
                            ms_drug_info_b_shiyongren.setVisibility(View.GONE);
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MS_drugInfo_activity.this);
                        Log.e(TAG+"解析异常",e.toString());
                    }
                   break;
           }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_drug_info);
        initView();
    }

    private void initView() {
//        头部
        imageView= (ImageView) findViewById(R.id.imageView);
        ms_drug_info_name= (TextView) findViewById(R.id.ms_drug_info_name);
        ms_drug_info_price= (TextView) findViewById(R.id.ms_drug_info_price);
        //尾部

        //名称，药品分类,包装大小,商品名称，通用名称
        ms_drug_info_b_name= (TextView) findViewById(R.id.ms_drug_info_b_name);
        ms_drug_info_b_kind= (TextView) findViewById(R.id.ms_drug_info_b_kind);
        ms_drug_info_b_size= (TextView) findViewById(R.id.ms_drug_info_b_size);
        ms_drug_info_b_goodsName= (TextView) findViewById(R.id.ms_drug_info_b_goodsName);
        ms_drug_info_b_Tname= (TextView) findViewById(R.id.ms_drug_info_b_Tname);

        //国药准字,生产企业，品牌,类型,剂型
        ms_drug_info_b_code= (TextView) findViewById(R.id.ms_drug_info_b_code);
        ms_drug_info_b_provider= (TextView) findViewById(R.id.ms_drug_info_b_provider);
        ms_drug_info_b_pinpai= (TextView) findViewById(R.id.ms_drug_info_b_pinpai);
        ms_drug_info_b_leixing= (TextView) findViewById(R.id.ms_drug_info_b_leixing);
        ms_drug_info_b_jixing= (TextView) findViewById(R.id.ms_drug_info_b_jixing);

        //适用人群
        ms_drug_info_b_shiyongren= (TextView) findViewById(R.id.ms_drug_info_b_shiyongren);
        ms_drug_info_b_name= (TextView) findViewById(R.id.ms_drug_info_b_name);
        ms_drug_info_b_name= (TextView) findViewById(R.id.ms_drug_info_b_name);
        ms_drug_info_b_name= (TextView) findViewById(R.id.ms_drug_info_b_name);


    }

    //立即购买／加入购物车
    public void shopping(View view) {
        if (view!=null){
            switch (view.getId()){
                case R.id.ms_drug_info_shoppingbuy://立即购买
                    showWindow(1);
                    break;
                case R.id.ms_drug_info_shoppingCart://加入购物车
                    showWindow(0);
                    break;
            }
        }
    }
    //加入购物车，立即购买pos=0：加入购物车 pos=1:立即购买
    private void showWindow(final int pos) {
        type=pos;
        View v=getLayoutInflater().inflate(R.layout.ms_druginfo_popupwindow, null);
            ms_drug_info_pop_gridveiw= (MyGridView) v.findViewById(R.id.ms_drug_info_pop_gridveiw);
        LinearLayout ms_drug_bu= (LinearLayout) v.findViewById(R.id.ms_drug_bu);

        if (listSpi!=null&&listSpi.size()>0){
            ms_drug_bu.setVisibility(View.VISIBLE);
            adapter=new Ms_druginfo_popAdapter(listSpi,MS_drugInfo_activity.this);
            ms_drug_info_pop_gridveiw.setAdapter(adapter);
            ms_drug_info_pop_gridveiw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    adapter.setSelectId(position);
                }
            });
        }
        else {
            ms_drug_bu.setVisibility(View.GONE);
        }
        final RelativeLayout popOutside= (RelativeLayout) v.findViewById(R.id.popOutside);
        TextView ms_druginfo_popupwindow_buy= (TextView) v.findViewById(R.id.ms_druginfo_popupwindow_buy);
        switch (type){
            case 0://加入购物车
                ms_druginfo_popupwindow_buy.setText("加入购物车");
                ms_druginfo_popupwindow_buy.setBackground(getResources().getDrawable(R.drawable.ms_druginfo_shoppingcart));
                break;
            case 1://立即购买
                ms_druginfo_popupwindow_buy.setText("立即购买");
                ms_druginfo_popupwindow_buy.setBackground(getResources().getDrawable(R.drawable.ms_druginfo_shoppingbuy));
                break;
        }

        ms_druginfo_pop_numAdd= (ImageView) v.findViewById(R.id.ms_druginfo_pop_numAdd);
        ms_druginfo_pop_numMinus= (ImageView) v.findViewById(R.id.ms_druginfo_pop_numMinus);
        ms_druginfo_pop_num= (TextView) v.findViewById(R.id.ms_druginfo_pop_num);
        ms_druginfo_pop_price= (TextView) v.findViewById(R.id.ms_druginfo_pop_price);
        //立即购买的按钮
        ms_druginfo_popupwindow_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type==1){//立即购买
                    Intent intent=new Intent();
                    intent.setClass(MS_drugInfo_activity.this,MS_drugBuy_activity.class);
                    intent.putExtra("num",ms_druginfo_pop_num.getText().toString());//总数量
                    intent.putExtra("price",ms_druginfo_pop_price.getText().toString());//单价
                    startActivity(intent);
                }
                else if (type==0){//加入购物车
                    Toast.makeText(MS_drugInfo_activity.this,"加入购物车数量："+ms_druginfo_pop_num.getText().toString(),Toast.LENGTH_SHORT).show();
                }
                popupWindow.dismiss();

            }
        });
        popOutside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow!=null){
                    popupWindow.dismiss();
                }
            }
        });
        popupWindow=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setContentView(v);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_ms_drug_info);
        popupWindow.setAnimationStyle(R.style.popup_anim);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }
    //返回键
    public void goBack(View view) {
        if (view!=null){
            switch (view.getId()){
                case R.id.ms_druginfo_return:
                    finish();
                    break;
            }
        }
    }


    public void numChange(View view){
        if (view!=null){
            switch (view.getId()){
                case R.id.ms_druginfo_pop_numAdd://jia
                    int num=Integer.parseInt(ms_druginfo_pop_num.getText().toString());
                    ms_druginfo_pop_num.setText((num+1)+"");
                    break;
                case R.id.ms_druginfo_pop_numMinus://jian
                    int nu=Integer.parseInt(ms_druginfo_pop_num.getText().toString());
                    if (nu>1){
                        ms_druginfo_pop_num.setText((nu-1)+"");
                    }
                    else {
                        Toast.makeText(MS_drugInfo_activity.this,"亲，已经不能再减了",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        DrugId=getIntent().getIntExtra(MyIntent.intent_MS_drugInfo,-1);
        if (DrugId!=-1){
            getDrugInfo();
        }
    }
    public void getDrugInfo() {
        Map<String,String> mp=new HashMap<String,String>();
        mp.put("id",DrugId+"");
        okhttp.getCall(Ip.url_F+Ip.interface_DrugInfo,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
                Log.e(TAG,e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resultStr=response.body().string();
                handler.sendEmptyMessage(1);
                Log.i(TAG+"-药品信息-",resultStr);
            }
        });
    }

//
//    public void selectPro(View view){
//        switch (view.getId()){
//            case R.id.ms_druginfo_pop_r1:
//                if (!ms_druginfo_pop_r1.isSelected()) {
//                    ms_druginfo_pop_r1.setSelected(true);
//                    ms_druginfo_pop_r2.setSelected(false);
//                }
//                break;
//            case R.id.ms_druginfo_pop_r2:
//                if (!ms_druginfo_pop_r2.isSelected()){
//                    ms_druginfo_pop_r2.setSelected(true);
//                    ms_druginfo_pop_r1.setSelected(false);
//                }
//                break;
//        }
//    }
}
