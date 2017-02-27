package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by wanyu on 2017/2/27.
 */

public class MS_allkindssort_Adapter extends BaseAdapter{
    private List<String> list;
    private Context context;
    public MS_allkindssort_Adapter(List<String> list,Context context){
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
                convertView= LayoutInflater.from(context).inflate(R.layout.activity_ms_allkinds_sort_item,null);
                hodler=new ViewHodler();
                hodler.textView= (TextView) convertView.findViewById(R.id.ms_allkindssort_item_textv);
                convertView.setTag(hodler);
        }
        hodler= (ViewHodler) convertView.getTag();
        hodler.textView.setText(list.get(position));
        return convertView;
    }
    class ViewHodler{
        TextView textView;
    }
}
