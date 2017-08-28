package com.technology.yuyi.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by wanyu on 2017/8/25.
 */
//内部只能有两个view
public class MyFrameLyout extends FrameLayout{
    Context con;
    EmptyAndNetWorkView em;
    public MyFrameLyout(Context context) {
        super(context);
        if (em==null){
            em=new EmptyAndNetWorkView();
        }
        this.con=context;
    }

    public MyFrameLyout(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (em==null){
            em=new EmptyAndNetWorkView();
        }
        this.con=context;
    }
    public void setNormal(){
        if (this.getChildCount()==2){
            this.removeViewAt(1);
        }
    }
    public void setEmptyView(String text){
        if (getChildCount()==2){
            removeViewAt(1);
        }
        this.addView(em.getEmptyView(con,text),1);
    }
    
    public void setNoNetWorkView(){
        if (getChildCount()==2){
            this.removeViewAt(1);
        }
        this.addView(em.getNetWorkView(con),1);
    }
}
