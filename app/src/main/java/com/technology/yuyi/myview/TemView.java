package com.technology.yuyi.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by liuhaidong on 2017/2/20.
 */

public class TemView extends View {
    private Context mContext;
    private DisplayMetrics mDisplayMetrics;
    private ArrayList<Integer> YData = new ArrayList<>();
    private ArrayList<Integer> XDate = new ArrayList<>();
    private ArrayList<Float> mTemData = new ArrayList<>();

    private final String paintColor="#6a6a6a";
    private Paint YXpaint;
    private Paint linePaint;
    private Paint mSolidCirclePaint;
    private Paint mStrokeCirclePaint;

    private float YEndPoint;//y轴终点坐标
    private float XScale;//x轴刻度
    private float YScale;//y轴刻度
    private float YEachBlood;
    private float mBigCircleRadius;
    private float mSmallCircleRadius;
    public TemView(Context context) {
        super(context);
        this.mContext=context;
        initData();
    }

    public TemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        initData();
    }

    public TemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        initData();
    }

    public void setTemInfo(ArrayList<Integer> YData, ArrayList<Integer> XDate, ArrayList<Float> temData) {
        this.YData = YData;
        this.XDate = XDate;
        this.mTemData = temData;
    }

    public void initData() {
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        float widthScreen = (float) mDisplayMetrics.widthPixels;
        float heightScreen = (float) mDisplayMetrics.heightPixels;
        mBigCircleRadius = dip2px(4);
        mSmallCircleRadius = dip2px(2.5f);
        YEndPoint = heightScreen / 3.5f;
        XScale = widthScreen / 10.0f;
        YScale = YEndPoint / 7.0f - 20;
        YEachBlood = YScale / 20.0f;

        //y,x轴数据画笔
        YXpaint = new Paint();
        YXpaint.setAntiAlias(true);
        YXpaint.setColor(Color.parseColor(paintColor));
        YXpaint.setTextSize(dip2px(10));
        YXpaint.setStrokeWidth(dip2px(0.5f));
        YXpaint.setStyle(Paint.Style.FILL_AND_STROKE);
        YXpaint.setTextAlign(Paint.Align.CENTER);

        //折线画笔
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor(paintColor));
        linePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        //实心圆圈画笔
        mSolidCirclePaint = new Paint();
        mSolidCirclePaint.setStyle(Paint.Style.FILL);
        mSolidCirclePaint.setStrokeWidth(dip2px(2));
        mSolidCirclePaint.setColor(Color.parseColor(paintColor));
        mSolidCirclePaint.setAntiAlias(true);

        //实心圆圈外边的大圆画笔
        mStrokeCirclePaint = new Paint();
        mStrokeCirclePaint.setColor(Color.parseColor(paintColor));
        mStrokeCirclePaint.setAntiAlias(true);
        mStrokeCirclePaint.setStyle(Paint.Style.STROKE);
        mStrokeCirclePaint.setStrokeWidth(dip2px(1));

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Y轴体温刻度
        for (int i = 0; i < YData.size(); i++) {
            try {
                //Y血量小横线
                // canvas.drawLine(YScale-20, YEndPoint-i*YScale,YScale+10 ,YEndPoint-i*YScale, mPaintXY);
                //Y血量刻度值
                canvas.drawText(YData.get(i) + "", XScale - 20, YEndPoint - i * YScale, YXpaint);
            } catch (Exception e) {
            }
        }

        //x轴日期刻度
        for (int i = 0; i < XDate.size(); i++) {
            if (i==0){
                canvas.drawText("3月"+XDate.get(i) + "日", XScale + XScale * (i + 1), YEndPoint + XScale , YXpaint);
            }else {
            canvas.drawText(XDate.get(i) + "日", XScale + XScale * (i + 1), YEndPoint + XScale , YXpaint);}
        }

        //折线走势
        for (int i = 0; i < mTemData.size(); i++) {
            //最后一个数据大圆套小圆
            if (i == mTemData.size() - 1) {
                //低压
                canvas.drawCircle(XScale + XScale * (i + 1), Ycode(mTemData.get(i)), mBigCircleRadius, mStrokeCirclePaint);
                canvas.drawCircle(XScale + XScale * (i + 1), Ycode(mTemData.get(i)), mSmallCircleRadius, mSolidCirclePaint);
                //否则都是小圈
            } else {
                canvas.drawCircle(XScale + XScale * (i + 1), Ycode(mTemData.get(i)), mSmallCircleRadius, mSolidCirclePaint);
            }
            //画折线
            try {
                canvas.drawLine(XScale + XScale * (i + 1), Ycode(mTemData.get(i)), XScale + XScale * (i + 2), Ycode(mTemData.get(i+1)), linePaint);
                canvas.drawLine(XScale + XScale * (i + 1), Ycode(mTemData.get(i)), XScale + XScale * (i + 2), Ycode(mTemData.get(i+1)), linePaint);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    public float dip2px(float dpValue) {
        float scale = mDisplayMetrics.density;
        return (dpValue * scale + 0.5f);
    }

    /**
     * 每个圆圈的纵坐标
     *
     * @param a 集合中获取的血量的多少
     * @return
     */
    private float Ycode(float a) {
        float e = YEndPoint - (a - 35) * YScale;
        return e;
    }
}
