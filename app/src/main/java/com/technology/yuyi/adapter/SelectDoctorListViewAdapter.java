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

import com.technology.yuyi.R;
import com.technology.yuyi.myview.RoundImageView;

import java.util.ArrayList;

/**
 * Created by liuhaidong on 2017/2/27.
 */

public class SelectDoctorListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private LayoutInflater mInflater;
    private Context mContext;
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

    public SelectDoctorListViewAdapter(Context mContext) {
        this.mContext = mContext;
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

    @Override
    public int getCount() {
        return 2;
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
            convertView.setTag(convertView);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
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
        }else if (id == mPrompt.getId()) {//去完善
            mSureAlertDialog.dismiss();
        }else if (id == mPrompt_Cancel.getId()) {
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
