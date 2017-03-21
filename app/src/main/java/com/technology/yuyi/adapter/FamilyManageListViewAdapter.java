package com.technology.yuyi.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_ListFamilyUser;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.myview.RoundImageView;

import java.util.List;

/**
 * Created by liuhaidong on 2017/2/28.
 */

public class FamilyManageListViewAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<bean_ListFamilyUser.ResultBean> list;
    public FamilyManageListViewAdapter(Context mContext,List<bean_ListFamilyUser.ResultBean> list) {
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
        ViewHodler hodler;
        if (convertView==null){
            convertView=mInflater.inflate(R.layout.family_manage_listview_item,null);
            hodler=new ViewHodler();
            hodler.img_head= (RoundImageView) convertView.findViewById(R.id.family_head);
            hodler.name= (TextView) convertView.findViewById(R.id.family_name);
            convertView.setTag(hodler);
        }
            hodler= (ViewHodler) convertView.getTag();
        Picasso.with(mContext).load(Uri.parse(Ip.imagePth+list.get(position).getAvatar())).error(R.mipmap.logo).into(hodler.img_head);
        hodler.name.setText(list.get(position).getTrueName());
        return convertView;
    }

    class ViewHodler{
        RoundImageView img_head;
        TextView name;
    }
}
