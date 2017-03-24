package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.UserListBean.Result;
import com.technology.yuyi.myview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/22.
 */

public class CurrentTemListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Result> list = new ArrayList<>();
    public CurrentTemListViewAdapter(Context mContext, List<Result> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater=LayoutInflater.from(this.mContext);
    }
    public void setList(List<Result> list) {
        this.list = list;
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

        HolderCurrent holderTem=null;
        if (convertView==null){
            holderTem=new HolderCurrent();
            convertView=mInflater.inflate(R.layout.current_tem_listview_item,null);
            holderTem.img= (RoundImageView) convertView.findViewById(R.id.head_img);
            holderTem.textView= (TextView) convertView.findViewById(R.id.tem_name);
            convertView.setTag(holderTem);
        }else {
            holderTem= (HolderCurrent) convertView.getTag();
        }
        holderTem.textView.setText(list.get(position).getTrueName());
        Picasso.with(mContext).load(UrlTools.BASE+list.get(position).getAvatar()).error(R.mipmap.error_small).into(holderTem.img);
        return convertView;

    }

    class HolderCurrent{
        RoundImageView img;
        TextView textView;
    }
}
