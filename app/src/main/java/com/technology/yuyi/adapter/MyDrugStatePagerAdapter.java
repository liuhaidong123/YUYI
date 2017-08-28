package com.technology.yuyi.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wanyu on 2017/8/24.
 */

public class MyDrugStatePagerAdapter extends PagerAdapter{
    List<View> li;
    public MyDrugStatePagerAdapter( List<View> li){
        this.li=li;
    }
    @Override
    public int getCount() {
        return li==null?0:li.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(li.get(position));
        return li.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView(li.get(position));
    }
}
