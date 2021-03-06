package com.technology.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.activity.SelectDoctorActivity;
import com.technology.yuyi.activity.StartActivity;
import com.technology.yuyi.bean.HospitalOutPatient;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/21.
 */

public class RightListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<HospitalOutPatient> mList = new ArrayList();

    public RightListViewAdapter(Context mContext, List<HospitalOutPatient> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmList(List<HospitalOutPatient> mList) {
        this.mList = mList;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        RightListViewAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.right_listview_item, null);
            viewHolder = new RightListViewAdapter.ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_right);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (RightListViewAdapter.ViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(mList.get(position).getClinicName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, SelectDoctorActivity.class);
                intent.putExtra("name",mList.get(position).getClinicName());
                intent.putExtra("cid",mList.get(position).getId());
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
