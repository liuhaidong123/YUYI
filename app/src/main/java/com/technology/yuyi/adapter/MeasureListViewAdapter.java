package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyi.R;

/**
 * Created by liuhaidong on 2017/2/22.
 */

public class MeasureListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public MeasureListViewAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return 2;
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
        convertView=mInflater.inflate(R.layout.measure_listview_item,null);

        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView tv_name;
        TextView tv_mess;
    }
}
