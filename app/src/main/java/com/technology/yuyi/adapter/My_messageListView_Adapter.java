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
import com.technology.yuyi.bean.bean_MyMessage;
import com.technology.yuyi.myview.RoundImageView;

import java.util.List;
import java.util.Map;

/**
 * Created by wanyu on 2017/3/2.
 */

public class My_messageListView_Adapter extends BaseAdapter{
    private Context context;
    private List<bean_MyMessage.ResultBean>listBean;
    public My_messageListView_Adapter(Context context,List<bean_MyMessage.ResultBean>listBean){
        this.context=context;
        this.listBean=listBean;
    }
    @Override
    public int getCount() {
        return listBean==null?0:listBean.size();
    }

    @Override
    public Object getItem(int position) {
        return listBean.get(position);
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
        int type=listBean.get(position).getMsgType();
        String title="";
        int resId;//图片id
        switch (type){
            case 1://公告
                title="宇医公告";
                resId=R.mipmap.msg1;
                break;
            case 2://挂号信息
                title="挂号通知";
                resId=R.mipmap.msg3;
                break;
            default:
                title="宇医消息";
                resId=R.mipmap.msg2;
                break;
        }
        my_message_listview_item_layout.setVisibility(View.GONE);
        imageView.setImageResource(resId);
        my_message_listview_item_msgName.setText(title);
        my_message_listview_item_time.setText(listBean.get(position).getCreateTimeString());
        my_message_listview_item_msgInfo.setText(listBean.get(position).getContent());
        return view;
    }
}
