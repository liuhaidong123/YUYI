package com.technology.yuyi.PopupSettings;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.technology.yuyi.R;

/**
 * Created by wanyu on 2017/8/21.
 */

public class Pop {
    static Pop pop;
    private Pop(){

    }
    public static Pop getInstance(){
        if (pop==null){
            pop=new Pop();
        }
        return pop;
    }
    //中心弹窗效果（进入与退出缩放效果）
    public void getCenterSettings(final Activity ac, PopupWindow pop, View parent,View contentView,float alpha,int height,int width){
        ac.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=ac.getWindow().getAttributes();
        params.alpha=0.6f;
        if (alpha!=0f){
            params.alpha=alpha;
        }
        ac.getWindow().setAttributes(params);
        pop.setHeight(height);
        pop.setWidth(width);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(contentView);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setAnimationStyle(R.style.popup3_anim);
        pop.showAtLocation(parent, Gravity.CENTER, 0,0);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ac.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=ac.getWindow().getAttributes();
                params.alpha=1f;
                ac.getWindow().setAttributes(params);
            }
        });
    }
}
