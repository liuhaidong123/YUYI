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
import com.technology.yuyi.lhd.utils.KmUtils;
import com.technology.yuyi.lzh_utils.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/23.
 */

public class AskListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<FirstPageInformationTwoData> mList = new ArrayList<>();

    public AskListViewAdapter(Context mContext, List<FirstPageInformationTwoData> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmList(List<FirstPageInformationTwoData> mList) {
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
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.ask_hospital_listview_item, null);
            viewHolder.img = (ImageView) convertView.findViewById(R.id.hospital_img);
            viewHolder.title_tv = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tel_tv = (TextView) convertView.findViewById(R.id.tv_tel_num);
            viewHolder.address_tv = (TextView) convertView.findViewById(R.id.tv_address);
            viewHolder.km_tv = (TextView) convertView.findViewById(R.id.tv_km);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(UrlTools.BASE + mList.get(position).getPicture()).error(R.mipmap.error_small).into(viewHolder.img);
        viewHolder.title_tv.setText(mList.get(position).getHospitalName());
        viewHolder.tel_tv.setText(mList.get(position).getTell());
        viewHolder.address_tv.setText("地址:"+mList.get(position).getAddress());

        viewHolder.km_tv.setText( KmUtils.getDistance(user.Longitude,user.Latitude,mList.get(position).getLng(),mList.get(position).getLat())+"km");


        return convertView;
    }

    class ViewHolder {
        ImageView img;
        TextView title_tv;
        TextView tel_tv;
        TextView address_tv;
        TextView km_tv;
    }
}
