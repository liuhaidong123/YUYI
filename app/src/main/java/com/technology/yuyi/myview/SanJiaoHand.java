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

import java.text.DecimalFormat;

/**
 * Created by liuhaidong on 2017/8/28.
 */

public class SanJiaoHand extends View {
    private DisplayMetrics mDisplayMetrics;
    private Context mContext;
    private double mTemNum;
    private TextView textView;
    private TextView du;
    private TextView mPrompt_tv;
    private float endPoint;
    private float startY;
    private DecimalFormat df = new DecimalFormat("######0.0");

    public SanJiaoHand(Context context, double mTemNum, TextView textView, TextView du, TextView mPrompt_tv) {
        super(context);
        this.mContext = context;
        this.mTemNum = mTemNum;
        this.textView = textView;
        this.du = du;
        this.mPrompt_tv = mPrompt_tv;
    }

    public SanJiaoHand(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public SanJiaoHand(Context context, AttributeSet attrs, int defStyleAttr) {
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

        startY =  ((getHeight() - dip2px(75f) - dip2px(24) - dip2px(35)) / 41);//每个小格的刻度值
        endPoint = getHeight() - dip2px(75f) - dip2px(24)-startY*5;
        float  a = (float) (getHeight() - dip2px(75f) - dip2px(24) - dip2px(35) - (mTemNum - 34) * 5 * startY);//计算每次体温下，三角形正对的刻度
        Path path = new Path();//画三角形
        path.moveTo(getWidth() / 2, a + dip2px(35));
        path.lineTo(getWidth() / 2 + dip2px(30), a + dip2px(25));
        path.lineTo(getWidth() / 2 + dip2px(30), a + dip2px(45));
        path.close();
        canvas.drawPath(path, paint);
        if (Double.valueOf(mTemNum) <= 36) {
            mPrompt_tv.setText("*当前体温过低,请查看测量部位");
            mPrompt_tv.setTextColor(Color.parseColor("#1ebeec"));
            textView.setTextColor(Color.parseColor("#1ebeec"));
            du.setTextColor(Color.parseColor("#1ebeec"));
        } else if (Double.valueOf(mTemNum) >= 38) {
            mPrompt_tv.setText("*当前体温过高,请尽快就医");
            mPrompt_tv.setTextColor(Color.parseColor("#f6547a"));
            textView.setTextColor(Color.parseColor("#f6547a"));
            du.setTextColor(Color.parseColor("#f6547a"));
        } else {
            mPrompt_tv.setText("*当前体温正常");
            mPrompt_tv.setTextColor(Color.parseColor("#f654f5"));
            textView.setTextColor(Color.parseColor("#f654f5"));
            du.setTextColor(Color.parseColor("#f654f5"));
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

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public float px2dip(Context context, float pxValue) {
        final float scale = mDisplayMetrics.density;
        return (float) (pxValue / scale + 0.5f);
    }

    float x, y;

    //当你触到按钮时，event.getX();event.getY();是相对于该按钮画布本身的相对位置。
    // 而event.getRawX();event.getRawY();始终是相对于屏幕的位置。
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                Log.e("getx", event.getX() + "");
                Log.e("gety", event.getY() + "");
                Log.e("getRawX", event.getRawX() + "");
                Log.e("getRawY", event.getRawY() + "");
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();
                //在规定范围内移动三角形
                if (y > dip2px(35) && y < endPoint && x < getWidth() / 2 + dip2px(30) && x > getWidth() / 2) {

                   mTemNum=((getHeight() - dip2px(75f) - dip2px(24) - dip2px(35))-(y-dip2px(35)))/startY/5+34;
                    invalidate();
                    //保留一位小数
                    String temNum = df.format(mTemNum);
                    textView.setText(temNum + "");
                }
                // Toast.makeText(mContext, "移动", Toast.LENGTH_SHORT).show();
                break;
            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                //点击三角形显示刻度
                if (y > dip2px(35) && y < endPoint && x < getWidth() / 2 + dip2px(30) && x > getWidth() / 2) {
                    mTemNum=((getHeight() - dip2px(75f) - dip2px(24) - dip2px(35))-(y-dip2px(35)))/startY/5+34;
                    invalidate();
                    //保留一位小数
                    String temNum = df.format(mTemNum);
                    textView.setText(temNum + "");


                }

                //Toast.makeText(mContext, "松开", Toast.LENGTH_SHORT).show();

                break;
        }
        return true;
    }


}
