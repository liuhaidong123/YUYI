package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_MyDrugState;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/24.
 */

public class MS_DrugStateAdapter extends BaseAdapter{
    private Context context;
    private List<bean_MyDrugState.ResultBean.BoilMedicineListBean>list;
    public  MS_DrugStateAdapter(Context context,List<bean_MyDrugState.ResultBean.BoilMedicineListBean>list){
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
        View view= LayoutInflater.from(context).inflate(R.layout.ms_drugstate_listview_item,null);
//        mp.put("state","药品清单医接收");
//        mp.put("isCheck","0");//1选中，0未选中
        ImageView ms_drugstte_listv_item_Image= (ImageView) view.findViewById(R.id.ms_drugstte_listv_item_Image);
        TextView ms_drugstte_listv_item_text= (TextView) view.findViewById(R.id.ms_drugstte_listv_item_text);
        TextView ms_drugstte_listv_item_TextMsg= (TextView) view.findViewById(R.id.ms_drugstte_listv_item_TextMsg);
        TextView ms_drugstte_listv_item_Texttime= (TextView) view.findViewById(R.id.ms_drugstte_listv_item_Texttime);

        ms_drugstte_listv_item_TextMsg.setText(list.get(position).getStateText());
        ms_drugstte_listv_item_Texttime.setText(list.get(position).getCreateTimeString());
        if (position!=list.size()-1){
            ms_drugstte_listv_item_Image.setImageResource(R.mipmap.unnow);
        }
        else {
            ms_drugstte_listv_item_Image.setImageResource(R.mipmap.now);
        }


        if (position==list.size()-1){
            ms_drugstte_listv_item_text.setVisibility(View.GONE);
        }
        if (position==list.size()-2){
            ms_drugstte_listv_item_text.setBackgroundColor(context.getResources().getColor(R.color.color_ms_home_disc));
        }
        else {
            ms_drugstte_listv_item_text.setBackgroundColor(context.getResources().getColor(R.color.unselect));
        }
        return view;
    }
}
