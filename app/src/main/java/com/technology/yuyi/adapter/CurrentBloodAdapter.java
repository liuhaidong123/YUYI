package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.technology.yuyi.R;

/**
 * Created by liuhaidong on 2017/2/22.
 */

public class CurrentBloodAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContent;

    public CurrentBloodAdapter(Context mContent) {
        this.mContent = mContent;
        mInflater=LayoutInflater.from(this.mContent);
    }

    @Override
    public int getCount() {
        return 5;
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
        convertView=mInflater.inflate(R.layout.current_tem_listview_item,null);
        return convertView;
    }
}
