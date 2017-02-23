package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;

import java.util.ArrayList;

/**
 * Created by liuhaidong on 2017/2/21.
 */

public class RightListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<String> mList = new ArrayList();

    public RightListViewAdapter(Context mContext, ArrayList mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(this.mContext);
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

        RightListViewAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.right_listview_item, null);
            viewHolder = new RightListViewAdapter.ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_right);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RightListViewAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(mList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
