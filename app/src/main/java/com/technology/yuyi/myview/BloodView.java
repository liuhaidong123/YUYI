package com.technology.yuyi.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by liuhaidong on 2017/2/20.
 */

public class BloodView extends View {
    private ArrayList<Integer> YBlood = new ArrayList<>();
    private ArrayList<String> XDate = new ArrayList<>();
    private ArrayList<Integer> mHeightBloodData = new ArrayList<>();
    private ArrayList<Integer> mLowBloodData = new ArrayList<>();

    private final String paintColor = "#22f3f6";
    private final String lineLowBloodColor = "#7ed66b";
    private final String blackColor = "#474e5b";
    private Paint mPaintXY;
    private Paint mHeightPaintTV;
    private Paint mLowPaintTV;
    private Paint mPaintBloodHeightLine;
    private Paint mPaintBloodLowLine;
    private Paint mPaintSloidCircle;
    private Paint mPaintStrokeCircle2;
    private Paint mPaintSloidLowCircle;
    private Paint mPaintStrokeLowCircle2;
    private Paint mBlackPaint;

    private float YEndPoint;//y轴终点坐标
    private float XScale;//x轴刻度
    private float YScale;//y轴刻度
    private float YEachBlood;
    private float mBigCircleRadius;
    private float mSmallCircleRadius;
    private Context mContext;
    private DisplayMetrics mDisplayMetrics;

    public BloodView(Context context) {
        super(context);
        this.mContext = context;

    }

