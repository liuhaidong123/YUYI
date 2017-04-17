package com.technology.yuyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.HandInputBloodListViewAdapter;
import com.technology.yuyi.bean.UserListBean.Result;
import com.technology.yuyi.bean.UserListBean.Root;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandInputBloodActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mListView;
    private HandInputBloodListViewAdapter mAdapter;
    private List<Result> mList = new ArrayList<>();
    private ImageView mBack;
    private Button mSure_btn;

    private int mPosintion;
    private boolean isSelect = false;
    private SwipeRefreshLayout mRefresh;
    private HttpTools mHttptools;
    private RelativeLayout mAdd_rl;
    private EditText mEditHeight;
    private EditText mEditLow;

    private Map<String, String> mMap = new HashMap<>();
    private Map<String, String> mSubmitMap = new HashMap<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 35) {//获取用户列表
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    mList = root.getResult();
                    mAdapter.setList(mList);
                    mAdapter.notifyDataSetChanged();
                    mRefresh.setRefreshing(false);

                    if (mList.size() == 6) {
                        mAdd_rl.setVisibility(View.GONE);
                    } else {
                        mAdd_rl.setVisibility(View.VISIBLE);
                    }
                }
            } else if (msg.what == 225) {//json解析失败
                mRefresh.setRefreshing(false);
            } else if (msg.what == 226) {//获取数据失败
                mRefresh.setRefreshing(false);
                //ToastUtils.myToast(HandInputBloodActivity.this, "获取用户数据失败");
            } else if (msg.what == 37) {//提交血压数据
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.SubmitTemBean.Root) {
                    com.technology.yuyi.bean.SubmitTemBean.Root root = (com.technology.yuyi.bean.SubmitTemBean.Root) o;
                    if (root.getCode().equals("0")) {
                        ToastUtils.myToast(HandInputBloodActivity.this, "提交数据成功");
                        MyDialog.stopDia();
                        finish();
                    } else {
                        ToastUtils.myToast(HandInputBloodActivity.this, "提交数据失败");
                        MyDialog.stopDia();
                    }
                }
            } else if (msg.what == 229) {//json解析失败
                MyDialog.stopDia();
            } else if (msg.what == 230) {//提交数据失败
                ToastUtils.myToast(HandInputBloodActivity.this, "提交数据失败");
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hand_input_blood);
        initView();
    }

    public void initView() {
        //请求用户列表
        mMap.put("token", user.token);
        mHttptools = HttpTools.getHttpToolsInstance();
        //选择用户
        mListView = (ListView) findViewById(R.id.hand_listview_id);
        mAdapter = new HandInputBloodListViewAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setSelector(R.color.color_bg);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosintion = position;
                isSelect = true;
            }
        });
        //刷新
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.blood_refresh);
        mRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mRefresh.setRefreshing(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHttptools.getUserLIst(mHandler, mMap);
            }
        });
        //返回
        mBack = (ImageView) findViewById(R.id.hand_back);
        mBack.setOnClickListener(this);
        //添加按钮
        mAdd_rl = (RelativeLayout) findViewById(R.id.add_user_rl);
        mAdd_rl.setOnClickListener(this);
        //确定提交按钮
        mSure_btn = (Button) findViewById(R.id.blood_btn_sure);
        mSure_btn.setOnClickListener(this);

        mEditHeight = (EditText) findViewById(R.id.edit_height_blood);
        mEditLow = (EditText) findViewById(R.id.edit_low_blood);

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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mSure_btn.getId()) {//确定提交
            submitBloodData();
        } else if (id == mAdd_rl.getId()) {//添加
            if (mList.size()!=0){
                if (mList.get(0).getAge() == 0 | mList.get(0).getTrueName().equals("")|mList.get(0).getGender()==null) {
                    mSureAlertDialog.show();
                }else {
                    Intent intent = new Intent(this, AddFamilyUserActivity.class);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                }
            }

        }else if (id == mPrompt.getId()) {//去完善
            startActivity(new Intent(HandInputBloodActivity.this,UserEditorActivity.class));
            mSureAlertDialog.dismiss();
        } else if (id == mPrompt_Cancel.getId()) {
            mSureAlertDialog.dismiss();
        }
    }

    /**
     * 获取高压
     *
     * @return
     */
    public String getHeightBlood() {
        if (!mEditHeight.getText().toString().trim().equals("") && mEditHeight.getText().toString() != null) {
            return mEditHeight.getText().toString().trim();
        } else {
            return "";
        }
    }

    /**
     * 获取低压
     *
     * @return
     */
    public String getLowBlood() {
        if (!mEditLow.getText().toString().trim().equals("") && mEditLow.getText().toString() != null) {
            return mEditLow.getText().toString().trim();
        } else {
            return "";
        }
    }

    /**
     * 判断输入的血压是否在范围之内
     */
    public boolean checkHeightBlood() {
        if (Integer.valueOf(getHeightBlood()) > 180 || Integer.valueOf(getHeightBlood()) < 40 || Integer.valueOf(getLowBlood()) < 40 || Integer.valueOf(getLowBlood()) > 180) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 提交
     */

    public void submitBloodData() {
        if (!getHeightBlood().equals("")) {//高压
            if (!getLowBlood().equals("")) {//低压
                if (checkHeightBlood()) {//判断输入的数据是否在规定范围内
                    if (isSelect) {//选中用户
                        mSubmitMap.put("token", user.token);
                        mSubmitMap.put("humeuserId", mList.get(mPosintion).getId() + "");
                        mSubmitMap.put("systolic", getHeightBlood());
                        mSubmitMap.put("diastolic", getLowBlood());
                        mHttptools.submitBloodData(mHandler, mSubmitMap);
                        MyDialog.showDialog(this);
                    } else {
                        Toast.makeText(this, "请选择用户", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(this, "请输入正确的血压数据", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请输入低压数据", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入高压数据", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mHttptools.getUserLIst(mHandler, mMap);
        mRefresh.setRefreshing(true);

    }
}
