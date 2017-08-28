package com.technology.yuyi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.FirstPageInformationTwoData;
import com.technology.yuyi.bean.UpdatedFirstPageTwoDataBean.Rows;
import com.technology.yuyi.lhd.utils.ImgUitls;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/21.
 */

public class InformationListViewAdapter extends BaseAdapter {
    private LayoutInflater mInfllater;
    private Context mContext;
    private List<Rows> list = new ArrayList<>();

    public InformationListViewAdapter(Context mContext, List<Rows> list) {
        this.mContext = mContext;
        this.list = list;
        mInfllater = LayoutInflater.from(this.mContext);
    }

    public void setList(List<Rows> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=mInfllater.inflate(R.layout.information_listview_item, null);
            viewHolder=new InformationListViewAdapter.ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.infor_img_mess);
            viewHolder.hospital_tv= (TextView) convertView.findViewById(R.id.infor_tv_title);
            viewHolder.mBg_rl= (RelativeLayout) convertView.findViewById(R.id.bg_rl_information);
           // viewHolder.hospital_message_tv= (TextView) convertView.findViewById(R.id.infor_tv_mess);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Picasso.with(mContext).load(UrlTools.BASE+list.get(position).getPicture()).into(viewHolder.imageView);
        viewHolder.hospital_tv.setText(list.get(position).getTitle());
        viewHolder.mBg_rl.getBackground().setAlpha(125);
       // viewHolder.hospital_message_tv.setText(list.get(position).getArticleText());
        return convertView;

    }

    class ViewHolder {
        ImageView imageView;
        TextView hospital_tv;
        TextView hospital_message_tv;
        RelativeLayout mBg_rl;
    }

}
