package com.technology.yuyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.activity.AddFamilyUserActivity;
import com.technology.yuyi.bean.SelectDoctor.DatenumberList;
import com.technology.yuyi.bean.UserListBean.Result;
import com.technology.yuyi.bean.UserListBean.Root;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.RoundImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaidong on 2017/2/27.
 */

public class SelectDoctorListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<DatenumberList> mListDoctor = new ArrayList<>();
    private boolean flag;

    public SelectDoctorListViewAdapter(final Context mContext, List<DatenumberList> mListDoctor, boolean flag) {
        this.mContext = mContext;
        this.mListDoctor = mListDoctor;
        this.flag = flag;
        mInflater = LayoutInflater.from(this.mContext);

    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setmListDoctor(List<DatenumberList> mListDoctor) {
        this.mListDoctor = mListDoctor;
    }

    @Override
    public int getCount() {
        return mListDoctor.size();
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
            convertView = mInflater.inflate(R.layout.select_doctor_listview_item, null);
            viewHolder.register_rl = (RelativeLayout) convertView.findViewById(R.id.right_rl);
           viewHolder.imageView = (ImageView) convertView.findViewById(R.id.doctor_img_head);
            viewHolder.doc_name = (TextView) convertView.findViewById(R.id.doctor_name_tv);
            viewHolder.doc_job = (TextView) convertView.findViewById(R.id.doctor_job_tv);
            viewHolder.num_tv = (TextView) convertView.findViewById(R.id.num_hao);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE + mListDoctor.get(position).getAvatar()).error(R.mipmap.error_small).into(viewHolder.imageView);
        viewHolder.doc_name.setText(mListDoctor.get(position).getTrueName());
        viewHolder.doc_job.setText(mListDoctor.get(position).getTitle());
        //上午
        if (flag) {
            Log.e("重新设置数据","上午");
            viewHolder.num_tv.setText(""+mListDoctor.get(position).getBeforNum());
        }
        //下午
        if (!flag) {
            Log.e("重新设置数据","下午");
            viewHolder.num_tv.setText("" + mListDoctor.get(position).getAfterNum());
        }

        return convertView;
    }


    class ViewHolder {
        ImageView imageView;
        TextView doc_name;
        TextView doc_job;
        TextView num_tv;
        RelativeLayout register_rl;
    }
}
