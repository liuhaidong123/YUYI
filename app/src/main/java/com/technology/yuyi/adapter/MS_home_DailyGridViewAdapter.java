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
import com.technology.yuyi.lzh_utils.Ip;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/2/23.
 */

public class MS_home_DailyGridViewAdapter extends BaseAdapter{
    private List<Map<String,String>>list;
    private Context context;
    public  MS_home_DailyGridViewAdapter(List<Map<String,String>>list,Context context){
        this.list=list;
        this.context=context;

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
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_ms_home_gridviewdailly_item,null);
            hodler=new ViewHodler();
            hodler.imageView= (ImageView) convertView.findViewById(R.id.ms_home_gridview_dailyMitem_image);
            hodler.textViewName= (TextView) convertView.findViewById(R.id.ms_home_gridview_dailyMitem_name);
            hodler.textViewPrice= (TextView) convertView.findViewById(R.id.ms_home_gridview_dailyMitem_price);
            convertView.setTag(hodler);

        }

//        m.put("childname",listDrugs.get(j).getDrugsName());
//                m.put("childid",listDrugs.get(j).getPrice()+"");
//                m.put("childurl",listDrugs.get(j).getPicture());
//          m.put("childprice",listDrugs.get(j).getPrice()+"");
        hodler= (ViewHodler) convertView.getTag();
        Picasso.with(context).load(Ip.url+list.get(position).get("childurl")).into( hodler.imageView);
        hodler.textViewName.setText(list.get(position).get("childname"));
        hodler.textViewPrice.setText("￥"+list.get(position).get("childprice"));
        return convertView;
    }
    class ViewHodler{
        TextView textViewName,textViewPrice;
        ImageView imageView;
    }
}
