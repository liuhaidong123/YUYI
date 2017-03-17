package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_MS_drguInfo;

import java.util.List;

/**
 * Created by wanyu on 2017/3/17.
 */

public class Ms_druginfo_popAdapter extends BaseAdapter{
    private List<bean_MS_drguInfo.SpecificationsdListBean> listSpi;
    private Context context;
    private int selectId=0;
    public Ms_druginfo_popAdapter(List<bean_MS_drguInfo.SpecificationsdListBean> listSpi,Context context){
        this.listSpi=listSpi;
        this.context=context;
    }
    @Override
    public int getCount() {
        return listSpi==null?0:listSpi.size();
    }

    @Override
    public Object getItem(int position) {
        return listSpi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Hodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.ms_druginfo_gridview_item,null);
            hodler=new Hodler();
            hodler.ms_druginfo_pop_r1= (TextView) convertView.findViewById(R.id.ms_druginfo_pop_item);
            convertView.setTag(hodler);
        }
        hodler= (Hodler) convertView.getTag();
        hodler.ms_druginfo_pop_r1.setText(listSpi.get(position).getSpecificationsdName());
        if (position==selectId){
            hodler.ms_druginfo_pop_r1.setSelected(true);
        }
        else {
            hodler.ms_druginfo_pop_r1.setSelected(false);
        }
        return convertView;
    }

    class Hodler{
            TextView ms_druginfo_pop_r1;
    }

    public void setSelectId(int ids){
        this.selectId=ids;
        notifyDataSetChanged();
    }
}
