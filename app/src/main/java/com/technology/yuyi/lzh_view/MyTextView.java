package com.technology.yuyi.lzh_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.technology.yuyi.R;

/**
 * Created by wanyu on 2017/3/24.
 */

public class MyTextView extends View {
    private Paint paint;
    private float precent=1.0f;
    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        paint=new Paint();
        paint.setColor(getResources().getColor(R.color.color_ms_home_disc));
        paint.setAntiAlias(true);
        int leftwid=(int)(canvas.getWidth()*precent);
        int rightwid=canvas.getWidth()-leftwid;
        int hei=canvas.getHeight();
        canvas.drawRect(0,0,leftwid,hei,paint);
        paint.setColor(getResources().getColor(R.color.color_white));
        canvas.drawRect(leftwid,0,canvas.getWidth(),hei,paint);
    }


    public  void setWidth(float precent){
        this.precent=precent;
//        postInvalidate();
        invalidate();
    }
}
