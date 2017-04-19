package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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
    private TextView mDate1;//日期
    private TextView mDate1Morning;//上午
    private TextView mDate1Afternoon;//下午
    private TextView mDate2;
    private TextView mDate2Morning;
    private TextView mDate2Afternoon;
    private TextView mDate3;
    private TextView mDate3Morning;
    private TextView mDate3Afternoon;
    private TextView mDate4;
    private TextView mDate4Morning;
    private TextView mDate4Afternoon;
    private TextView mDate5;
    private TextView mDate5Morning;
    private TextView mDate5Afternoon;
    private TextView mDate6;
    private TextView mDate6Morning;
    private TextView mDate6Afternoon;
    private TextView mDate7;
    private TextView mDate7Morning;
    private TextView mDate7Afternoon;
    private final String mGreen = "#25f368";
    private final String mGay = "#6a6a6a";
    private final String mWhite = "#ffffff";


    private List<TextView> mDate = new ArrayList<>();
    private List<TextView> mMorninng = new ArrayList<>();
    private List<TextView> mAfternoon = new ArrayList<>();
    private SimpleDateFormat simpleDateFormat;
    private final int morning = 0;//0代表上午，1代表下午
    private final int afternoon = 1;
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
                    if (root.getCode().equals("0")) {
                        int month = 0;
                        int day = 0;
                        mList = root.getResult();
                        if (mList.size() != 0) {
                            //设置日期
                            for (int i = 0; i < mList.size(); i++) {
                                try {
                                    Date date = simpleDateFormat.parse(mList.get(i).getDatastr());
                                    month = date.getMonth() + 1;
                                    day = date.getDate();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                String date = month + "/" + day;
                                mDate.get(i).setText(date);
                            }
                            //刚进页面显示第一个日期，上午的医生数据
                            mAdapterList = mList.get(0).getDatenumberList();
                            mAdapter.setmListDoctor(mAdapterList);
                            mAdapter.setFlag(morning);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                }
                //获取挂号时的用户列表
            } else if (msg.what == 35) {
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.UserListBean.Root) {
                    com.technology.yuyi.bean.UserListBean.Root root = (com.technology.yuyi.bean.UserListBean.Root) o;
                    userList = root.getResult();
                    if (userList.size() != 0) {
                        mRegisterAdapter.setmList(userList);
                        mRegisterAdapter.notifyDataSetChanged();
                    }
                }
            } else if (msg.what == 226) {
                ToastUtils.myToast(SelectDoctorActivity.this, "获取列表失败");
                //挂号是否成功
            } else if (msg.what == 40) {
                Object o = msg.obj;
                if (o!=null&&o instanceof com.technology.yuyi.bean.RegisterResult.Root){
                    com.technology.yuyi.bean.RegisterResult.Root root= (com.technology.yuyi.bean.RegisterResult.Root) o;
                    //判断返回的结果
                    if (root.getCode().equals("0")){//挂号成功
                        if (isFlag){
                            mAdapterList.get(mPositionDoc).setBeforNum( mAdapterList.get(mPositionDoc).getBeforNum()-1);
                        }else {
                            mAdapterList.get(mPositionDoc).setAfterNum( mAdapterList.get(mPositionDoc).getAfterNum()-1);
                        }
                        mAdapter.setmListDoctor(mAdapterList);
                        mAdapter.notifyDataSetChanged();
                        ToastUtils.myToast(SelectDoctorActivity.this,root.getResult());
                    }else if (root.getCode().equals("10101")){//一天之内只能挂3个人的号
                        ToastUtils.myToast(SelectDoctorActivity.this,"挂号失败，一天之内只能挂3个人的号");
                    }else if (root.getCode().equals("10102")){//请选择挂号的家庭成员
                        ToastUtils.myToast(SelectDoctorActivity.this,root.getResult());
                    }else if (root.getCode().equals("10103")){//用户信息不完整，无法挂号
                        ToastUtils.myToast(SelectDoctorActivity.this,"此用户信息不完整，无法挂号");
                    }else if (root.getCode().equals("10104")){//没有选择挂号门诊的医生
                        ToastUtils.myToast(SelectDoctorActivity.this,root.getResult());
                    }else if (root.getCode().equals("10105")){//请选择上午还是下午
                        ToastUtils.myToast(SelectDoctorActivity.this,root.getResult());
                    }else if (root.getCode().equals("10106")){//您选择的医生已经没号了
                        ToastUtils.myToast(SelectDoctorActivity.this,root.getResult());
                    }else if (root.getCode().equals("10107")){//已经挂过该医生的号了
                        ToastUtils.myToast(SelectDoctorActivity.this,"您已经挂过该医生的好了，亲");
                    }else if (root.getCode().equals("10108")){//该医生不出诊
                        ToastUtils.myToast(SelectDoctorActivity.this,root.getResult()+"亲");
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
    private int mPositionDoc=-1;
    private Map<String, String> registerMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_doctor);
        initView();
    }

    public void initView() {

        //初始化预约挂号人
        initSelectUser();
        //时间
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        //获取网络数据
        mHttptools = HttpTools.getHttpToolsInstance();


        mName_ke = (TextView) findViewById(R.id.name_ke);
        mName_ke.setText(getIntent().getStringExtra("name"));
        //返回
        mBack = (ImageView) findViewById(R.id.doctor_back);
        mBack.setOnClickListener(this);

        //选择医生ListView
        mListView = (ListView) findViewById(R.id.doctor_listview);
        //0代表上午，1代表下午
        mAdapter = new SelectDoctorListViewAdapter(this, mAdapterList, morning);
        mListView.setAdapter(mAdapter);
        //点击挂号
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.num_hao);
                //判断用户信息是否完善
                if (userList.get(0).getAge() == 0 | userList.get(0).getTrueName().equals("")|userList.get(0).getGender()==null) {
                    mSureAlertDialog.show();

                } else {
                    if (mAdapterList.size() != 0) {
                        docID = mAdapterList.get(position).getId();
                        Log.e("docid", docID + "");
                        numHao = Integer.valueOf(textView.getText().toString());
                        mPositionDoc=position;
                    }
                    mAlertDialog.show();
                }


            }
        });

        //日期，上午，下午
        initDate();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            finish();
            //第1个日期上午
        } else if (id == mDate1Morning.getId()) {
            setColor(id);
            showNum(morning, 0);
        } else if (id == mDate1Afternoon.getId()) {
            setColor(id);
            showNum(afternoon, 0);
            //第2个日期上午
        } else if (id == mDate2Morning.getId()) {
            setColor(id);
            showNum(morning, 1);
        } else if (id == mDate2Afternoon.getId()) {
            setColor(id);
            showNum(afternoon, 1);
            //第3个日期上午
        } else if (id == mDate3Morning.getId()) {
            setColor(id);
            showNum(morning, 2);
        } else if (id == mDate3Afternoon.getId()) {
            setColor(id);
            showNum(afternoon, 2);
            //第4个日期上午
        } else if (id == mDate4Morning.getId()) {
            setColor(id);
            showNum(morning, 3);
        } else if (id == mDate4Afternoon.getId()) {
            setColor(id);
            showNum(afternoon, 3);
            //第5个日期上午
        } else if (id == mDate5Morning.getId()) {
            setColor(id);
            showNum(morning, 4);
        } else if (id == mDate5Afternoon.getId()) {
            setColor(id);
            showNum(afternoon, 4);
            //第6个日期上午
        } else if (id == mDate6Morning.getId()) {
            setColor(id);
            showNum(morning, 5);
        } else if (id == mDate6Afternoon.getId()) {
            setColor(id);
            showNum(afternoon, 5);
            //第7个日期上午
        } else if (id == mDate7Morning.getId()) {
            setColor(id);
            showNum(morning, 6);
        } else if (id == mDate7Afternoon.getId()) {
            setColor(id);
            showNum(afternoon, 6);
        } else if (id == mCancel.getId()) {//取消
            mPosition = -1;
            mAlertDialog.dismiss();

        } else if (id == mSure.getId()) {//确定
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
                    } else {
                        ToastUtils.myToast(this, "无余号，请重新选择");
                        mAlertDialog.dismiss();
                    }
                } else {
                    ToastUtils.myToast(this, "请选择挂号人");
                }
            }

        }
        else if (id == mPrompt.getId()) {//去完善
            startActivity(new Intent(SelectDoctorActivity.this,UserEditorActivity.class));
            mSureAlertDialog.dismiss();
        } else if (id == mPrompt_Cancel.getId()) {
            mSureAlertDialog.dismiss();
        }
    }

    //初始化日期，上午，下午
    public void initDate() {
        mDate1 = (TextView) findViewById(R.id.date1);
        mDate1Morning = (TextView) findViewById(R.id.date1_morning);
        mDate1Afternoon = (TextView) findViewById(R.id.date1_afternoon);
        mDate1Morning.setOnClickListener(this);
        mDate1Afternoon.setOnClickListener(this);
        mDate.add(mDate1);
        mMorninng.add(mDate1Morning);
        mAfternoon.add(mDate1Afternoon);

        mDate2 = (TextView) findViewById(R.id.date2);
        mDate2Morning = (TextView) findViewById(R.id.date2_morning);
        mDate2Afternoon = (TextView) findViewById(R.id.date2_afternoon);
        mDate2Morning.setOnClickListener(this);
        mDate2Afternoon.setOnClickListener(this);
        mDate.add(mDate2);
        mMorninng.add(mDate2Morning);
        mAfternoon.add(mDate2Afternoon);

        mDate3 = (TextView) findViewById(R.id.date3);
        mDate3Morning = (TextView) findViewById(R.id.date3_morning);
        mDate3Afternoon = (TextView) findViewById(R.id.date3_afternoon);
        mDate3Morning.setOnClickListener(this);
        mDate3Afternoon.setOnClickListener(this);
        mDate.add(mDate3);
        mMorninng.add(mDate3Morning);
        mAfternoon.add(mDate3Afternoon);

        mDate4 = (TextView) findViewById(R.id.date4);
        mDate4Morning = (TextView) findViewById(R.id.date4_morning);
        mDate4Afternoon = (TextView) findViewById(R.id.date4_afternoon);
        mDate4Morning.setOnClickListener(this);
        mDate4Afternoon.setOnClickListener(this);
        mDate.add(mDate4);
        mMorninng.add(mDate4Morning);
        mAfternoon.add(mDate4Afternoon);

        mDate5 = (TextView) findViewById(R.id.date5);
        mDate5Morning = (TextView) findViewById(R.id.date5_morning);
        mDate5Afternoon = (TextView) findViewById(R.id.date5_afternoon);
        mDate5Morning.setOnClickListener(this);
        mDate5Afternoon.setOnClickListener(this);
        mDate.add(mDate5);
        mMorninng.add(mDate5Morning);
        mAfternoon.add(mDate5Afternoon);

        mDate6 = (TextView) findViewById(R.id.date6);
        mDate6Morning = (TextView) findViewById(R.id.date6_morning);
        mDate6Afternoon = (TextView) findViewById(R.id.date6_afternoon);
        mDate6Morning.setOnClickListener(this);
        mDate6Afternoon.setOnClickListener(this);
        mDate.add(mDate6);
        mMorninng.add(mDate6Morning);
        mAfternoon.add(mDate6Afternoon);

        mDate7 = (TextView) findViewById(R.id.date7);
        mDate7Morning = (TextView) findViewById(R.id.date7_morning);
        mDate7Afternoon = (TextView) findViewById(R.id.date7_afternoon);
        mDate7Morning.setOnClickListener(this);
        mDate7Afternoon.setOnClickListener(this);
        mDate.add(mDate7);
        mMorninng.add(mDate7Morning);
        mAfternoon.add(mDate7Afternoon);

    }

    //点击上午下午设置颜色
    public void setColor(int id) {

        for (int i = 0; i < mMorninng.size(); i++) {
            //如果点击的是上午
            if (id == mMorninng.get(i).getId()) {
                //所有的下午颜色都是灰色，背景都是白色
                for (int j = 0; j < mAfternoon.size(); j++) {
                    mAfternoon.get(j).setTextColor(Color.parseColor(mGay));
                    mAfternoon.get(j).setBackgroundColor(Color.parseColor(mWhite));
                }

                for (int k = 0; k < mMorninng.size(); k++) {
                    //设置选中的上午颜色及日期颜色，和背景都是绿色
                    if (k == i) {
                        mDate.get(k).setTextColor(Color.parseColor(mGreen));
                        mMorninng.get(k).setTextColor(Color.parseColor(mWhite));
                        mMorninng.get(k).setBackgroundColor(Color.parseColor(mGreen));
                        //其他的都是灰色，白色
                    } else {
                        mDate.get(k).setTextColor(Color.parseColor(mGay));
                        mMorninng.get(k).setTextColor(Color.parseColor(mGay));
                        mMorninng.get(k).setBackgroundColor(Color.parseColor(mWhite));
                    }
                }

            }
        }


        for (int j = 0; j < mAfternoon.size(); j++) {
            //如果点击的是下午
            if (id == mAfternoon.get(j).getId()) {
                //所有的上午颜色都是灰色，背景是白色
                for (int i = 0; i < mMorninng.size(); i++) {
                    mMorninng.get(i).setTextColor(Color.parseColor(mGay));
                    mMorninng.get(i).setBackgroundColor(Color.parseColor(mWhite));
                }

                //设置选中的下午
                for (int k = 0; k < mAfternoon.size(); k++) {
                    //当前点击的下午所有颜色
                    if (k == j) {
                        //设置选中的下午颜色及日期颜色，和背景
                        mDate.get(k).setTextColor(Color.parseColor(mGreen));
                        mAfternoon.get(k).setTextColor(Color.parseColor(mWhite));
                        mAfternoon.get(k).setBackgroundColor(Color.parseColor(mGreen));
                    } else {
                        //设置选中的下午颜色及日期颜色，和背景
                        mDate.get(k).setTextColor(Color.parseColor(mGay));
                        mAfternoon.get(k).setTextColor(Color.parseColor(mGay));
                        mAfternoon.get(k).setBackgroundColor(Color.parseColor(mWhite));
                    }
                }

            }
        }

    }

    public void showNum(int flag, int position) {
        mAdapter.setFlag(flag);
        if (flag == morning) {
            isFlag = true;//上午为true
        } else {
            isFlag = false;
        }

        if (mList.size() != 0) {
            mAdapterList = mList.get(position).getDatenumberList();
            mAdapter.setmListDoctor(mAdapterList);
        }
        mAdapter.notifyDataSetChanged();
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
        Log.e("00000", "123456");
        map.put("token", user.token);
        mHttptools.getUserLIst(mHandler, map);//获取所有挂号人
        mHttptools.getUserRegisterData(mHandler, getIntent().getIntExtra("cid", -1));//获取所有医生列表
    }


}
