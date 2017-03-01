package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.myview.RoundImageView;

/**
 * Created by liuhaidong on 2017/2/28.
 */

public class FamilyManageListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public FamilyManageListViewAdapter(Context mContext) {
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
        convertView=mInflater.inflate(R.layout.family_manage_listview_item,null);
        return convertView;
    }

    class ViewHodler{
        RoundImageView img_head;
        TextView name;
    }
}
