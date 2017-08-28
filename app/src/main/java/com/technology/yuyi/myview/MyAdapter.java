package com.technology.yuyi.myview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;

/**
 * Created by wanyu on 2017/8/17.
 */

public class MyAdapter extends BaseAdapter{
    Context context;
    public MyAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return 7;
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
        convertView= LayoutInflater.from(context).inflate(R.layout.text,null);
        TextView texta= (TextView) convertView.findViewById(R.id.texta);
        texta.setText("测试数据："+position);
        return convertView;
    }
}
