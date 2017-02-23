package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.technology.yuyi.R;

/**
 * Created by liuhaidong on 2017/2/20.
 */

public class UseDrugGridViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public UseDrugGridViewAdapter(Context context) {
        this.mContext = context;
        mInflater=LayoutInflater.from(this.mContext);

    }

    @Override
    public int getCount() {
        return 7;
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
        View view=mInflater.inflate(R.layout.gridview_first_item,null);
        return view;
    }
}
