package com.technology.yuyi.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.technology.yuyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/20.
 */

public class ViewPagerAdAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Integer> mListImgAd = new ArrayList<>();//广告图片集合

    public ViewPagerAdAdapter(Context mContext, List<Integer> list) {
        this.mContext = mContext;
        this.mListImgAd = list;
        mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mListImgAd.size() == 1 ? 1 : Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //View view = View.inflate(mContext, R.layout.viewpager_item, null);
        View view = mInflater.inflate(R.layout.viewpager_item, null);
        ((ImageView) view.findViewById(R.id.imageView1)).setImageResource(mListImgAd.get(position % mListImgAd.size()));
        ((ViewPager) container).addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);

    }
}

