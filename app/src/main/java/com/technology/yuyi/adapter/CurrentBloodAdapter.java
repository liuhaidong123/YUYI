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

public class CurrentBloodAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContent;
    private List<Result> list = new ArrayList<>();
    public CurrentBloodAdapter(Context mContent,List<Result> list) {
        this.mContent = mContent;
        this.list = list;
        mInflater=LayoutInflater.from(this.mContent);
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
        HolderCurrBlood holderTem = null;
        if (convertView == null) {
            holderTem = new HolderCurrBlood();
            convertView = mInflater.inflate(R.layout.current_tem_listview_item, null);
            holderTem.img = (RoundImageView) convertView.findViewById(R.id.head_img);
            holderTem.textView = (TextView) convertView.findViewById(R.id.tem_name);
            convertView.setTag(holderTem);
        } else {
            holderTem = (HolderCurrBlood) convertView.getTag();
        }
        holderTem.textView.setText(list.get(position).getTrueName());
        Picasso.with(mContent).load(UrlTools.BASE + list.get(position).getAvatar()).error(R.mipmap.usererr).into(holderTem.img);
        return convertView;
    }

    class HolderCurrBlood{
        RoundImageView img;
        TextView textView;
    }
}
