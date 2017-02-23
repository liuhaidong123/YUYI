package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.technology.yuyi.R;

/**
 * Created by liuhaidong on 2017/2/21.
 */

public class AppointmentListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContent;

    public AppointmentListViewAdapter(Context mContent) {
        this.mContent = mContent;
        mInflater=LayoutInflater.from(this.mContent);
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
        View view=mInflater.inflate(R.layout.appointment_listview_item,null);
        return view;
    }
}
