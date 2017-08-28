package com.technology.yuyi.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by liuhaidong on 2017/8/17.
 */

public class TherC extends View {
    private DisplayMetrics mDisplayMetrics;
    private Context mContext;
    private double mTemNum;

    public TherC(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public TherC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }

    public TherC(Context context) {
        super(context);
        this.mContext = context;
        //this.mTemNum = mTemNum;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //屏幕信息类
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        Paint paint = new Paint();


        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
         /* 设置渐变色 这个正方形的颜色是改变的 */
        Shader mShader = new LinearGradient(getWidth() / 2, dip2px(24), getWidth() / 2, getHeight(),
                new int[]{Color.parseColor("#f654f5"), Color.parseColor("#745ee1")}, null, Shader.TileMode.REPEAT); // 一个材质,打造出一个线性梯度沿著一条线。


        //左边的白线
        paint.setShader(null);
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStrokeWidth(dip2px(4));
        canvas.drawLine(getWidth() / 2 - dip2px(12), dip2px(24), getWidth() / 2 - dip2px(12), getHeight() - dip2px(75), paint);
        //右边的白线
        canvas.drawLine(getWidth() / 2 + dip2px(12), dip2px(24), getWidth() / 2 + dip2px(12), getHeight() - dip2px(75), paint);
        //画上边白色小弧度
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(getWidth() / 2 - dip2px(12), dip2px(24) - dip2px(10), getWidth() / 2 + dip2px(12), dip2px(24) + dip2px(10));
        canvas.drawArc(rectF, 180, 180, false, paint);

        //画上边粉色小弧度
        paint.setColor(Color.parseColor("#f654f5"));
        paint.setStyle(Paint.Style.FILL);
        rectF.set(getWidth() / 2 - dip2px(10), dip2px(24) - dip2px(8), getWidth() / 2 + dip2px(10), dip2px(24) + dip2px(8));
        canvas.drawArc(rectF, 180, 180, false, paint);

        //画下边大圆
        paint.setColor(Color.parseColor("#745ee1"));
        paint.setShader(mShader);
        canvas.drawCircle(getWidth() / 2, getHeight() - dip2px(37.5f) - dip2px(24), dip2px(37.5f), paint);
        Log.e("控件宽", getWidth() + "");
        Log.e("控件高", getHeight() + "");
        //画下边大弧度
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(getWidth() / 2, getHeight() - dip2px(37.5f) - dip2px(24), dip2px(37.5f), paint);
        //中间的粗线
        paint.setShader(mShader);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(dip2px(20));
        canvas.drawLine(getWidth() / 2, dip2px(24), getWidth() / 2, getHeight() - dip2px(65), paint);

        //刻度
        paint.setShader(null);
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStrokeWidth(dip2px(1));
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(dip2px(10));
        int startY = 50;
        int text = 42;
        for (int i = 0; i <= 40; i++) {
            if (i % 5 == 0) {//刻度长线
                canvas.drawLine(getWidth() / 2 - dip2px(10), dip2px(startY), getWidth() / 2, dip2px(startY), paint);
                canvas.drawText(String.valueOf(text), getWidth() / 2 - dip2px(25), dip2px(startY), paint);
                text -= 1;
            } else {//刻度短线
                canvas.drawLine(getWidth() / 2 - dip2px(10), dip2px(startY), getWidth() / 2 - dip2px(5), dip2px(startY), paint);
            }
            startY += 5;
        }


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
