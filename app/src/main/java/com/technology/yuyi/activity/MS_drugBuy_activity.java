package com.technology.yuyi.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.technology.yuyi.R;
import com.technology.yuyi.lzh_WXutils.Constants;
import com.technology.yuyi.lzh_alipay.AuthResult;
import com.technology.yuyi.lzh_alipay.OrderInfoUtil2_0;
import com.technology.yuyi.lzh_alipay.PayResult;
import com.technology.yuyi.lzh_alipay.alipayEnvironment;
import com.technology.yuyi.lzh_alipay.alipayId;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
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

    /** 支付宝支付业务：入参app_id */
    public static final String APPID = alipayId.APPID;
    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String PID = "";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String TARGET_ID = "";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA2_PRIVATE =alipayId.RSA2_PRIVATE;
    public static final String RSA_PRIVATE = "";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    //以上支付宝
    //微信
    private IWXAPI api;
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MS_drugBuy_activity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MS_drugBuy_activity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(MS_drugBuy_activity.this,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(MS_drugBuy_activity.this,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_drugbuy);
        num=Integer.parseInt(getIntent().getStringExtra("num"));
        price=Float.parseFloat(getIntent().getStringExtra("price"));
        initView();
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);
        api.registerApp(Constants.APP_ID);

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
                            payV2(ms_drugbuy_paymentSubmit);
                            break;
                        case 1:
                            Toast.makeText(MS_drugBuy_activity.this,"微信支付",Toast.LENGTH_SHORT).show();
                            pay(ms_drugbuy_paymentSubmit);
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

    /**
     * 支付宝支付业务
     *
     * @param v
     */
    public void payV2(View v) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         * orderInfo的获取必须来自服务端；
         */
        alipayEnvironment.setEnvironment();
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MS_drugBuy_activity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }



    //吊起微信支付（假订单）
    public void pay(View view) {
        PayReq request = new PayReq();
        request.appId = "wxd930ea5d5a258f4f";
        request.partnerId = "1900000109";
        request.prepayId= "1101000000140415649af9fc314aa427";
        request.packageValue = "Sign=WXPay";
        request.nonceStr= "1101000000140429eb40476f8896f4c9";
        request.timeStamp= "1398746574";
        request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
        api.sendReq(request);
    }
}
