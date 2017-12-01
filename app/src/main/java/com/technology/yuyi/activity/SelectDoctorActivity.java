package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.SelectDoctorListViewAdapter;
import com.technology.yuyi.adapter.SelectRegisterPersonGridViewAlertAdapter;
import com.technology.yuyi.bean.SelectDoctor.DatenumberList;
import com.technology.yuyi.bean.SelectDoctor.Result;
import com.technology.yuyi.bean.SelectDoctor.Root;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectDoctorActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mName_ke;
    private ImageView mBack;
    private ListView mListView;
    private SelectDoctorListViewAdapter mAdapter;
    private List<DatenumberList> mAdapterList = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat;
    private Calendar mCalender;
    private int hour;
    private List<Result> mList = new ArrayList<>();
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //获取所有的挂号医生
            if (msg.what == 30) {
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root!=null&&root.getCode().equals("0")) {
                        int month = 0;
                        int day = 0;
                        mList = root.getResult();
                        if (mList.size() != 0) {
                            //设置日期
                            for (int i = 0; i < mList.size(); i++) {
                                try {
                                    Date date = simpleDateFormat.parse(mList.get(i).getDatastr());
                                    mCalender.setTime(date);
                                    month = mCalender.get(Calendar.MONTH) + 1;
                                    day = mCalender.get(Calendar.DAY_OF_MONTH);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String date = month + "/" + day;
                                mDateList.add(date);
                                //mDate.get(i).setText(date);
                            }
                            mDateMsg_tv.setText(mDateList.get(datePosition));
                            //刚进页面显示第一个日期，上午的医生数据
                            mAdapterList = mList.get(datePosition).getDatenumberList();
                            if (mAdapterList.size()!=0){
                                mAdapter.setmListDoctor(mAdapterList);
                                mAdapter.setFlag(isFlag);
                                mAdapter.notifyDataSetChanged();
                                mAll_Doctor_Rl.setVisibility(View.VISIBLE);
                            }else {
                                nodata_rl.setVisibility(View.VISIBLE);
                                Toast.makeText(SelectDoctorActivity.this,"此门诊目前没有医生信息",Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                }
                //获取挂号时的用户列表
            } else if (msg.what == 35) {
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.UserListBean.Root) {
                    com.technology.yuyi.bean.UserListBean.Root root = (com.technology.yuyi.bean.UserListBean.Root) o;
                    if (root!=null&&root.getResult()!=null){
                        userList = root.getResult();
                        if (userList.size() != 0) {
                            mRegisterAdapter.setmList(userList);
                            mRegisterAdapter.notifyDataSetChanged();
                        }
                    }else {
                        Toast.makeText(SelectDoctorActivity.this,"账号异常，请重新登录",Toast.LENGTH_SHORT).show();
                    }

                }
            } else if (msg.what == 226) {
                ToastUtils.myToast(SelectDoctorActivity.this, "获取列表失败");
                //挂号是否成功
            } else if (msg.what == 40) {
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.RegisterResult.Root) {
                    com.technology.yuyi.bean.RegisterResult.Root root = (com.technology.yuyi.bean.RegisterResult.Root) o;
                    //判断返回的结果
                    if (root.getCode().equals("0")) {//挂号成功
                        if (isFlag) {
                            mAdapterList.get(mPositionDoc).setBeforNum(mAdapterList.get(mPositionDoc).getBeforNum() - 1);
                        } else {
                            mAdapterList.get(mPositionDoc).setAfterNum(mAdapterList.get(mPositionDoc).getAfterNum() - 1);
                        }
                        mAdapter.setmListDoctor(mAdapterList);
                        mAdapter.notifyDataSetChanged();
                        ToastUtils.myToast(SelectDoctorActivity.this, root.getResult());
                    } else if (root.getCode().equals("10101")) {//一天之内只能挂3个人的号
                        ToastUtils.myToast(SelectDoctorActivity.this, "挂号失败，一天之内只能挂3个人的号");
                    } else if (root.getCode().equals("10102")) {//请选择挂号的家庭成员
                        ToastUtils.myToast(SelectDoctorActivity.this, root.getResult());
                    } else if (root.getCode().equals("10103")) {//用户信息不完整，无法挂号
                        ToastUtils.myToast(SelectDoctorActivity.this, "此用户信息不完整，无法挂号");
                    } else if (root.getCode().equals("10104")) {//没有选择挂号门诊的医生
                        ToastUtils.myToast(SelectDoctorActivity.this, root.getResult());
                    } else if (root.getCode().equals("10105")) {//请选择上午还是下午
                        ToastUtils.myToast(SelectDoctorActivity.this, root.getResult());
                    } else if (root.getCode().equals("10106")) {//您选择的医生已经没号了
                        ToastUtils.myToast(SelectDoctorActivity.this, root.getResult());
                    } else if (root.getCode().equals("10107")) {//已经挂过该医生的号了
                        ToastUtils.myToast(SelectDoctorActivity.this, "您已经挂过该医生的好了，亲");
                    } else if (root.getCode().equals("10108")) {//该医生不出诊
                        ToastUtils.myToast(SelectDoctorActivity.this, root.getResult() + "亲");
                    }
                }
            }
        }
    };

    //获取取用户列表的所有数据
    private Map<String, String> map = new HashMap<>();
    private List<com.technology.yuyi.bean.UserListBean.Result> userList = new ArrayList<>();

    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private View mAlertView;
    private TextView mSure;//确定
    private TextView mCancel;//取消

    //确定弹框
    private AlertDialog.Builder mSureBuilder;
    private AlertDialog mSureAlertDialog;
    private View mSureAlertView;
    private TextView mPrompt;//去完善
    private TextView mPrompt_Cancel;//取消
    private GridView mAlertGridView;
    private SelectRegisterPersonGridViewAlertAdapter mRegisterAdapter;
    private boolean isFlag = true;//默认true上午, false 下午
    private long docID;
    private long homeuserId;
    private int numHao;
    private int mPosition = -1;
    private int mPositionDoc = -1;
    private Map<String, String> registerMap = new HashMap();

    private ImageView mLfetDate_img, mRightDate_img;//左右日期
    private TextView mDateMsg_tv, mMorning_tv, mAfternoon_tv;//日期，上午下午
    private List<String> mDateList = new ArrayList<>();
    private int datePosition = 0;
    private RelativeLayout mAll_Doctor_Rl;
    private RelativeLayout nodata_rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_doctor);
        initView();
    }

    public void initView() {
        nodata_rl = (RelativeLayout) findViewById(R.id.nodata_rl);
        nodata_rl.setOnClickListener(this);
        mAll_Doctor_Rl= (RelativeLayout) findViewById(R.id.doctor_all_data_rl);
        //左右日期按钮
        mLfetDate_img = (ImageView) findViewById(R.id.left_date_img);
        mRightDate_img = (ImageView) findViewById(R.id.right_date_img);
        mLfetDate_img.setOnClickListener(this);
        mRightDate_img.setOnClickListener(this);
        //上下午
        mDateMsg_tv = (TextView) findViewById(R.id.date_msg_tv);
        mMorning_tv = (TextView) findViewById(R.id.morning_tv_btn);
        mAfternoon_tv = (TextView) findViewById(R.id.afternoon_tv_msg);
        mMorning_tv.setOnClickListener(this);
        mAfternoon_tv.setOnClickListener(this);

        //初始化预约挂号人
        initSelectUser();
        //时间
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        mCalender = Calendar.getInstance();
        hour = mCalender.get(Calendar.HOUR_OF_DAY);
        //获取网络数据
        mHttptools = HttpTools.getHttpToolsInstance();


        mName_ke = (TextView) findViewById(R.id.name_ke);
        mName_ke.setText(getIntent().getStringExtra("name"));
        //返回
        mBack = (ImageView) findViewById(R.id.doctor_back);
        mBack.setOnClickListener(this);

        //选择医生ListView
        mListView = (ListView) findViewById(R.id.doctor_listview);
        //true代表上午，false代表下午
        mAdapter = new SelectDoctorListViewAdapter(this, mAdapterList, isFlag);
        mAdapter.setFlag(isFlag);
        mListView.setAdapter(mAdapter);
        //点击挂号
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.num_hao);
                //判断用户信息是否完善
                if (userList.get(0).getAge() == 0 | userList.get(0).getTrueName().equals("") | userList.get(0).getGender() == null) {
                    mSureAlertDialog.show();

                } else {
                    if (mAdapterList.size() != 0) {
                        docID = mAdapterList.get(position).getId();
                        Log.e("docid", docID + "");
                        numHao = Integer.valueOf(textView.getText().toString());
                        mPositionDoc = position;
                    }
                    mAlertDialog.show();
                }


            }
        });

        //日期，上午，下午
        // initDate();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
        } else if (id == mCancel.getId()) {//取消
            mPosition = -1;
            mAlertDialog.dismiss();

        } else if (id == mSure.getId()) {//确定
            if (datePosition == 0 && isFlag && hour >= 11) {//过了今天上午11点不能挂上午的号
                Toast.makeText(getApplicationContext(), "抱歉,挂号时间已过", Toast.LENGTH_SHORT).show();
            } else if (datePosition == 0 && !isFlag && hour >= 17) {//过了今天下午17点不能挂下午的号
                Toast.makeText(getApplicationContext(), "抱歉,挂号时间已过", Toast.LENGTH_SHORT).show();
            } else {
                if (mAdapterList.size() != 0) {
                    if (mPosition != -1) {
                        if (numHao > 0) {
                            registerMap.put("datenumberId", String.valueOf(docID));//确定预约挂号时的参数
                            registerMap.put("isAm", String.valueOf(isFlag));//确定预约挂号时的参数
                            registerMap.put("homeuserId", String.valueOf(homeuserId));
                            registerMap.put("token", user.token);
                            mHttptools.sureRegister(mHandler, registerMap);
                            Log.e("homeuserId", homeuserId + "");
                            Log.e("Id", docID + "");
                            Log.e("isAm", isFlag + "");
                            Log.e("token", user.token + "");
                            mAlertDialog.dismiss();
                            mPosition = -1;
                        } else {
                            ToastUtils.myToast(this, "无余号，请重新选择");
                            mAlertDialog.dismiss();
                            mPosition = -1;
                        }
                    } else {
                        ToastUtils.myToast(this, "请选择挂号人");
                    }
                }
            }


        } else if (id == mPrompt.getId()) {//去完善信息
            startActivity(new Intent(SelectDoctorActivity.this, UserEditorActivity.class));
            mSureAlertDialog.dismiss();
        } else if (id == mPrompt_Cancel.getId()) {//取消信息
            mSureAlertDialog.dismiss();
        } else if (id == mLfetDate_img.getId()) {//左日期
            if (datePosition == 0) {
                Toast.makeText(getApplicationContext(), "抱歉，只显示当天的数据", Toast.LENGTH_SHORT).show();
            } else {
                datePosition -= 1;
                mDateMsg_tv.setText(mDateList.get(datePosition));

                mAdapterList = mList.get(datePosition).getDatenumberList();
                mAdapter.setmListDoctor(mAdapterList);
                mAdapter.setFlag(isFlag);
                mAdapter.notifyDataSetChanged();
            }

        } else if (id == mRightDate_img.getId()) {//右日期
            if (datePosition == mDateList.size() - 1) {
                Toast.makeText(getApplicationContext(), "抱歉，最多显示一周的数据", Toast.LENGTH_SHORT).show();
            } else {
                datePosition += 1;
                mDateMsg_tv.setText(mDateList.get(datePosition));

                mAdapterList = mList.get(datePosition).getDatenumberList();
                mAdapter.setmListDoctor(mAdapterList);
                mAdapter.setFlag(isFlag);
                mAdapter.notifyDataSetChanged();
            }

        } else if (id == mMorning_tv.getId()) {//上午
            mMorning_tv.setBackgroundResource(R.color.navigate_tv_select);
            mMorning_tv.setTextColor(ContextCompat.getColor(this, R.color.color_white));

            mAfternoon_tv.setBackgroundResource(R.color.color_white);
            mAfternoon_tv.setTextColor(ContextCompat.getColor(this, R.color.color_drumall));
            isFlag = true;//上午true
            mAdapter.setFlag(isFlag);
            mAdapter.notifyDataSetChanged();
        } else if (id == mAfternoon_tv.getId()) {//下午
            mAfternoon_tv.setBackgroundResource(R.color.navigate_tv_select);
            mAfternoon_tv.setTextColor(ContextCompat.getColor(this, R.color.color_white));

            mMorning_tv.setBackgroundResource(R.color.color_white);
            mMorning_tv.setTextColor(ContextCompat.getColor(this, R.color.color_drumall));
            isFlag = false;//下午false
            mAdapter.setFlag(isFlag);
            mAdapter.notifyDataSetChanged();
        }
    }

    //初始化预约挂号人
    public void initSelectUser() {

        mBuilder = new AlertDialog.Builder(this);
        //挂号alert弹框
        mAlertDialog = mBuilder.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertView = LayoutInflater.from(this).inflate(R.layout.alert_select_register_person, null);
        mAlertDialog.setView(mAlertView);

        //弹框设置适配器
        mAlertGridView = (GridView) mAlertView.findViewById(R.id.be_select_gridview);
        mAlertGridView.setSelector(R.drawable.nopress_bgcolor);
        mRegisterAdapter = new SelectRegisterPersonGridViewAlertAdapter(SelectDoctorActivity.this, userList);
        mAlertGridView.setAdapter(mRegisterAdapter);
        mAlertGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //添加
                if (position == userList.size()) {
                    Intent intent = new Intent(SelectDoctorActivity.this, AddFamilyUserActivity.class);
                    intent.putExtra("type", "0");
                    startActivity(intent);
                    mAlertDialog.dismiss();
                } else {//选择某一个联系人
                    homeuserId = userList.get(position).getId();
                    mPosition = position;
                }

            }
        });


        //取消，确定
        mSure = (TextView) mAlertView.findViewById(R.id.alert_sure);
        mSure.setOnClickListener(this);
        mCancel = (TextView) mAlertView.findViewById(R.id.alert_cancel);
        mCancel.setOnClickListener(this);

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
    protected void onResume() {
        super.onResume();
        map.put("token", user.token);
        mHttptools.getUserLIst(mHandler, map);//获取所有挂号人
        mHttptools.getUserRegisterData(mHandler, getIntent().getIntExtra("cid", -1));//获取所有医生列表
    }

}
