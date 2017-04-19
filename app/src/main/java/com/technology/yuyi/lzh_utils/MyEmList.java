package com.technology.yuyi.lzh_utils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by wanyu on 2017/4/18.
 */

public class MyEmList extends ListView{
    private Context context;
    public MyEmList(Context context) {
        super(context);
    }

    public MyEmList(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
    public void setErr(){
        if (this.getAdapter()!=null&&this.getAdapter().getCount()>0){
            return;
        }
        else {
            if (this.getHeaderViewsCount()>0){
                for (int i=0;i<this.getHeaderViewsCount();i++){
                    this.removeViewAt(i);
                }
            }


        }
    }



}
