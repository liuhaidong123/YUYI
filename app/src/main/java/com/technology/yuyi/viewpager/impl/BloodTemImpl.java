package com.technology.yuyi.viewpager.impl;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import com.technology.yuyi.R;

/**
 * Created by liuhaidong on 2017/2/21.
 */

public class BloodTemImpl implements ViewPager.OnPageChangeListener {

    private ImageView[] mImgCircleArr;

    public BloodTemImpl(ImageView[] mImgCircleArr) {
        this.mImgCircleArr = mImgCircleArr;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setImageBackground(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
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
