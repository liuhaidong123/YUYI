package com.technology.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.activity.InformationDetailsActivity;
import com.technology.yuyi.bean.AdBean.Rows;
import com.technology.yuyi.fragment.FirstPageFragment;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/20.
 */

public class ViewPagerAdAdapter extends PagerAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<Rows> mListImgAd = new ArrayList<>();//广告图片集合

    public ViewPagerAdAdapter(Context mContext, List<Rows> list) {
        this.mContext = mContext;
        this.mListImgAd = list;
        mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmListImgAd(List<Rows> mListImgAd) {
        this.mListImgAd = mListImgAd;
    }

    @Override
    public int getCount() {
        return mListImgAd.size() == 1 ? 1 : mListImgAd.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }


    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mInflater.inflate(R.layout.viewpager_item, null);
        ImageView img = ((ImageView) view.findViewById(R.id.imageView1));
        Picasso.with(mContext).load(UrlTools.BASE + mListImgAd.get(position).getPicture()).error(R.mipmap.error_big).into(img);
        //点击轮播图片跳转
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListImgAd != null && mListImgAd.size() > 0) {
                    Intent intent = new Intent(mContext, InformationDetailsActivity.class);
                    intent.putExtra("type", "ad");
                    intent.putExtra("id", mListImgAd.get(position).getId());
                    mContext.startActivity(intent);
                }
            }
        });
        ((ViewPager) container).addView(view);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);

    }
}

