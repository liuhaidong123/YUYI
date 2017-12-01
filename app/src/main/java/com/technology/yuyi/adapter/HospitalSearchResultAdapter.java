package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.bean.SearchHospital.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/3/1.
 */

public class HospitalSearchResultAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Result> mList = new ArrayList();

    public HospitalSearchResultAdapter(Context mContext, List<Result> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflater = LayoutInflater.from(this.mContext);
    }

    public void setmList(List<Result> mList) {
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
        ViewHodler viewHodler = null;
        if (convertView == null) {
            viewHodler = new ViewHodler();
            convertView = mInflater.inflate(R.layout.search_history_listview_item, null);
            viewHodler.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(viewHodler);
        } else {
            viewHodler = (ViewHodler) convertView.getTag();
        }

        viewHodler.name.setText(mList.get(position).getHospitalName());

        return convertView;
    }

    class ViewHodler {
        TextView name;
    }
}
