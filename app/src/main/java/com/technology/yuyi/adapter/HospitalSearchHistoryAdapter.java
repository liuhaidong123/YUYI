package com.technology.yuyi.adapter;

import android.content.Context;
import android.support.annotation.ArrayRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/3/20.
 */

public class HospitalSearchHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private LayoutInflater mInflater;

    public HospitalSearchHistoryAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        this.mInflater = LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
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
        MyHolder myHolder = null;
        if (convertView == null) {
            myHolder = new MyHolder();
            convertView = mInflater.inflate(R.layout.search_history_listview_item, null);
            myHolder.name= (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(myHolder);
        } else {
            myHolder= (MyHolder) convertView.getTag();
        }
        myHolder.name.setText(mList.get(position));
        return convertView;
    }

    class MyHolder {
        TextView name;
    }
}
