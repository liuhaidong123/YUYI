package com.technology.yuyi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.R;
//药品详细信息页面
public class MS_drugInfo_activity extends Activity {
    private PopupWindow popupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_drug_info);
    }

    //立即购买／加入购物车
    public void shopping(View view) {
        if (view!=null){
            switch (view.getId()){
                case R.id.ms_drug_info_shoppingbuy://立即购买
                    showWindow();
                    break;
                case R.id.ms_drug_info_shoppingCart://加入购物车
                    Toast.makeText(MS_drugInfo_activity.this,"加入购物车",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void showWindow() {

        View v=getLayoutInflater().inflate(R.layout.ms_druginfo_popupwindow, null);
        final RelativeLayout popOutside= (RelativeLayout) v.findViewById(R.id.popOutside);
        TextView ms_druginfo_popupwindow_buy= (TextView) v.findViewById(R.id.ms_druginfo_popupwindow_buy);
        //立即购买的按钮
        ms_druginfo_popupwindow_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MS_drugInfo_activity.this,MS_drugBuy_activity.class);
                startActivity(intent);
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
}
