package com.technology.yuyi.lzh_utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.technology.yuyi.R;

/**
 * Created by wanyu on 2017/3/14.
 */

public class MyExpanListview extends ExpandableListView{
    private Context cont;
    private int width;
    public MyExpanListview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.cont=context;
        WindowManager manager= (WindowManager) cont.getSystemService(Context.WINDOW_SERVICE);
        width=manager.getDefaultDisplay().getWidth();
    }

    public MyExpanListview(Context context) {
        super(context);

    }


    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
