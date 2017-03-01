package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyi.R;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/2/28.
 */

public class My_shoppingCart_Adapter extends BaseAdapter{
    private Context context;
    private List<Map<String,String>>list;
    public My_shoppingCart_Adapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.activity_my_shoppingcart_listview_item,null);
            hodler=new ViewHodler();
            hodler.my_shoppingcart_listitem_image= (ImageView) convertView.findViewById(R.id.my_shoppingcart_listitem_image);
            convertView.setTag(hodler);
        }
        hodler= (ViewHodler) convertView.getTag();
        hodler.my_shoppingcart_listitem_image.setImageResource(Integer.parseInt(list.get(position).get("image")));
        return convertView;
    }

    class ViewHodler{
        ImageView my_shoppingcart_listitem_image;
//        TextView
    }
}
