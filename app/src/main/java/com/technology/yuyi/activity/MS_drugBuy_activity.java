package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanyu on 2017/2/27.
 */
//intent.putExtra("num",ms_druginfo_pop_num.getText().toString());//总数量
//        intent.putExtra("price",ms_druginfo_pop_price.getText().toString());//单价
//立即购买页面
public class MS_drugBuy_activity extends Activity{
    private PopupWindow popupW;//显示支付方式的window
    private TextView ms_drugbuy_price;//单价
    private TextView ms_drugbuy_numX;//x数量
    private TextView ms_drugbuy_num;//共 件
    private TextView ms_drugbuy_totalPrice;//总价
    private TextView ms_drugbuy_submit;
    private int num;//总数量
    private float price;//单价
    private ImageView ms_drugbuy_imageA,ms_drugbuy_imageB;//及时送药A、预约送药B
    //弹窗中支付方式的选择以及确认按钮
    private TextView ms_drugbuy_paymentAlipay,ms_drugbuy_paymentWX,ms_drugbuy_paymentCashOnDelivery,ms_drugbuy_paymentSubmit;//支付宝，微信，货到付款，确认
    private List<TextView>li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_drugbuy);
        num=Integer.parseInt(getIntent().getStringExtra("num"));
        price=Float.parseFloat(getIntent().getStringExtra("price"));
        initView();

    }

    private void initView() {
        ms_drugbuy_price= (TextView) findViewById(R.id.ms_drugbuy_price);
        ms_drugbuy_numX= (TextView) findViewById(R.id.ms_drugbuy_numX);
        ms_drugbuy_num= (TextView) findViewById(R.id.ms_drugbuy_num);
        ms_drugbuy_totalPrice= (TextView) findViewById(R.id.ms_drugbuy_totalPrice);

        ms_drugbuy_price.setText("￥"+price);
        ms_drugbuy_numX.setText("X"+num);
        ms_drugbuy_num.setText("共 "+num+" 件");
        ms_drugbuy_totalPrice.setText("￥"+(price*num));



        ms_drugbuy_imageA= (ImageView) findViewById(R.id.ms_drugbuy_imageA);
        ms_drugbuy_imageB= (ImageView) findViewById(R.id.ms_drugbuy_imageB);
        ms_drugbuy_imageA.setSelected(true);
        ms_drugbuy_imageB.setSelected(false);

        ms_drugbuy_submit= (TextView) findViewById(R.id.ms_drugbuy_submit);

    }
    //确认付款的按钮
    public void SubmitShoppingOrder(View view) {
//        Toast.makeText(MS_drugBuy_activity.this,"选择支付方式",Toast.LENGTH_SHORT).show();
        if (view!=null){
            switch (view.getId()){
                case R.id.ms_drugbuy_submit://提交订单，弹出支付选项
                    showWindowPayment();//显示可用支付方式
                    break;
            }
        }
    }
    public void goBack(View view) {
        if (view!=null){
            if (view.getId()==R.id.ms_drugbuy_return){
                finish();
            }
        }
    }
//及时送药，预约送药
    public void setSelect(View view) {
        if (view!=null){
            switch (view.getId()){
                case R.id.ms_drugbuy_imageA://及时送药
                    if (!ms_drugbuy_imageA.isSelected()){
                        ms_drugbuy_imageA.setSelected(true);
                        ms_drugbuy_imageB.setSelected(false);
                    }
                    break;
                case R.id.ms_drugbuy_imageB://预约送药
                    if (!ms_drugbuy_imageB.isSelected()){
                        ms_drugbuy_imageB.setSelected(true);
                        ms_drugbuy_imageA.setSelected(false);
                    }
                    break;
            }
        }
    }



    //弹窗显示支付方式：支付宝，微信，货到付款
    private void showWindowPayment() {
        View v=getLayoutInflater().inflate(R.layout.ms_drugbuy_popupwindow, null);

        ms_drugbuy_paymentAlipay= (TextView) v.findViewById(R.id.ms_drugbuy_paymentAlipay);
        ms_drugbuy_paymentWX= (TextView) v.findViewById(R.id.ms_drugbuy_paymentWX);
        ms_drugbuy_paymentCashOnDelivery= (TextView) v.findViewById(R.id.ms_drugbuy_paymentCashOnDelivery);
        ms_drugbuy_paymentSubmit= (TextView) v.findViewById(R.id.ms_drugbuy_paymentSubmit);
        li=new ArrayList<>();
        li.add(ms_drugbuy_paymentAlipay);
        li.add(ms_drugbuy_paymentWX);
        li.add(ms_drugbuy_paymentCashOnDelivery);
        setSelectColor(0);//默认选中支付宝


        RelativeLayout ms_drugbuy_parentRelative= (RelativeLayout) findViewById(R.id.ms_drugbuy_parentRelative);

        ms_drugbuy_parentRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupW!=null){
                    popupW.dismiss();
                }
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
        popupW.setContentView(v);
        popupW.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popupW.setTouchable(true);
        popupW.setFocusable(true);
        popupW.setOutsideTouchable(true);
        RelativeLayout parent= (RelativeLayout) findViewById(R.id.ms_drugbuy_parentRelative);
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



    //弹窗中的支付方式选择以及确认按钮
    // ,,,
    public void selectPayment(View view) {
        if (view!=null){
            switch (view.getId()){
                case R.id.ms_drugbuy_paymentAlipay://支付宝付款
                    setSelectColor(0);
                    break;
                case R.id.ms_drugbuy_paymentWX://微信支付
                    setSelectColor(1);
                    break;
                case R.id.ms_drugbuy_paymentCashOnDelivery://货到付款
                    setSelectColor(2);
                    break;
                case R.id.ms_drugbuy_paymentSubmit://确认
                    switch(getSelectPos()){//获取到被选中到支付方式0支付宝，1微信，2货到付款
                        case 0:
                            Toast.makeText(MS_drugBuy_activity.this,"支付宝支付",Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(MS_drugBuy_activity.this,"微信支付",Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(MS_drugBuy_activity.this,"货到付款",Toast.LENGTH_SHORT).show();
                            break;
                    }
                    if (popupW!=null){
                        popupW.dismiss();
                    }
                    break;
            }
        }
    }

    public void setSelectColor(int selectColor) {
        for (int i=0;i<li.size();i++){
            if (selectColor!=i){
                li.get(i).setSelected(false);
            }
            else {
                li.get(i).setSelected(true);
            }
        }
    }

    public int getSelectPos() {
        int postion=0;//默认支付宝
        for (int i=0;i<li.size();i++){
            if (li.get(i).isSelected()){
                postion=i;
            }
        }

        return postion;
    }
}
