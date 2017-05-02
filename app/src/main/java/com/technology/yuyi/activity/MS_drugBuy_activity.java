package com.technology.yuyi.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.alipay.sdk.app.EnvUtils;
//import com.alipay.sdk.app.PayTask;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_UserAddress;
import com.technology.yuyi.lzh_WXutils.Constants;
//import com.technology.yuyi.lzh_alipay.AuthResult;
//import com.technology.yuyi.lzh_alipay.OrderInfoUtil2_0;
//import com.technology.yuyi.lzh_alipay.PayResult;
//import com.technology.yuyi.lzh_alipay.alipayEnvironment;
//import com.technology.yuyi.lzh_alipay.alipayId;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.ResCode;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/2/27.
 */
//intent.putExtra("num",ms_druginfo_pop_num.getText().toString());//总数量
//        intent.putExtra("price",ms_druginfo_pop_price.getText().toString());//单价
//立即购买页面
public class MS_drugBuy_activity extends Activity{
    //支付宝
//
//    /** 支付宝支付业务：入参app_id */
//    public static final String APPID = alipayId.APPID;
//    /** 支付宝账户登录授权业务：入参pid值 */
//    public static final String PID = "";
//    /** 支付宝账户登录授权业务：入参target_id值 */
//    public static final String TARGET_ID = "";
//    /** 商户私钥，pkcs8格式 */
//    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
//    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
//    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
//    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
//    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
//    public static final String RSA2_PRIVATE =alipayId.RSA2_PRIVATE;
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    //以上支付宝
    //微信
//    private IWXAPI api;
    //微信
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

    private RelativeLayout  ms_drug_buy_Address,ms_drug_buy_noAddress;
    private PopupWindow popSucc;
    ImageView drugbuy_window_succ_close;
    TextView drug_window_succ_bottom_submit;

    PopupWindow popFail;
    ImageView drugbuy_window_fail_close;
    TextView drug_window_fail_bottom_submit;


    TextView ms_drugbuy_addressName;
    TextView ms_drugbuy_address,ms_drugbuy_phonenum;
//    @SuppressLint("HandlerLeak")
//    private Handler mHandler = new Handler() {
//        @SuppressWarnings("unused")
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case SDK_PAY_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
//                    /**
//                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
//                     */
//                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
//                    String resultStatus = payResult.getResultStatus();
//                    // 判断resultStatus 为9000则代表支付成功
//                    if (TextUtils.equals(resultStatus, "9000")) {
//                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                        Toast.makeText(MS_drugBuy_activity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                        showSuccessPay();
//                    } else {
//                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
//                        Toast.makeText(MS_drugBuy_activity.this, "支付失败", Toast.LENGTH_SHORT).show();
//                        showFailurePay();
//                    }
//                    break;
//                }
//                case SDK_AUTH_FLAG: {
//                    @SuppressWarnings("unchecked")
//                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
//                    String resultStatus = authResult.getResultStatus();
//
//                    // 判断resultStatus 为“9000”且result_code
//                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
//                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
//                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
//                        // 传入，则支付账户为该授权账户
//                        Toast.makeText(MS_drugBuy_activity.this,
//                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
//                                .show();
//                    } else {
//                        // 其他状态值则为授权失败
//                        Toast.makeText(MS_drugBuy_activity.this,
//                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();
//
//                    }
//                    break;
//                }
//                default:
//                    break;
//            }
//        };
//    };



    private String resultStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(MS_drugBuy_activity.this);
                    break;
                case 1:
                    if (!"".equals(resultStr)&&!TextUtils.isEmpty(resultStr)){
                        bean_UserAddress ad= gson.gson.fromJson(resultStr,bean_UserAddress.class);
                        ms_drugbuy_addressName.setText("收货人："+ad.getTrueName());
                        ms_drugbuy_address.setText("详细信息："+ad.getAreaName()+ad.getAddress());
                        ms_drugbuy_phonenum.setText("联系电话："+ad.getTelephone());
                    }
                    else {//用户还没有添加地址
                        ms_drug_buy_Address.setVisibility(View.GONE);
                        ms_drug_buy_noAddress.setVisibility(View.VISIBLE);
                    }

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_drugbuy);
        num=Integer.parseInt(getIntent().getStringExtra("num"));
        price=Float.parseFloat(getIntent().getStringExtra("price"));
        initView();
//        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
//        api.registerApp(Constants.APP_ID);

        getUserAddress();//获取默认收货地址

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

        ms_drugbuy_submit= (TextView) findViewById(R.id.ms_drugbuy_submitBuy);

        ms_drugbuy_addressName= (TextView) findViewById(R.id.ms_drugbuy_addressName);
        ms_drugbuy_address= (TextView) findViewById(R.id.ms_drugbuy_address);
        ms_drugbuy_phonenum= (TextView) findViewById(R.id.ms_drugbuy_phonenum);
        ms_drug_buy_Address= (RelativeLayout) findViewById(R.id.ms_drug_buy_Address);
        ms_drug_buy_noAddress= (RelativeLayout) findViewById(R.id.ms_drug_buy_noAddress);

        ms_drug_buy_noAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MS_drugBuy_activity.this,My_address_Activity.class), ResCode.Requst_drugbuy);
            }
        });

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
//                            payV2(ms_drugbuy_paymentSubmit);
                            break;
                        case 1:
                            Toast.makeText(MS_drugBuy_activity.this,"微信支付",Toast.LENGTH_SHORT).show();
