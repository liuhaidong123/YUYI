package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.technology.yuyi.adapter.HandInputTemListViewAdapter;
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

public class HandInputTemActivity extends AppCompatActivity implements View.OnClickListener {
    private InformationListView mListView;
    private HandInputTemListViewAdapter mAdapter;
    private List<Result> mList = new ArrayList<>();

    private ImageView mBack;
    private RelativeLayout mAdd_rl;
    private EditText mEdit;
    private TextView mPrompt_tv;
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
                ToastUtils.myToast(HandInputTemActivity.this, "获取用户数据失败");
            } else if (msg.what == 36) {//提交数据接口
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.SubmitTemBean.Root) {
                    com.technology.yuyi.bean.SubmitTemBean.Root root = (com.technology.yuyi.bean.SubmitTemBean.Root) o;
                    if (root.getCode().equals("0")) {
                        Toast.makeText(HandInputTemActivity.this, "提交数据成功", Toast.LENGTH_SHORT).show();
                        MyDialog.stopDia();
                        finish();
                    } else {
                        Toast.makeText(HandInputTemActivity.this, "提交数据失败", Toast.LENGTH_SHORT).show();
                        MyDialog.stopDia();
                    }
                }
            } else if (msg.what == 227) {//json解析失败
                MyDialog.stopDia();
            } else if (msg.what == 228) {//提交数据失败
                ToastUtils.myToast(HandInputTemActivity.this, "提交数据失败");
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
        setContentView(R.layout.activity_hand_input_tem);
        initView();
    }

    public void initView() {
        //请求用户列表
        mMap.put("token", user.token);
        mHttptools = HttpTools.getHttpToolsInstance();

        //刷新
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.tem_refresh);
        mRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mRefresh.setRefreshing(true);
        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHttptools.getUserLIst(mHandler, mMap);
            }
        });
        mListView = (InformationListView) findViewById(R.id.hand_tem_listview_id);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mPosintion = position;
                isSelect = true;
            }
        });
        mAdapter = new HandInputTemListViewAdapter(this, mList);
        mListView.setAdapter(mAdapter);
        mListView.setSelector(R.color.color_bg);

        //返回
        mBack = (ImageView) findViewById(R.id.hand_back_tem);
        mBack.setOnClickListener(this);

        //确定提交按钮
        mSure_btn = (Button) findViewById(R.id.blood_btn_sure);
        mSure_btn.setOnClickListener(this);
        //添加按钮
        mAdd_rl = (RelativeLayout) findViewById(R.id.add_user_rl);
        mAdd_rl.setOnClickListener(this);
        //体温提示文字
        mPrompt_tv = (TextView) findViewById(R.id.tv_prompt_input);

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
        //输入框
        mEdit = (EditText) findViewById(R.id.edit_tem);
        mEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() != null && !"".equals(s.toString())) {
                    if (Float.valueOf(s.toString()) <= 36) {
                        mPrompt_tv.setText("*您当前体温处于低烧状态，请尽快就医");
                        mPrompt_tv.setTextColor(Color.parseColor("#e80000"));
                    } else if (Float.valueOf(s.toString()) >= 38) {
                        mPrompt_tv.setText("*您当前体温处于高烧状态，请尽快就医");
                        mPrompt_tv.setTextColor(Color.parseColor("#e80000"));
                    } else {
                        mPrompt_tv.setText("*您当前体温处于正常状态");
                        mPrompt_tv.setTextColor(Color.parseColor("#25f368"));
                    }
                } else {
                    mPrompt_tv.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        } else if (id == mSure_btn.getId()) {//确定提交
            submitTemData();
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
            startActivity(new Intent(HandInputTemActivity.this,UserEditorActivity.class));
            mSureAlertDialog.dismiss();
        } else if (id == mPrompt_Cancel.getId()) {
            mSureAlertDialog.dismiss();
        }
    }

    /**
     * 获取体温
     */
    public String getTemData() {
        if (!mEdit.getText().toString().trim().equals("") && mEdit.getText().toString() != null) {
            return mEdit.getText().toString().trim();
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
            if (checkTem()) {//体温在正常范围之内35~42之间
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
                Toast.makeText(this, "请输入正确的体温", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入体温", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHttptools.getUserLIst(mHandler, mMap);
        mRefresh.setRefreshing(true);
    }

}
