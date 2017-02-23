package com.technology.yuyi.fragment;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.activity.AddEquipmentActivity;
import com.technology.yuyi.activity.CurrentBloodActivity;
import com.technology.yuyi.activity.CurrentTemActivity;
import com.technology.yuyi.activity.HandInputBloodActivity;
import com.technology.yuyi.activity.HandInputTemActivity;
import com.technology.yuyi.adapter.MeasureListViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeasureFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {
    private ListView mMeasureListView;
    private MeasureListViewAdapter mAdapter;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private View mAlertView;
    private Window mWindow;
    private Button mAutoBtn;
    private Button mHandBtn;
    private Button mCancelBtn;
    private RelativeLayout mAddRelative;
    private int mPosition;
    public MeasureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_measure, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        //设备
        mMeasureListView = (ListView) view.findViewById(R.id.measure_listview_id);
        mMeasureListView.setOnItemClickListener(this);
        mAdapter = new MeasureListViewAdapter(this.getContext());
        mMeasureListView.setAdapter(mAdapter);
        //点击设备弹框
        mBuilder = new AlertDialog.Builder(this.getContext());
        mAlertDialog = mBuilder.create();
        mAlertView = LayoutInflater.from(this.getContext()).inflate(R.layout.measure_alert_box, null);
        mAlertDialog.setView(mAlertView);
        mWindow = mAlertDialog.getWindow();

        mWindow.setGravity(Gravity.BOTTOM);
        mAutoBtn = (Button) mAlertView.findViewById(R.id.btn_auto);
        mAutoBtn.setOnClickListener(this);
        mHandBtn = (Button) mAlertView.findViewById(R.id.btn_hand);
        mHandBtn.setOnClickListener(this);
        mCancelBtn = (Button) mAlertView.findViewById(R.id.btn_cancel);
        mCancelBtn.setOnClickListener(this);
        //添加设备
        mAddRelative= (RelativeLayout) view.findViewById(R.id.add_relative);
        mAddRelative.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAlertDialog.show();
        mPosition=position;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mAutoBtn.getId()) {
           mAlertDialog.dismiss();
            if (mPosition==0){//自动输入体温
                startActivity(new Intent(this.getContext(),CurrentTemActivity.class));
            }else if (mPosition==1){//自动输入血压
                startActivity(new Intent(this.getContext(),CurrentBloodActivity.class));
            }

        } else if (id == mHandBtn.getId()) {
            mAlertDialog.dismiss();

            if (mPosition==0){//手动输入体温
                startActivity(new Intent(this.getContext(),HandInputTemActivity.class));
            }else if (mPosition==1){//手动输入血压
                startActivity(new Intent(this.getContext(),HandInputBloodActivity.class));
            }

        } else if (id == mCancelBtn.getId()) {
            mAlertDialog.dismiss();
        }else if (id == mAddRelative.getId()) {//添加设备
           startActivity(new Intent(this.getContext(),AddEquipmentActivity.class));
        }
    }

}
