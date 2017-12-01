package com.technology.yuyi.adapter;

import android.content.Context;
import android.util.Log;
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
 * Created by liuhaidong on 2017/2/21.
 */

public class AppointmentListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContent;
    private List<FirstPageInformationTwoData> list=new ArrayList<>();
    private int a=0;
    public AppointmentListViewAdapter(Context mContent,List<FirstPageInformationTwoData> list) {
        this.mContent = mContent;
        this.list=list;
        mInflater=LayoutInflater.from(this.mContent);
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
        a++;
        Log.e("医院a=",""+a);
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.appointment_listview_item,null);
            viewHolder.img= (ImageView) convertView.findViewById(R.id.yu_img_mess);
            viewHolder.name_tv= (TextView) convertView.findViewById(R.id.yu_tv_title);
            viewHolder.grade_tv=(TextView) convertView.findViewById(R.id.grade_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContent).load(UrlTools.BASE+list.get(position).getPicture()).error(R.mipmap.errorpicture).into(viewHolder.img);
        viewHolder.name_tv.setText(list.get(position).getHospitalName());
        viewHolder.grade_tv.setText(list.get(position).getGrade());
        return convertView;
    }

    class ViewHolder{
        ImageView img;
        TextView name_tv;
        TextView grade_tv;
    }
}
