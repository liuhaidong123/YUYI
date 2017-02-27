package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.technology.yuyi.R;

import java.util.ArrayList;

/**
 * Created by liuhaidong on 2017/2/27.
 */

public class SelectRegisterPersonGridViewAlertAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList mList=new ArrayList();
    public SelectRegisterPersonGridViewAlertAdapter(Context mContext,ArrayList mList) {
        this.mContext = mContext;
        this.mList=mList;
        mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mList.size()+1;
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
        if (position==mList.size()){
            convertView=mInflater.inflate(R.layout.alert_select_register_person_gridview_footer,null);
        }else {
            convertView=mInflater.inflate(R.layout.alert_select_register_person_gridview_item,null);
        }

        return convertView;
    }
}
