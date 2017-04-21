package com.technology.yuyi.viewpager.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.technology.yuyi.R;
import com.technology.yuyi.activity.InformationDetailsActivity;
import com.technology.yuyi.bean.AdBean.Rows;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by liuhaidong on 2017/2/21.
 */

public class AdListenerImpl implements ViewPager.OnPageChangeListener {

    private ImageView[] mImgCircleArr;
    private List<Rows> mImgList = new ArrayList<>();
    private ViewPager mAdViewpager;
    private Handler mHandler;
    private SwipeRefreshLayout mResfresh;


    public AdListenerImpl(ImageView[]mImgCircleArr, Handler mHandler, ViewPager mAdViewpager, List<Rows> mImgList, SwipeRefreshLayout swipeRefreshLayout
    ) {
        this.mImgCircleArr = mImgCircleArr;
        this.mHandler = mHandler;
        this.mAdViewpager = mAdViewpager;
        this.mImgList = mImgList;
        this.mResfresh = swipeRefreshLayout;

    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
       // setImageBackground(position % mImgList.size());
        setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //手指开始滑动
        if (state == mAdViewpager.SCROLL_STATE_DRAGGING) {
            mHandler.removeMessages(1);
            mResfresh.setEnabled(false);
            //手指松开后自动滑动
        } else if (state == mAdViewpager.SCROLL_STATE_SETTLING) {
            mHandler.removeMessages(1);
            mResfresh.setEnabled(true);
            //停在某一页
        } else {
           // mHandler.sendEmptyMessageDelayed(1, 3000);
            mResfresh.setEnabled(true);
        }
    }

    /**
     * 停在某一页时，变换小圆点
     *
     * @param selectItems
     */
    private void setImageBackground(int selectItems) {
        for (int i = 0; i < mImgCircleArr.length; i++) {
            if (i == selectItems) {
                mImgCircleArr[i].setBackgroundResource(R.mipmap.select_ad);
            } else {
                mImgCircleArr[i].setBackgroundResource(R.mipmap.no_select_ad);
            }
        }
    }
}
