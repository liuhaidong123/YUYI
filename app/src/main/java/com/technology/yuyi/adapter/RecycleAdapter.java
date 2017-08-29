package com.technology.yuyi.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.UserListBean.Result;
import com.technology.yuyi.lhd.utils.ImgUitls;
import com.technology.yuyi.myview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 指定泛型是你自己的holder
 */

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.MyRecycleHolder> {
    private List<Result> mList = new ArrayList<>();
    private List<Boolean> showNameList=new ArrayList<>();
    private Context mContext;
    private LayoutInflater inflater;


    /**
     * ItemClick的回调接口
     *
     * @author zhy
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
        Log.e("实例化接口", "");
    }


    public void setmList(List<Result> mList) {
        this.mList = mList;
    }

    public void setShowNameList(List<Boolean> showNameList) {
        this.showNameList = showNameList;
    }

    public RecycleAdapter(List<Result> mList, List<Boolean> showNameList, Context mContext) {
        this.mList = mList;
        this.showNameList=showNameList;
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(this.mContext);
        Log.e("初始化Adapter", "");
    }

    //返回数据多少
    @Override
    public int getItemCount() {
        Log.e("返回数据多少", "");
        return mList.size() == 0 ? 1 : mList.size() + 1;

    }

    //初始化viewholder,并且设置item布局
    @Override
    public RecycleAdapter.MyRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recycle_item, null);
        MyRecycleHolder myRecycleHolder = new MyRecycleHolder(view);
        Log.e("==", "初始化viewholder,并且设置item布局");
        return myRecycleHolder;
    }

    //设置数据
    @Override
    public void onBindViewHolder(final MyRecycleHolder holder, final int position) {
        Log.e("设置数据", "");
        if (mList.size() == 0) {
            holder.circleImageView.setImageResource(R.mipmap.add_tem);
        } else {
            if (position == mList.size()) {
                holder.circleImageView.setImageResource(R.mipmap.add_tem);
                holder.name.setVisibility(View.GONE);
                holder.snajiao.setVisibility(View.GONE);
                holder.name.setText("");
            } else {
                Picasso.with(mContext).load(UrlTools.BASE + mList.get(position).getAvatar()).error(R.mipmap.error_small).into(holder.circleImageView);
                if (showNameList.get(position)){
                    holder.name.setVisibility(View.VISIBLE);
                    holder.snajiao.setVisibility(View.VISIBLE);
                    holder.name.setText(mList.get(position).getTrueName());
                }else {
                    holder.name.setVisibility(View.GONE);
                    holder.snajiao.setVisibility(View.GONE);
                    holder.name.setText("");
                }
            }
        }

        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(holder.itemView, position);
                    Log.e("点击回调", "");
                }
            });
        }

    }


    //初始化控件
    class MyRecycleHolder extends RecyclerView.ViewHolder {

        RoundImageView circleImageView;
        TextView name;
        ImageView snajiao;
        public MyRecycleHolder(View itemView) {
            super(itemView);
            circleImageView = (RoundImageView) itemView.findViewById(R.id.img_small);
            name= (TextView) itemView.findViewById(R.id.name);
            snajiao= (ImageView) itemView.findViewById(R.id.sanjiao_img);
        }
    }

}
