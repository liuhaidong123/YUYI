package com.technology.yuyi.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by liuhaidong on 2017/8/25.
 */

public class SanJiao extends View {

    private DisplayMetrics mDisplayMetrics;
    private Context mContext;
    private double mTemNum;
    private TextView textView;

    public SanJiao(Context context, double mTemNum, TextView textView) {
        super(context);
        this.mContext = context;
        this.mTemNum = mTemNum;
        this.textView = textView;
    }

    public SanJiao(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public SanJiao(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //屏幕信息类
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStrokeWidth(dip2px(1));

        float startY = ((getHeight() - dip2px(75f) - dip2px(24) - dip2px(35)) / 41);//每个小格的刻度值
        float a = (float) (getHeight() -dip2px(75f) - dip2px(24)- dip2px(35) - (mTemNum - 34) * 5 * startY);//计算每次体温下，三角形正对的刻度
        Path path = new Path();//画三角形
        path.moveTo(getWidth() / 2, a+dip2px(35));
        path.lineTo(getWidth() / 2 + dip2px(30), a+dip2px(25));
        path.lineTo(getWidth() / 2 + dip2px(30), a+dip2px(45));
        path.close();
        canvas.drawPath(path, paint);
        Log.e("a=",a+"");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素
     *
     * @param dpValue
     * @return
     */
    public float dip2px(double dpValue) {
        float scale = mDisplayMetrics.density;
        return (float) (dpValue * scale + 0.5f);
    }

}
