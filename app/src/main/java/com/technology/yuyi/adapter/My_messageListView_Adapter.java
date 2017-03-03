package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.myview.RoundImageView;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/2.
 */

public class My_messageListView_Adapter extends BaseAdapter{
    private Context context;
    private List<Map<String,String>> list;
    public My_messageListView_Adapter(Context context,List<Map<String,String>> list){
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
//        mp.put("image",imgId[i]+"");
//        mp.put("title",title[i]);
//        mp.put("time",time[i]);
//        mp.put("info",info[i]);
//        mp.put("type",type[i]);
        View view= LayoutInflater.from(context).inflate(R.layout.activity_my_message_listview_item1,null);
        RoundImageView imageView= (RoundImageView) view.findViewById(R.id.my_message_listview_item_iamgeview);//公告图片
        TextView my_message_listview_item_msgName= (TextView) view.findViewById(R.id.my_message_listview_item_msgName);//公告名称
        TextView my_message_listview_item_time= (TextView) view.findViewById(R.id.my_message_listview_item_time);//公告时间
        TextView my_message_listview_item_msgInfo= (TextView) view.findViewById(R.id.my_message_listview_item_msgInfo);//公告内容
        TextView my_message_listview_item_more= (TextView) view.findViewById(R.id.my_message_listview_item_more);//查看更多
        RelativeLayout my_message_listview_item_layout= (RelativeLayout) view.findViewById(R.id.my_message_listview_item_layout);//查看更多所在的groupView
        String type=list.get(position).get("type");
        if ("0".equals(type)){
            my_message_listview_item_layout.setVisibility(View.GONE);
        }
        imageView.setImageResource(Integer.parseInt(list.get(position).get("image")));
        my_message_listview_item_msgName.setText(list.get(position).get("title"));
        my_message_listview_item_time.setText(list.get(position).get("time"));
        my_message_listview_item_msgInfo.setText(list.get(position).get("info"));
        return view;
    }
}
