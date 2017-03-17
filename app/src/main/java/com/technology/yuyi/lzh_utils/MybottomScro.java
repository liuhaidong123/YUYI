package com.technology.yuyi.lzh_utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by wanyu on 2017/3/16.
 */

public class MybottomScro extends ScrollView{

    private OnScrollToBottomListener onScrollToBottom;

    public MybottomScro(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MybottomScro(Context context) {
        super(context);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX,
                                  boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if(scrollY != 0 && null != onScrollToBottom){
            onScrollToBottom.onScrollBottomListener(clampedY);
        }
    }

    public void setOnScrollToBottomLintener(OnScrollToBottomListener listener){
        onScrollToBottom = listener;
    }

    public interface OnScrollToBottomListener{
        public void onScrollBottomListener(boolean isBottom);
    }


}