//                            pay(ms_drugbuy_paymentSubmit);
                            break;
                        case 2:
                            Toast.makeText(MS_drugBuy_activity.this,"货到付款",Toast.LENGTH_SHORT).show();
                            break;
                    }
                    if (popupW!=null){
                        popupW.dismiss();
                    }
                    break;
                case R.id.drugbuy_window_succ_close://付款成功中的关闭按钮
                    popSucc.dismiss();

                    break;
                case R.id.drug_window_succ_bottom_submit://付款成功中的确定按钮,许改动，跳转到订单详情页面
                    popSucc.dismiss();
                    break;
                case  R.id.drugbuy_window_fail_close:
                    popFail.dismiss();
                    break;
                case R.id.drug_window_fail_bottom_submit:
                    popFail.dismiss();
                    showWindowPayment();
                    break;
                case R.id.ms_drugbuy_addressEdit://
                    startActivityForResult(new Intent(MS_drugBuy_activity.this,My_address_Activity.class), ResCode.Requst_drugbuy);
                    break;
            }
        }
    }

    //修改地址返回的数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==ResCode.Requst_drugbuy){
            if (resultCode==ResCode.Response_drugbuy){
                Bundle bundle=data.getBundleExtra("bundle");
                if (bundle!=null){
                    ms_drugbuy_addressName.setText("收货人："+bundle.getString("name"));
                    ms_drugbuy_address.setText("详细地址："+bundle.getString("address"));
                    ms_drugbuy_phonenum.setText("联系电话："+bundle.getString("phonenum"));
                    ms_drug_buy_noAddress.setVisibility(View.GONE);
                    ms_drug_buy_Address.setVisibility(View.VISIBLE);
                }
                else {
                    Log.e("MS_drugBuy_activity","-onActivityResult-"+"返回的地址信息等错误，请检查");
                }
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

//    /**
//     * 支付宝支付业务
//     * @param v
//     */
//    public void payV2(View v) {
//        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialoginterface, int i) {
//                            //
//                            finish();
//                        }
//                    }).show();
//            return;
//        }
//
//        /**
//         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
//         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
//         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
//         * orderInfo的获取必须来自服务端；
//         */
//        alipayEnvironment.setEnvironment();
//        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);
//
//        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
//        final String orderInfo = orderParam + "&" + sign;
//
//        Runnable payRunnable = new Runnable() {
//
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(MS_drugBuy_activity.this);
//                Map<String, String> result = alipay.payV2(orderInfo, true);
//                Log.i("msp", result.toString());
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
//    }
//
//
//
//    //吊起微信支付（假订单）
//    public void pay(View view) {
//        PayReq request = new PayReq();
//        request.appId = "wxd930ea5d5a258f4f";
//        request.partnerId = "1900000109";
//        request.prepayId= "1101000000140415649af9fc314aa427";
//        request.packageValue = "Sign=WXPay";
//        request.nonceStr= "1101000000140429eb40476f8896f4c9";
//        request.timeStamp= "1398746574";
//        request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
//        api.sendReq(request);
//    }

    public void SubmitOrder(View view) {
        showWindowPayment();//显示可用支付方式
    }

    //支付成功的弹窗
    public void showSuccessPay(){
        View v=getLayoutInflater().inflate(R.layout.ms_drugbuy_window_paysuccess, null);
        drugbuy_window_succ_close= (ImageView) v.findViewById(R.id.drugbuy_window_succ_close);
//        drug_window_succ_img_succ.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popSucc.dismiss();
//            }
//        });
        drug_window_succ_bottom_submit= (TextView) v.findViewById(R.id.drug_window_succ_bottom_submit);

//        drug_window_succ_bottom_submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupW.dismiss();//许改动，应跳转到订单详细信息页面
//            }
//        });
        popSucc=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        popSucc.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popSucc.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popSucc.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popSucc.setContentView(v);
        popSucc.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popSucc.setTouchable(true);
        popSucc.setFocusable(true);
        popSucc.setOutsideTouchable(true);
        RelativeLayout parent= (RelativeLayout) findViewById(R.id.ms_drugbuy_parentRelative);
        popSucc.setAnimationStyle(R.style.popup2_anim);
        popSucc.showAtLocation(parent, Gravity.CENTER, 0,0);
        popSucc.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }
    //付款失败的弹窗
    public void showFailurePay(){
        View v=getLayoutInflater().inflate(R.layout.ms_drugbuy_window_failure, null);
        drugbuy_window_fail_close= (ImageView) v.findViewById(R.id.drugbuy_window_fail_close);
        drug_window_fail_bottom_submit= (TextView) v.findViewById(R.id.drug_window_fail_bottom_submit);

        popFail=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        popFail.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popFail.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popFail.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popFail.setContentView(v);
        popFail.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        popFail.setTouchable(true);
        popFail.setFocusable(true);
        popFail.setOutsideTouchable(true);
        RelativeLayout parent= (RelativeLayout) findViewById(R.id.ms_drugbuy_parentRelative);
        popFail.setAnimationStyle(R.style.popup2_anim);
        popFail.showAtLocation(parent, Gravity.CENTER, 0,0);
        popFail.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }


    //获取用户的信息
    public void getUserAddress(){
        ms_drug_buy_Address.setVisibility(View.VISIBLE);
        ms_drug_buy_noAddress.setVisibility(View.GONE);
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
