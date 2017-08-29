package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.RecycleAdapter;
import com.technology.yuyi.bean.UserListBean.Result;
import com.technology.yuyi.bean.UserListBean.Root;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.SanJiao;
import com.technology.yuyi.myview.SanJiaoHand;
import com.technology.yuyi.myview.TherC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandInputTemActivity2 extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView mRecycleview;
    private RecycleAdapter mAdapter;
    private List<Result> mList = new ArrayList<>();
    private List<Boolean> showNameList = new ArrayList<>();
    private ImageView mBack;
    private TextView mSure_btn;
    private TextView mCurrent_tem, du_tv;//体温数据
    private TextView mPrompt_tv, mName;
    private boolean isSelect = false;
    private Map<String, String> mSubmitMap = new HashMap<>();
    private int mPosintion = -1;
    private HttpTools mHttptools;
    private Map<String, String> mMap = new HashMap<>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 35) {//获取用户列表
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root != null && root.getResult() != null) {
                        showNameList.clear();
                        mList = root.getResult();
                        for (int i = 0; i < mList.size(); i++) {
                            if (i == 0) {
                                showNameList.add(true);
                            } else {
                                showNameList.add(false);
                            }

                        }
                        mAdapter.setmList(mList);
                        mAdapter.setShowNameList(showNameList);
                        mRecycleview.setAdapter(mAdapter);
                        // mAdapter.notifyDataSetChanged();
                        mPosintion = 0;
                        isSelect = true;
                    }


                }
            } else if (msg.what == 36) {//提交数据接口
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.SubmitTemBean.Root) {
                    com.technology.yuyi.bean.SubmitTemBean.Root root = (com.technology.yuyi.bean.SubmitTemBean.Root) o;
                    if (root.getCode().equals("0")) {
                        ToastUtils.myToast(getApplicationContext(), "提交数据成功");
                        MyDialog.stopDia();
                        finish();
                    } else {
                        ToastUtils.myToast(getApplicationContext(), "提交数据失败");
                        MyDialog.stopDia();
                    }
                }
            } else if (msg.what == 227) {//json解析失败
                MyDialog.stopDia();
            } else if (msg.what == 228) {//提交数据失败
                ToastUtils.myToast(getApplicationContext(), "提交数据失败");
                MyDialog.stopDia();
            }

        }
    };

    //确定弹框
    private AlertDialog.Builder mSureBuilder;
    private AlertDialog mSureAlertDialog;
    private View mSureAlertView;
    private TextView mPrompt;//去完善
    private TextView mPrompt_Cancel;//取消
    private RelativeLayout mTem_rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_input_tem2);
        //获取用户列表
        mMap.put("token", user.token);
        mHttptools = HttpTools.getHttpToolsInstance();
        initUI();
    }
    private void initUI() {

        mRecycleview = (RecyclerView) findViewById(R.id.recycle_id);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.HORIZONTAL);//水平走向
        mRecycleview.setLayoutManager(manager);
        mAdapter = new RecycleAdapter(mList, showNameList, this);
        mAdapter.setOnItemClickLitener(new RecycleAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                // LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.show_name_ll);
                TextView textView = (TextView) view.findViewById(R.id.name);
                ImageView imageView = (ImageView) view.findViewById(R.id.sanjiao_img);
                if (mList.size() == 0) {//只有添加按钮
                    Intent intent = new Intent(getApplicationContext(), AddFamilyUserActivity.class);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                } else {
                    if (position == mList.size()) {//最后一个是添加按钮
                        Intent intent = new Intent(getApplicationContext(), AddFamilyUserActivity.class);
                        intent.putExtra("type", "0");
                        startActivity(intent);
                    } else {
                        //用户信息不完善
                        if (mList.get(0).getAge() == 0 | mList.get(0).getTrueName().equals("") | mList.get(0).getGender() == null) {
                            mSureAlertDialog.show();
                        } else {//点击的是某个用户头像

                            for (int i = 0; i < mList.size(); i++) {
                                if (position == i) {
                                    showNameList.set(position, true);
                                } else {
                                    showNameList.set(i, false);
                                }
                            }

                            mAdapter.setShowNameList(showNameList);
                            mAdapter.notifyDataSetChanged();

                            mPosintion = position;
                            Log.e("name", mList.get(position).getTrueName());
                        }
                    }

                }
            }
        });
        //返回
        mBack = (ImageView) findViewById(R.id.current_back);
        mBack.setOnClickListener(this);

        //保存
        mSure_btn = (TextView) findViewById(R.id.btn_sure);
        mSure_btn.setOnClickListener(this);

        //体温数据
        mCurrent_tem = (TextView) findViewById(R.id.current_tem);
        du_tv = (TextView) findViewById(R.id.du);
        //体温提示文字
        mPrompt_tv = (TextView) findViewById(R.id.tv_prompt);
        //姓名
        mName = (TextView) findViewById(R.id.name);
        //显示温度计
        mTem_rl = (RelativeLayout) findViewById(R.id.my_tem_rl);
        SanJiaoHand sanJiao=new SanJiaoHand(this,37,mCurrent_tem,du_tv,mPrompt_tv);//默认体温37
        TherC therC = new TherC(this);
        mCurrent_tem.setText("37");
        mTem_rl.addView(therC);
        mTem_rl.addView(sanJiao);

        //信息不完整弹框
        mSureBuilder = new AlertDialog.Builder(this);
        mSureAlertDialog = mSureBuilder.create();
        mSureAlertDialog.setCanceledOnTouchOutside(false);
        mSureAlertView = LayoutInflater.from(this).inflate(R.layout.alert_sure, null);
        mSureAlertDialog.setView(mSureAlertView);
        //去完善、取消
        mPrompt = (TextView) mSureAlertView.findViewById(R.id.alert_sure_prompt);
        mPrompt.setOnClickListener(this);
        mPrompt_Cancel = (TextView) mSureAlertView.findViewById(R.id.alert_sure_cancel);
        mPrompt_Cancel.setOnClickListener(this);

        if (Integer.valueOf(mCurrent_tem.getText().toString().trim()) <= 36) {
            mPrompt_tv.setText("*当前体温过低,请查看测量部位");
            mPrompt_tv.setTextColor(Color.parseColor("#1ebeec"));
            mCurrent_tem.setTextColor(Color.parseColor("#1ebeec"));
            du_tv.setTextColor(Color.parseColor("#1ebeec"));
        } else if (Integer.valueOf(mCurrent_tem.getText().toString().trim()) >= 38) {
            mPrompt_tv.setText("*当前体温过高,请尽快就医");
            mPrompt_tv.setTextColor(Color.parseColor("#f6547a"));
            mCurrent_tem.setTextColor(Color.parseColor("#f6547a"));
            du_tv.setTextColor(Color.parseColor("#f6547a"));
        } else {
            mPrompt_tv.setText("*当前体温正常");
            mPrompt_tv.setTextColor(Color.parseColor("#f654f5"));
            mCurrent_tem.setTextColor(Color.parseColor("#f654f5"));
            du_tv.setTextColor(Color.parseColor("#f654f5"));
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mSure_btn.getId()) {//保存
            submitTemData();
        } else if (id == mPrompt.getId()) {//去完善
            startActivity(new Intent(this, UserEditorActivity.class));
            mSureAlertDialog.dismiss();
        } else if (id == mPrompt_Cancel.getId()) {
            mSureAlertDialog.dismiss();
        }
    }

    /**
     * 获取体温
     */
    public String getTemData() {
        if (!mCurrent_tem.getText().toString().trim().equals("") && mCurrent_tem.getText().toString() != null&&Double.valueOf(mCurrent_tem.getText().toString().trim())!=0) {
            return mCurrent_tem.getText().toString().trim();
        } else {
            return "";
        }
    }

    /**
     * 提交体温
     */
    public void submitTemData() {
        if (!getTemData().equals("")) {//体温不为""；
            if (isSelect) {//选中了某一个用户
                mSubmitMap.put("token", user.token);
                mSubmitMap.put("humeuserId", mList.get(mPosintion).getId() + "");
                mSubmitMap.put("temperaturet", getTemData());
                mHttptools.submitTemData(mHandler, mSubmitMap);
                MyDialog.showDialog(this);
            } else {
                Toast.makeText(this, "请选择用户", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "体温错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHttptools.getUserLIst(mHandler, mMap);//
    }
}
