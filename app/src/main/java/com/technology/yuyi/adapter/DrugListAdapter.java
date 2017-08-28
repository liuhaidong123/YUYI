package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.bean.BeanDrugStates;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by wanyu on 2017/8/24.
 */

public class DrugListAdapter extends BaseAdapter{
    Context con;
    List<BeanDrugStates.ResultBean> lt;
    public DrugListAdapter(Context con,List<BeanDrugStates.ResultBean>lt){
        this.con=con;
        this.lt=lt;
    }
    @Override
    public int getCount() {
        return lt==null?0:lt.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return lt.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler;
        if (convertView==null){
            convertView= LayoutInflater.from(con).inflate(R.layout.listitem,null);
            hodler=new ViewHodler();
            hodler.textview= (TextView) convertView.findViewById(R.id.ttx);
            convertView.setTag(hodler);
        }
        else {
            hodler= (ViewHodler) convertView.getTag();
        }
        if (position!=lt.size()){//不是最后一个
            try{
                SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date=format.parse(lt.get(position).getCreateTimeString());
                Calendar c=Calendar.getInstance();
                c.setTime(date);
                hodler.textview.setText(c.get(Calendar.YEAR)+""+(c.get(Calendar.MONTH)+1)+""+c.get(Calendar.DAY_OF_MONTH)+"-"+lt.get(position).getTitle());
            }
            catch (Exception e){
                hodler.textview.setText(lt.get(position).getCreateTimeString()+"-"+lt.get(position).getTitle());
                e.printStackTrace();
            }
        }
    else{//最后一个
            hodler.textview.setText("取消");
            hodler.textview.setGravity(Gravity.CENTER);
        }
        return convertView;
    }
    class ViewHodler{
        TextView textview;
    }
}
