package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_FamilyUserEle;
import com.technology.yuyi.bean.bean_MedicalRecordList;

import java.util.List;

/**
 * Created by wanyu on 2017/3/29.
 */

public class FamilyUserEleListAdapter extends BaseAdapter{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<bean_FamilyUserEle.ResultBean> list;
    public FamilyUserEleListAdapter(Context mContext,List<bean_FamilyUserEle.ResultBean> list) {
        this.mContext = mContext;
        mInflater=LayoutInflater.from(this.mContext);
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
        convertView=mInflater.inflate(R.layout.elec_listview_item,null);
        TextView tv_date= (TextView) convertView.findViewById(R.id.tv_date);
        tv_date.setText(list.get(position).getCreateTimeString());
        return convertView;
    }
}
