package com.technology.yuyi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.bean.MyEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/21.
 */

public class LeftListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<MyEntity> mList = new ArrayList();


    public LeftListViewAdapter(Context mContext, List mList) {
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
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.left_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_left);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final MyEntity entity=mList.get(position);
        String title=entity.getTitle();
        TextView tv=viewHolder.textView;
        tv.setText(title);

        //点击的时候文字和背景变颜色
        if(entity.isSelected()){
            tv.setBackgroundColor(Color.parseColor("#ffffff"));
            tv.setTextColor(Color.parseColor("#25f368"));
        }else{
            tv.setBackgroundColor(Color.parseColor("#f2f2f2"));
            tv.setTextColor(Color.parseColor("#333333"));
        }
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(MyEntity e:mList){
                    if(e==entity){
                        e.setSelected(true);
                    }else {
                        e.setSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
        return convertView;

    }

    class ViewHolder {
        TextView textView;
    }
}
