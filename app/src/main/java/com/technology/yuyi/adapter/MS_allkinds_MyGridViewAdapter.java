package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_MS_allkinds_alldrugs;
import com.technology.yuyi.lzh_utils.Ip;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/2/27.
 */

public class MS_allkinds_MyGridViewAdapter extends BaseAdapter{
    private Context context;
    private List<bean_MS_allkinds_alldrugs.RowsBean> list;
    public MS_allkinds_MyGridViewAdapter(Context context,List<bean_MS_allkinds_alldrugs.RowsBean> list){
        this.context=context;
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
          convertView= LayoutInflater.from(context).inflate(R.layout.activity_ms_allkinds_gridview_itme,null);
            hodler=new ViewHodler();
            hodler.ms_allkinds_gridview_item_image= (ImageView) convertView.findViewById(R.id.ms_allkinds_gridview_item_image);
            hodler.ms_allkinds_gridview_item_name= (TextView) convertView.findViewById(R.id.ms_allkinds_gridview_item_name);
            hodler.ms_home_gridview_dailyMitem_price= (TextView) convertView.findViewById(R.id.ms_home_gridview_dailyMitem_price);
            convertView.setTag(hodler);
        }
        hodler= (ViewHodler) convertView.getTag();
        Picasso.with(context).load(Ip.imagePth_F+list.get(position).getPicture()).into(hodler.ms_allkinds_gridview_item_image);
        hodler.ms_allkinds_gridview_item_name.setText(list.get(position).getDrugsName());
        StringBuilder builder=new StringBuilder();
        builder.append("¥");
        builder.append(list.get(position).getPrice());
        hodler.ms_home_gridview_dailyMitem_price.setText(builder.toString());
        return convertView;
    }
     class ViewHodler{
        ImageView ms_allkinds_gridview_item_image;
         TextView ms_allkinds_gridview_item_name,ms_home_gridview_dailyMitem_price;
    }
}
