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
import com.technology.yuyi.bean.FirstPageInformationTwoData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/20.
 */

public class FirstPageListViewAdapter extends BaseAdapter {
    private LayoutInflater mInfllater;
    private Context mContext;
    private List<FirstPageInformationTwoData> list = new ArrayList<>();

    public FirstPageListViewAdapter(Context context, List<FirstPageInformationTwoData> list) {
        this.mContext = context;
        this.list=list;
        mInfllater = LayoutInflater.from(this.mContext);
    }

    public void setList(List<FirstPageInformationTwoData> list) {
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

        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=mInfllater.inflate(R.layout.listview_firstpage_item, null);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.img_mess);
            viewHolder.hospital_tv= (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.hospital_message_tv= (TextView) convertView.findViewById(R.id.tv_mess);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(UrlTools.BASE+list.get(position).getPicture()).error(R.mipmap.error_small).into(viewHolder.imageView);
        viewHolder.hospital_tv.setText(list.get(position).getHospitalName());
        viewHolder.hospital_message_tv.setText(list.get(position).getIntroduction());
        return convertView;
    }
    class ViewHolder{
        ImageView imageView;
        TextView hospital_tv;
        TextView hospital_message_tv;

    }
}
