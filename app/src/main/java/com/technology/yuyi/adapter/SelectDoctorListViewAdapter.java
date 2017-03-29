package com.technology.yuyi.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.SelectDoctor.DatenumberList;
import com.technology.yuyi.myview.RoundImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuhaidong on 2017/2/27.
 */

public class SelectDoctorListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<DatenumberList> mListDoctor = new ArrayList<>();
    private int flag;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private View mAlertView;
    private GridView mAlertGridView;
    private SelectRegisterPersonGridViewAlertAdapter mAdapter;
    private TextView mSure;//确定
    private TextView mCancel;//取消
    private ArrayList mList = new ArrayList();
    //确定弹框
    private AlertDialog.Builder mSureBuilder;
    private AlertDialog mSureAlertDialog;
    private View mSureAlertView;
    private TextView mPrompt;//去完善
    private TextView mPrompt_Cancel;//取消

    public SelectDoctorListViewAdapter(Context mContext, List<DatenumberList> mListDoctor,int flag) {
        this.mContext = mContext;
        this.mListDoctor = mListDoctor;
        this.flag=flag;
        mInflater = LayoutInflater.from(this.mContext);
        mBuilder = new AlertDialog.Builder(this.mContext);
        //alert弹框
        mAlertDialog = mBuilder.create();
        mAlertView = mInflater.inflate(R.layout.alert_select_register_person, null);
        mAlertDialog.setView(mAlertView);
        //弹框设置适配器
        mList.add(1);
        mList.add(2);
        mList.add(3);
        mList.add(4);
        mList.add(5);
        mAlertGridView = (GridView) mAlertView.findViewById(R.id.be_select_gridview);
        mAdapter = new SelectRegisterPersonGridViewAlertAdapter(this.mContext, mList);
        mAlertGridView.setAdapter(mAdapter);
        mAlertGridView.setSelector(R.drawable.nopress_bgcolor);
        //取消，确定
        mSure = (TextView) mAlertView.findViewById(R.id.alert_sure);
        mSure.setOnClickListener(this);
        mCancel = (TextView) mAlertView.findViewById(R.id.alert_cancel);
        mCancel.setOnClickListener(this);
        //确定后的弹框
        mSureBuilder = new AlertDialog.Builder(this.mContext);
        mSureAlertDialog = mSureBuilder.create();
        mSureAlertView = mInflater.inflate(R.layout.alert_sure, null);
        mSureAlertDialog.setView(mSureAlertView);
        //去完善、取消
        mPrompt = (TextView) mSureAlertView.findViewById(R.id.alert_sure_prompt);
        mPrompt.setOnClickListener(this);
        mPrompt_Cancel = (TextView) mSureAlertView.findViewById(R.id.alert_sure_cancel);
        mPrompt_Cancel.setOnClickListener(this);
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setmListDoctor(List<DatenumberList> mListDoctor) {
        this.mListDoctor = mListDoctor;
    }

    @Override
    public int getCount() {
        return mListDoctor.size();
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.select_doctor_listview_item, null);
            viewHolder.register_rl = (RelativeLayout) convertView.findViewById(R.id.right_rl);
            viewHolder.imageView= (RoundImageView) convertView.findViewById(R.id.doctor_img_head);
            viewHolder.doc_name= (TextView) convertView.findViewById(R.id.doctor_name_tv);
            viewHolder.doc_job=(TextView) convertView.findViewById(R.id.doctor_job_tv);
            viewHolder.good_tv=(TextView) convertView.findViewById(R.id.good_at_tv);
            viewHolder.num_tv=(TextView) convertView.findViewById(R.id.doc_num);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Picasso.with(mContext).load(UrlTools.BASE+mListDoctor.get(position).getAvatar()).error(R.mipmap.error_small).into(viewHolder.imageView);
        viewHolder.doc_name.setText(mListDoctor.get(position).getTrueName());
        viewHolder.doc_job.setText(mListDoctor.get(position).getTitle());
        viewHolder.good_tv.setText("待定");
        //上午
        if (flag==0){
            viewHolder.num_tv.setText("余号 "+mListDoctor.get(position).getBeforNum());
        }
        //下午
        if (flag==1){
            viewHolder.num_tv.setText(mListDoctor.get(position).getAfterNum());
        }
        viewHolder.register_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.show();
            }
        });
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mCancel.getId()) {//取消
            mAlertDialog.dismiss();

        } else if (id == mSure.getId()) {//确定
            mAlertDialog.dismiss();
            mSureAlertDialog.show();
        } else if (id == mPrompt.getId()) {//去完善
            mSureAlertDialog.dismiss();
        } else if (id == mPrompt_Cancel.getId()) {
            mSureAlertDialog.dismiss();
        }

    }

    class ViewHolder {
        RoundImageView imageView;
        TextView doc_name;
        TextView doc_job;
        TextView good_tv;
        TextView num_tv;
        RelativeLayout register_rl;
    }
}
