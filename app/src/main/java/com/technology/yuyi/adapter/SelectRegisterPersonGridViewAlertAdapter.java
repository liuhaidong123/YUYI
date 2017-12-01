package com.technology.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.activity.AddFamilyUserActivity;
import com.technology.yuyi.bean.UserListBean.Result;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.myview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/27.
 */

public class SelectRegisterPersonGridViewAlertAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<Result> mList = new ArrayList();
    private RoundImageView imageView;
    private TextView textView;

    public SelectRegisterPersonGridViewAlertAdapter(Context mContext, List<Result> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmList(List<Result> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size() >= 6 ? mList.size() : mList.size() + 1;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(R.layout.alert_select_register_person_gridview_item, null);
        imageView = (RoundImageView) convertView.findViewById(R.id.person_head);
        textView = (TextView) convertView.findViewById(R.id.name_alert);

        if (position < mList.size()) {
            Picasso.with(mContext).load(UrlTools.BASE + mList.get(position).getAvatar()).error(R.mipmap.usererr).into(imageView);
            textView.setText(mList.get(position).getTrueName());
        }

        if (position == mList.size()) {
            return mInflater.inflate(R.layout.alert_select_register_person_gridview_footer, null);
        }
        return convertView;
    }

}
