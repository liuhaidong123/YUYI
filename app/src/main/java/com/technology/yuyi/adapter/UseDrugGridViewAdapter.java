package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.FirstPageDrugSixData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/20.
 */

public class UseDrugGridViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<FirstPageDrugSixData> mList = new ArrayList<>();

    public UseDrugGridViewAdapter(Context context, List<FirstPageDrugSixData> mList) {
        this.mContext = context;
        this.mList = mList;
        mInflater = LayoutInflater.from(this.mContext);

    }

    public void setmList(List<FirstPageDrugSixData> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gridview_first_item, null);
            viewHolder = new ViewHolder();
            viewHolder.drugname = (TextView) convertView.findViewById(R.id.tv_item_drugMess);
            viewHolder.drugprice = (TextView) convertView.findViewById(R.id.tv_item_price);
            viewHolder.drugimg = (ImageView) convertView.findViewById(R.id.img_item_drug);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
          viewHolder.drugname.setText(mList.get(position).getDrugsName());
          viewHolder.drugprice.setText("¥"+mList.get(position).getPrice());
         Picasso.with(mContext).load(UrlTools.BASE+mList.get(position).getPicture()) .into(viewHolder.drugimg);
        return convertView;
    }

    class ViewHolder {
        TextView drugname;
        TextView drugprice;
        ImageView drugimg;
    }
}
