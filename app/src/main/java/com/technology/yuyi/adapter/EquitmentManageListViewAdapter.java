package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.bean.MyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/27.
 */

public class EquitmentManageListViewAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    private List<MyEntity> list = new ArrayList<>();
    public EquitmentManageListViewAdapter(Context mContext,List<MyEntity> list) {
        this.mContext = mContext;
        this.list=list;
        mInflater=LayoutInflater.from(this.mContext);
    }

    @Override
    public int getCount() {
        return list.size();
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
        ETHolder etHolder=null;
        if (convertView==null){
            etHolder=new ETHolder();
            convertView=mInflater.inflate(R.layout.equitment_manage_listview_item,null);
            etHolder.imageView= (ImageView) convertView.findViewById(R.id.blood_img);
            etHolder.name= (TextView) convertView.findViewById(R.id.tv_blood);
            etHolder.status= (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(etHolder);
        }else {
            etHolder= (ETHolder) convertView.getTag();
        }
        etHolder.imageView.setImageResource(list.get(position).getId());
        etHolder.name.setText(list.get(position).getTitle());
        etHolder.status.setText(list.get(position).getName());

        return convertView;
    }

    class ETHolder{
        ImageView imageView;
        TextView name,status;
    }
}
