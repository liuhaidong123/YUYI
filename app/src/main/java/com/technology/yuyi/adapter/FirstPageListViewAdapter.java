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

public class FirstPageListViewAdapter extends BaseAdapter {
    private LayoutInflater mInfllater;
    private Context mContext;

    public FirstPageListViewAdapter(Context context) {
        this.mContext = context;
        mInfllater = LayoutInflater.from(this.mContext);
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

        View view=mInfllater.inflate(R.layout.listview_firstpage_item,null);
        return view;
    }
}