    public BloodView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

    }

    public BloodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;


    }

    public void setInfo(ArrayList<Integer> YBlood, ArrayList<String> XDate, ArrayList<Integer> mHeightBloodData, ArrayList<Integer> mLowBloodData) {
        this.YBlood = YBlood;
        this.XDate = XDate;
        this.mHeightBloodData = mHeightBloodData;
        this.mLowBloodData = mLowBloodData;
        initPaint();
    }

    public void initPaint() {
        //屏幕信息类
        mDisplayMetrics = mContext.getResources().getDisplayMetrics();
        mBigCircleRadius = dip2px(4);
        mSmallCircleRadius = dip2px(2.5f);

        YEndPoint = getHeight();
        YScale = YEndPoint / 10.0f;
        YEachBlood = YScale / 20.0f;
        XScale = getWidth() / 8.0f;

        Log.e("YEndPoint", YEndPoint + "");
        Log.e("XScale", XScale + "");
        Log.e("YScale", YScale + "");
        Log.e("YEachBlood", YEachBlood + "");

        //x,y轴画笔
        mPaintXY = new Paint();
        mPaintXY.setColor(Color.parseColor(paintColor));
        mPaintXY.setAntiAlias(true);
        mPaintXY.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintXY.setTextSize(dip2px(10));
        mPaintXY.setStrokeWidth(dip2px(0.3f));
        mPaintXY.setTextAlign(Paint.Align.CENTER);

        //高压文字画笔
        mHeightPaintTV = new Paint();
        mHeightPaintTV.setColor(Color.parseColor(paintColor));
        mHeightPaintTV.setAntiAlias(true);
        mHeightPaintTV.setStyle(Paint.Style.FILL_AND_STROKE);
        mHeightPaintTV.setTextSize(dip2px(12));
        mHeightPaintTV.setStrokeWidth(dip2px(0.3f));
        mHeightPaintTV.setTextAlign(Paint.Align.CENTER);

        //低压文字画笔
        mLowPaintTV = new Paint();
        mLowPaintTV.setColor(Color.parseColor(lineLowBloodColor));
        mLowPaintTV.setAntiAlias(true);
        mLowPaintTV.setStyle(Paint.Style.FILL_AND_STROKE);
        mLowPaintTV.setTextSize(dip2px(12));
        mLowPaintTV.setStrokeWidth(dip2px(0.3f));
        mLowPaintTV.setTextAlign(Paint.Align.CENTER);
        //高压折线
        mPaintBloodHeightLine = new Paint();
        mPaintBloodHeightLine.setStrokeWidth(dip2px(2));
        mPaintBloodHeightLine.setColor(Color.parseColor(paintColor));
        mPaintBloodHeightLine.setAntiAlias(true);
        mPaintBloodHeightLine.setStyle(Paint.Style.STROKE);
        //低压折线
        mPaintBloodLowLine = new Paint();
        mPaintBloodLowLine.setStrokeWidth(dip2px(2));
        mPaintBloodLowLine.setColor(Color.parseColor(lineLowBloodColor));
        mPaintBloodLowLine.setAntiAlias(true);
        mPaintBloodLowLine.setStyle(Paint.Style.STROKE);
        //高压实心圆圈
        mPaintSloidCircle = new Paint();
        mPaintSloidCircle.setStyle(Paint.Style.FILL);
        mPaintSloidCircle.setStrokeWidth(dip2px(2));
        mPaintSloidCircle.setColor(Color.parseColor(paintColor));
        mPaintSloidCircle.setAntiAlias(true);


//        mBlackPaint= new Paint();
//        mBlackPaint.setStyle(Paint.Style.FILL);
//        mBlackPaint.setStrokeWidth(dip2px(2));
//        mBlackPaint.setColor(Color.parseColor(blackColor));
//        mBlackPaint.setAntiAlias(true);


        //高压实心圆圈外边的圆圈
        mPaintStrokeCircle2 = new Paint();
        mPaintStrokeCircle2.setStyle(Paint.Style.STROKE);
        mPaintStrokeCircle2.setStrokeWidth(dip2px(1));
        mPaintStrokeCircle2.setColor(Color.parseColor(paintColor));
        mPaintStrokeCircle2.setAntiAlias(true);


        //低压实心圆圈
        mPaintSloidLowCircle = new Paint();
        mPaintSloidLowCircle.setStyle(Paint.Style.FILL);
        mPaintSloidLowCircle.setStrokeWidth(dip2px(2));
        mPaintSloidLowCircle.setColor(Color.parseColor(lineLowBloodColor));
        mPaintSloidLowCircle.setAntiAlias(true);

        //低压实心圆圈外边的圆圈
        mPaintStrokeLowCircle2 = new Paint();
        mPaintStrokeLowCircle2.setStyle(Paint.Style.STROKE);
        mPaintStrokeLowCircle2.setStrokeWidth(dip2px(1));
        mPaintStrokeLowCircle2.setColor(Color.parseColor(lineLowBloodColor));
        mPaintStrokeLowCircle2.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画折线


        if (YBlood.size()!=0){
            canvas.drawLine(XScale*6, YScale, XScale*7, YScale, mPaintBloodHeightLine);//高压说明线
            canvas.drawLine(XScale*6, YScale*2, XScale*7, YScale*2, mPaintBloodLowLine);//低压说明线
            canvas.drawCircle(XScale*7, YScale, mBigCircleRadius, mPaintStrokeCircle2);
            canvas.drawCircle(XScale*7, YScale*2, mBigCircleRadius, mPaintStrokeLowCircle2);

            canvas.drawCircle(XScale*7, YScale, mSmallCircleRadius, mPaintSloidCircle);
            canvas.drawCircle(XScale*7, YScale*2, mSmallCircleRadius, mPaintSloidLowCircle);

            canvas.drawText("高压",XScale*7.5f, YScale-(mPaintXY.ascent() + mPaintXY.descent() ) , mHeightPaintTV);
            canvas.drawText("低压",XScale*7.5f, YScale*2-(mPaintXY.ascent() + mPaintXY.descent() ) , mLowPaintTV);


        }

        //Y血量刻度
        for (int i = 0; i < YBlood.size(); i++) {
            try {
                //Y血量小横线
                // canvas.drawLine(YScale-20, YEndPoint-i*YScale,YScale+10 ,YEndPoint-i*YScale, mPaintXY);

                //Y血量刻度值
                canvas.drawText(YBlood.get(i) + "", dip2px(20), YEndPoint - (2 + i) * YScale - (mPaintXY.ascent() + mPaintXY.descent() / 2), mPaintXY);
            } catch (Exception e) {

            }

        }

        //x轴日期刻度
        for (int i = 0; i < XDate.size(); i++) {
            canvas.drawText(XDate.get(i), XScale + XScale * i, YEndPoint - YScale, mPaintXY);
        }

        //折线走势
        for (int i = 0; i < mHeightBloodData.size(); i++) {
            //最后一个数据大圆套小圆
            if (i == mHeightBloodData.size() - 1) {

                //低压
                canvas.drawCircle(XScale + XScale * i, Ycode(mLowBloodData.get(i)), mBigCircleRadius, mPaintStrokeLowCircle2);
                canvas.drawCircle(XScale + XScale * i, Ycode(mLowBloodData.get(i)), mSmallCircleRadius, mPaintSloidLowCircle);
                //高压
                canvas.drawCircle(XScale + XScale * i, Ycode(mHeightBloodData.get(i)), mBigCircleRadius, mPaintStrokeCircle2);
                canvas.drawCircle(XScale + XScale * i, Ycode(mHeightBloodData.get(i)), mSmallCircleRadius, mPaintSloidCircle);

                //否则都是小圈
            } else {

                canvas.drawCircle(XScale + XScale * i, Ycode(mHeightBloodData.get(i)), mSmallCircleRadius, mPaintSloidCircle);
                canvas.drawCircle(XScale + XScale * i, Ycode(mLowBloodData.get(i)), mSmallCircleRadius, mPaintSloidLowCircle);
            }
            //画折线
            try {
                canvas.drawLine(XScale + XScale * i, Ycode(mHeightBloodData.get(i)), XScale + XScale * (i + 1), Ycode(mHeightBloodData.get(i + 1)), mPaintBloodHeightLine);
                canvas.drawLine(XScale + XScale * i, Ycode(mLowBloodData.get(i)), XScale + XScale * (i + 1), Ycode(mLowBloodData.get(i + 1)), mPaintBloodLowLine);


            } catch (Exception e) {
            }


        }


    }

    /**
     * 每个圆圈的纵坐标
     *
     * @param a 集合中获取的血量的多少
     * @return
     */
    private float Ycode(int a) {

        float e = YEndPoint - a * YEachBlood;
        return e;
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
}
