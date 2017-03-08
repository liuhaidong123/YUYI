package com.technology.yuyi.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.EquitmentManageListViewAdapter;

public class EquipmentManageActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mListView;
    private EquitmentManageListViewAdapter mAdapter;
    private ImageView mBack;
    private View mFooterView;//添加设备
    private AlertDialog.Builder mBuilder;//删除设备的弹框
    private AlertDialog mAlertDialog;
    private View mAlertView;
    private  TextView mSure_btn;
    private  TextView mCancel_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_manage);
        initView();
    }

    public void initView() {

        mAlertView = LayoutInflater.from(this).inflate(R.layout.alert_delete_equitment_box, null);
        //确定按钮
        mSure_btn= (TextView) mAlertView.findViewById(R.id.alert_sure);
        mSure_btn.setOnClickListener(this);
        //取消按钮
        mCancel_btn= (TextView) mAlertView.findViewById(R.id.alert_cancel);
        mCancel_btn.setOnClickListener(this);
        //长安设备的弹框
        mBuilder = new AlertDialog.Builder(this);
        mAlertDialog = mBuilder.create();
        mAlertDialog.setView(mAlertView);
        //设备列表
        mFooterView = LayoutInflater.from(this).inflate(R.layout.add_other_equitment, null);
        mListView = (ListView) findViewById(R.id.equitment_listview_id);
        mAdapter = new EquitmentManageListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.addFooterView(mFooterView);
        //设备长按事件
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //这里需要判断添加其他设备，因为他是listview的footer
                mAlertDialog.show();
                return false;
            }
        });
        //返回
        mBack = (ImageView) findViewById(R.id.equit_back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        }else if (id==mSure_btn.getId()){//确定按钮
            mAlertDialog.dismiss();
        }else if (id==mCancel_btn.getId()){//取消按钮
            mAlertDialog.dismiss();
        }
    }
}
