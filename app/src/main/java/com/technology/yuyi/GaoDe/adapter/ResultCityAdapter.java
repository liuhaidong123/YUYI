package com.technology.yuyi.GaoDe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.bean.City;

import java.util.ArrayList;


/**
 * Created by liuhaidong on 2017/2/16.
 */

public class ResultCityAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<City> mResultList = new ArrayList<City>();
    private Context mContext;

    public ResultCityAdapter(ArrayList<City> mResultList, Context mContext) {
        this.mResultList = mResultList;
        this.mContext = mContext;
        mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return mResultList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.gs_search_result_listview_item,null);
            viewHolder.mTvName= (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHolder);
        }else {
           viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.mTvName.setText(mResultList.get(position).getName());
        return convertView;
    }

    class ViewHolder{
        TextView mTvName;
    }
}
