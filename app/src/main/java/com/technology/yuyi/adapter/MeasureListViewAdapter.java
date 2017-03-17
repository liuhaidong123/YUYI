package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.MyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/22.
 */

public class MeasureListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<MyEntity> list = new ArrayList<>();

    public MeasureListViewAdapter(Context mContext,List<MyEntity> list) {
        this.mContext = mContext;
        this.list=list;
        mInflater = LayoutInflater.from(this.mContext);
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
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView = mInflater.inflate(R.layout.measure_listview_item, null);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.blood_img);
            viewHolder.tv_name= (TextView) convertView.findViewById(R.id.tv_blood);
            viewHolder.tv_mess= (TextView) convertView.findViewById(R.id.tv_mess);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(list.get(position).getId()).into(viewHolder.imageView);
        viewHolder.tv_name.setText(list.get(position).getTitle());
        viewHolder.tv_mess.setText(list.get(position).getName());

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView tv_name;
        TextView tv_mess;
    }
}
