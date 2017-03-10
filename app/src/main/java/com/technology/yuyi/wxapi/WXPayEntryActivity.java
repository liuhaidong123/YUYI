package com.technology.yuyi.wxapi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.technology.yuyi.R;
import com.technology.yuyi.lzh_WXutils.Constants;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by wanyu on 2017/3/7.
 */

//必须实现接口，重写方法
public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("---------->","code:"+baseResp.errCode);

        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //这里就是支付完成后需要做的事，如跳到哪个页面啥的
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("支付结果：");
            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(baseResp.errCode)));
            builder.show();
        }
    }
}