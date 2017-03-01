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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.R;
//药品详细信息页面
public class MS_drugInfo_activity extends Activity {
    private PopupWindow popupWindow;
    private ImageView ms_druginfo_pop_numAdd,ms_druginfo_pop_numMinus;//弹窗中加减的view
    private TextView ms_druginfo_pop_num;//pop中显示购买数量的view
    private TextView ms_druginfo_pop_price;//pop中显示单价的view
    private TextView ms_druginfo_pop_r1,ms_druginfo_pop_r2;//弹窗的中的属性1，2
    private int type=-1;
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
                    showWindow(1);
                    break;
                case R.id.ms_drug_info_shoppingCart://加入购物车
                    showWindow(0);
                    break;
            }
        }
    }

    //加入购物车，立即购买pos=0：加入购物车 pos=1:立即购买
    private void showWindow(int pos) {
        type=pos;
        View v=getLayoutInflater().inflate(R.layout.ms_druginfo_popupwindow, null);
        ms_druginfo_pop_r1= (TextView) v.findViewById(R.id.ms_druginfo_pop_r1);
        ms_druginfo_pop_r2= (TextView) v.findViewById(R.id.ms_druginfo_pop_r2);
        ms_druginfo_pop_r1.setSelected(true);
        ms_druginfo_pop_r2.setSelected(false);
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

    public void selectPro(View view){
        switch (view.getId()){
            case R.id.ms_druginfo_pop_r1:
                if (!ms_druginfo_pop_r1.isSelected()) {
                    ms_druginfo_pop_r1.setSelected(true);
                    ms_druginfo_pop_r2.setSelected(false);
                }
                break;
            case R.id.ms_druginfo_pop_r2:
                if (!ms_druginfo_pop_r2.isSelected()){
                    ms_druginfo_pop_r2.setSelected(true);
                    ms_druginfo_pop_r1.setSelected(false);
                }
                break;
        }
    }
}
