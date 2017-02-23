package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;

import java.util.List;
//医药商城gridview的适配器
/**
 * Created by wanyu on 2017/2/23.
 */

public class MS_home_GridViewAdapter extends BaseAdapter{
    private List<String> list;
    private Context context;
    public MS_home_GridViewAdapter(List<String> list,Context context){
        this.context=context;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_ms_home_gridvieitem,null);
            holder.textView= (TextView) convertView.findViewById(R.id.ms_home_gridview_item_text);
            convertView.setTag(holder);
        }

        holder= (ViewHolder) convertView.getTag();
        holder.textView.setText(list.get(position));
        return convertView;
    }
    class ViewHolder{
        TextView textView;

    }
}
