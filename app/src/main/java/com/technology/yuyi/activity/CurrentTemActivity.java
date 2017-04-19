package com.technology.yuyi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.CurrentTemListViewAdapter;
import com.technology.yuyi.bean.UserListBean.Result;
import com.technology.yuyi.bean.UserListBean.Root;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.InformationListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CurrentTemActivity extends AppCompatActivity implements View.OnClickListener {
    private InformationListView mListview;
    private CurrentTemListViewAdapter mAdapter;
    private List<Result> mList = new ArrayList<>();
    private ImageView mBack;

    private RelativeLayout mAdd_rl;
    private TextView mPrompt_tv;
    private TextView mCurrent_tem;//体温数据
    private Button mSure_btn;
    private int mPosintion;
    private boolean isSelect = false;
    private SwipeRefreshLayout mRefresh;
    private HttpTools mHttptools;
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
                // ToastUtils.myToast(CurrentTemActivity.this, "获取用户数据失败");
            } else if (msg.what == 36) {//提交数据接口
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.SubmitTemBean.Root) {
                    com.technology.yuyi.bean.SubmitTemBean.Root root = (com.technology.yuyi.bean.SubmitTemBean.Root) o;
                    if (root.getCode().equals("0")) {
                        ToastUtils.myToast(CurrentTemActivity.this, "提交数据成功");
                        MyDialog.stopDia();
                        finish();
                    } else {
                        ToastUtils.myToast(CurrentTemActivity.this, "提交数据失败");
                        MyDialog.stopDia();
                    }
                }
            } else if (msg.what == 227) {//json解析失败
                MyDialog.stopDia();
            } else if (msg.what == 228) {//提交数据失败
                ToastUtils.myToast(CurrentTemActivity.this, "提交数据失败");
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
        setContentView(R.layout.activity_current_tem);
        initView();
    }

    public void initView() {

        mListview = (InformationListView) findViewById(R.id.userdata_listview);
        mAdapter = new CurrentTemListViewAdapter(this, mList);
        mListview.setAdapter(mAdapter);
        mListview.setSelector(R.color.color_bg);
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosintion = position;
                isSelect = true;
            }
        });
        //返回
        mBack = (ImageView) findViewById(R.id.current_back);
        mBack.setOnClickListener(this);
        //体温数据
        mCurrent_tem = (TextView) findViewById(R.id.current_tem);
        //体温提示文字
        mPrompt_tv = (TextView) findViewById(R.id.tv_prompt);
        //确定提交按钮
        mSure_btn = (Button) findViewById(R.id.btn_sure);
        mSure_btn.setOnClickListener(this);
        //获取用户列表
        mMap.put("token", user.token);
        mHttptools = HttpTools.getHttpToolsInstance();
        //添加按钮
        mAdd_rl = (RelativeLayout) findViewById(R.id.add_user_rl);
        mAdd_rl.setOnClickListener(this);
        //刷新
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.current_tem_refresh);
        mRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mRefresh.setRefreshing(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHttptools.getUserLIst(mHandler, mMap);
            }
        });

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
            submitTemData();
        } else if (id == mAdd_rl.getId()) {//添加
            if (mList.size() != 0) {
                if (mList.get(0).getAge() == 0 | mList.get(0).getTrueName().equals("") | mList.get(0).getGender() == null) {
                    mSureAlertDialog.show();
                } else {
                    Intent intent = new Intent(this, AddFamilyUserActivity.class);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                }
            }

        } else if (id == mPrompt.getId()) {//去完善
            startActivity(new Intent(CurrentTemActivity.this, UserEditorActivity.class));
            mSureAlertDialog.dismiss();
        } else if (id == mPrompt_Cancel.getId()) {
            mSureAlertDialog.dismiss();
        }
    }

    /**
     * 获取体温
     */
    public String getTemData() {
        if (!mCurrent_tem.getText().toString().trim().equals("") && mCurrent_tem.getText().toString() != null) {
            return mCurrent_tem.getText().toString().trim();
        } else {
            return "";
        }
    }

    /**
     * 判断体温提交时，是否超过范围
     *
     * @return
     */
    public boolean checkTem() {
        if (Float.valueOf(getTemData()) < 35 || Float.valueOf(getTemData()) > 42) {
            return false;
        } else {
            return true;
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
        mHttptools.getUserLIst(mHandler, mMap);
        mRefresh.setRefreshing(true);
    }
}
