package com.technology.yuyi.myview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyi.R;

/**
 * Created by wanyu on 2017/8/25.
 */

public class EmptyAndNetWorkView {
    View vi;
    TextView empty_text;
    ImageView empty_image;
    public EmptyAndNetWorkView(){

    }
    public View getEmptyView(Context con,String emptyText){
        if (vi==null){
            vi= LayoutInflater.from(con).inflate(R.layout.emview,null);
            empty_text= (TextView) vi.findViewById(R.id.empty_text);
            empty_image= (ImageView) vi.findViewById(R.id.empty_image);
        }
        empty_text.setText(emptyText);
        empty_image.setImageResource(R.mipmap.nothings);
        return vi;
    }

    public View getNetWorkView(Context con){
        if (vi==null){
            vi= LayoutInflater.from(con).inflate(R.layout.emview,null);
            empty_text= (TextView) vi.findViewById(R.id.empty_text);
            empty_image= (ImageView) vi.findViewById(R.id.empty_image);
        }
        empty_text.setText("没有网络连接");
        empty_image.setImageResource(R.mipmap.nonetworks);
        return vi;
    }
}
