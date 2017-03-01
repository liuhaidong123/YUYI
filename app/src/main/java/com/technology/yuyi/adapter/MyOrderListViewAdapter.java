package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.technology.yuyi.R;

/**
 * Created by liuhaidong on 2017/3/1.
 */

public class MyOrderListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public MyOrderListViewAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=mInflater.inflate(R.layout.my_order_listview_item,null);
        return convertView;
    }
}
