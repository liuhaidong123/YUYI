package com.technology.yuyi.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.activity.AddFamilyUserActivity;
import com.technology.yuyi.activity.AppointmentActivity;
import com.technology.yuyi.activity.GaoDeLocateActivity;
import com.technology.yuyi.activity.InformationActivity;
import com.technology.yuyi.activity.InformationDetailsActivity;
import com.technology.yuyi.activity.MS_allkinds_activity;
import com.technology.yuyi.activity.MS_drugInfo_activity;
import com.technology.yuyi.activity.MS_home_Activity;
import com.technology.yuyi.activity.SearchActivity;
import com.technology.yuyi.activity.SelectDoctorActivity;
import com.technology.yuyi.activity.UserEditorActivity;
import com.technology.yuyi.adapter.FirstPageListViewAdapter;
import com.technology.yuyi.adapter.UseDrugGridViewAdapter;
import com.technology.yuyi.adapter.ViewPagerAdAdapter;
import com.technology.yuyi.adapter.ViewPagerBloodTemAdapter;
import com.technology.yuyi.bean.AdBean.Root;
import com.technology.yuyi.bean.AdBean.Rows;
import com.technology.yuyi.bean.FirstPageDrugSixData;
import com.technology.yuyi.bean.FirstPageDrugSixDataRoot;
import com.technology.yuyi.bean.FirstPageInformationTwoData;
import com.technology.yuyi.bean.FirstPageInformationTwoDataRoot;
import com.technology.yuyi.bean.FirstPageUserDataBean.BloodpressureList;
import com.technology.yuyi.bean.FirstPageUserDataBean.Result;
import com.technology.yuyi.bean.FirstPageUserDataBean.TemperatureList;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.BloodView;
import com.technology.yuyi.myview.InformationListView;
import com.technology.yuyi.myview.RoundImageView;
import com.technology.yuyi.myview.TemView;
import com.technology.yuyi.viewpager.impl.AdListenerImpl;
import com.technology.yuyi.viewpager.impl.BloodTemImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstPageFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, AMapLocationListener {
    private RelativeLayout mEdit_rl;//搜索
    private TextView mLocate_tv;
    private RelativeLayout mScrollRelative;

    private SwipeRefreshLayout mSwipeRefresh;

    private FirstPageListViewAdapter mListViewAdapter;//资讯adapter
    private ListView mFirstPageListView;//资讯ListView
    private List<com.technology.yuyi.bean.UpdatedFirstPageTwoDataBean.Rows> mInforList = new ArrayList<>();

    private GridView mGridview;//常用药品Gridview
    private UseDrugGridViewAdapter mUseDrugAdapter;//常用药品adapter
    private List<FirstPageDrugSixData> mGridList = new ArrayList<>();

    private ViewPager mViewPagerAD;
    private ViewPagerAdAdapter AdAdapter;
    private List<Rows> mListAd = new ArrayList<>();//广告图片集合
    private ImageView mCircleImg;
    private ImageView[] mArrImageView;//存放广告小圆点的数组
    private ViewGroup mGroup;//存放小圆点容器

    private ViewPager mViewPagerBlood;
    private ViewPagerBloodTemAdapter mBloodTemAdapter;
    private BloodView mBloodView;
    private TemView mTemView;
    private LinearLayout drugmall_ll;
    private ArrayList<Integer> YbloodNum = new ArrayList<>();//y轴血压数据
    private ArrayList<String> XdateNum = new ArrayList<>();//x轴日期数据
    private ArrayList<Integer> heightBloodData = new ArrayList<>();  //血压高压数据
    private ArrayList<Integer> lowBloodData = new ArrayList<>();//血压低压数据

    private ArrayList<Integer> YTemData = new ArrayList<>();//Y轴温度
    private ArrayList<String> XTemdateNum = new ArrayList<>(); //x轴日期数据
    private ArrayList<Float> temData = new ArrayList<>(); //体温
    private ArrayList<View> mViewList = new ArrayList<>();
    private ViewGroup mBloodGroup;
    private ImageView[] mBloodImageViewArr;//存放血压体温小圆点的数组

    private RelativeLayout mInformation_rl;//资讯跳转
    private LinearLayout mRegister_ll;//预约挂号
    private RelativeLayout mStaple_drug_rl;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private final int LOCATE_CODE = 123;

    private LinearLayout mAllUser_ll;//首页全部用户布局
    private List<Result> mUserData = new ArrayList();

    private HttpTools mHttptools;
    //广告
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (handler.hasMessages(1)) {
                handler.removeMessages(1);
            }
            if (msg.what == 1) {
                mViewPagerAD.setCurrentItem(mViewPagerAD.getCurrentItem() + 1);
                handler.sendEmptyMessageDelayed(1, 3000);
            }

        }
    };
    //网络请求
    private Handler mHttpHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            //首页常用药品6条数据
            if (msg.what == 21) {
                Object o = msg.obj;
                if (o != null && o instanceof FirstPageDrugSixDataRoot) {
                    FirstPageDrugSixDataRoot root = (FirstPageDrugSixDataRoot) o;
                    mGridList = root.getRows();
                    mUseDrugAdapter.setmList(mGridList);
                    mUseDrugAdapter.notifyDataSetChanged();
                    mSwipeRefresh.setRefreshing(false);
                }

            } else if (msg.what == 200) {
                mSwipeRefresh.setRefreshing(false);
            } else if (msg.what == 201) {
                mSwipeRefresh.setRefreshing(false);
            }

            //首页资讯2条数据
            else if (msg.what == 22) {
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.UpdatedFirstPageTwoDataBean.Root) {
                    com.technology.yuyi.bean.UpdatedFirstPageTwoDataBean.Root root = (com.technology.yuyi.bean.UpdatedFirstPageTwoDataBean.Root) o;
                    mInforList = root.getRows();
                    mListViewAdapter.setList(mInforList);
                    mListViewAdapter.notifyDataSetChanged();
                    mSwipeRefresh.setRefreshing(false);
                }
                //资讯请求失败
            } else if (msg.what == 202) {
                mSwipeRefresh.setRefreshing(false);
            } else if (msg.what == 203) {
                mSwipeRefresh.setRefreshing(false);
            } else if (msg.what == 31) {//广告接口
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    mListAd = root.getResult().getRows();
                    AdAdapter.setmListImgAd(mListAd);
                    mViewPagerAD.setAdapter(AdAdapter);
                    AdAdapter.notifyDataSetChanged();
                    setADCircleImg();
                }
            } else if (msg.what == 219) {
                ToastUtils.myToast(getContext(), "广告数据错误");
            } else if (msg.what == 38) {//首页用户列表及默认数据
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.FirstPageUserDataBean.Root) ;
                com.technology.yuyi.bean.FirstPageUserDataBean.Root root = (com.technology.yuyi.bean.FirstPageUserDataBean.Root) o;
                mUserData.clear();

                if (root!=null&&root.getResult()!=null){
                    mUserData = root.getResult();
                    initUserMessage();//初始化用户的头像和昵称，绘制折线图
                }
                mAllUser_ll.removeAllViews();
                mSwipeRefresh.setRefreshing(false);
            } else if (msg.what == 231) {
                mSwipeRefresh.setRefreshing(false);
            } else if (msg.what == 232) {
                mSwipeRefresh.setRefreshing(false);
                ToastUtils.myToast(getContext(), "获用户列表失败");
            } else if (msg.what == 39) {//点击首页用户头像
                Object o = msg.obj;
                if (o != null && o instanceof com.technology.yuyi.bean.FirstPageClickUserBean.Root) {
                    MyDialog.stopDia();
                    com.technology.yuyi.bean.FirstPageClickUserBean.Root root = (com.technology.yuyi.bean.FirstPageClickUserBean.Root) o;
                    XdateNum.clear();
                    heightBloodData.clear();
                    lowBloodData.clear();
                    XTemdateNum.clear();
                    temData.clear();
                    int month = 0;
                    int day = 0;

                    List<com.technology.yuyi.bean.FirstPageClickUserBean.BloodpressureList> bloodlist = root.getResult().getBloodpressureList();
                    List<com.technology.yuyi.bean.FirstPageClickUserBean.TemperatureList> temlist = root.getResult().getTemperatureList();
                    //血压
                    if (bloodlist.size() != 0) {
                        for (int i = 0; i < bloodlist.size(); i++) {
                            try {
                                Date date = simpleDateFormat.parse(bloodlist.get(i).getCreateTimeString());
                                month = date.getMonth() + 1;
                                day = date.getDate();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String date = month + "月" + day + "日";
                            XdateNum.add(date);
                            heightBloodData.add(bloodlist.get(i).getSystolic());//高
                            lowBloodData.add(bloodlist.get(i).getDiastolic());
                        }

                    }

                    //填补血压日期
                    if (XdateNum.size() != 7) {
                        Calendar calendarBlood = Calendar.getInstance();
                        int dayNum = 7 - XdateNum.size();
                        if (XdateNum.size() == 0) {
                            for (int i = 0; i < dayNum; i++) {
                                int month2 = calendarBlood.get(Calendar.MONTH) + 1;
                                int day2 = calendarBlood.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XdateNum.add(date2);
                                calendarBlood.add(Calendar.DAY_OF_MONTH, 1);
                            }
                        } else {
                            for (int i = 0; i < dayNum; i++) {
                                calendarBlood.add(Calendar.DAY_OF_MONTH, 1);
                                int month2 = calendarBlood.get(Calendar.MONTH) + 1;
                                int day2 = calendarBlood.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XdateNum.add(date2);
                            }
                        }

                    }
                    // 体温
                    if (temlist.size() != 0) {
                        for (int i = 0; i < temlist.size(); i++) {
                            try {
                                Date date = simpleDateFormat.parse(temlist.get(i).getCreateTimeString());
                                month = date.getMonth() + 1;
                                day = date.getDate();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String date = month + "月" + day + "日";
                            XTemdateNum.add(date);
                            temData.add(temlist.get(i).getTemperaturet());//体温

                        }

                    }

                    //填补体温日期
                    if (XTemdateNum.size() != 7) {
                        Calendar calendarTem = Calendar.getInstance();
                        int dayNum = 7 - XTemdateNum.size();
                        //没有数据时，从当前日期开始
                        if (XTemdateNum.size() == 0) {
                            for (int i = 0; i < dayNum; i++) {
                                int month2 = calendarTem.get(Calendar.MONTH) + 1;
                                int day2 = calendarTem.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XTemdateNum.add(date2);
                                calendarTem.add(Calendar.DAY_OF_MONTH, 1);
                            }
                            //有数据时，从当前日期的下一天开始
                        } else {
                            for (int i = 0; i < dayNum; i++) {
                                calendarTem.add(Calendar.DAY_OF_MONTH, 1);
                                int month2 = calendarTem.get(Calendar.MONTH) + 1;
                                int day2 = calendarTem.get(Calendar.DAY_OF_MONTH);
                                String date2 = month2 + "月" + day2 + "日";
                                XTemdateNum.add(date2);
                            }
                        }

                    }
                    mBloodView.setInfo(YbloodNum, XdateNum, heightBloodData, lowBloodData);
                    mBloodView.invalidate();
                    mTemView.setTemInfo(YTemData, XTemdateNum, temData);
                    mTemView.invalidate();

                    //判断数据是否正常，设置文字图片提示
                    if (bloodlist.size() != 0 && temlist.size() != 0) {
                        checkBlood(bloodlist.get(bloodlist.size() - 1).getSystolic(), bloodlist.get(bloodlist.size() - 1).getDiastolic(), temlist.get(temlist.size() - 1).getTemperaturet());
                    } else if (bloodlist.size() == 0 && temlist.size() != 0) {
                        checkBlood(0, 0, temlist.get(temlist.size() - 1).getTemperaturet());
                    } else if (bloodlist.size() != 0 && temlist.size() == 0) {
                        checkBlood(bloodlist.get(bloodlist.size() - 1).getSystolic(), bloodlist.get(bloodlist.size() - 1).getDiastolic(), 0);
                    } else {
                        checkBlood(0, 0, 0);
                    }

                }
            } else if (msg.what == 233) {
                MyDialog.stopDia();
                ToastUtils.myToast(getContext(), "获取失败");
            } else if (msg.what == 234) {
                MyDialog.stopDia();
            }
        }
    };
    //用户头像，昵称外层的布局参数
    private LinearLayout.LayoutParams params;
    //用户头像布局参数
    private LinearLayout.LayoutParams paramsImg;
    //用户昵称布局参数
    private LinearLayout.LayoutParams paramsTV;
    //用户布局
    private LinearLayout linearLayout;
    //用户头像
    private RoundImageView roundImageView;
    //用户真实姓名
    private TextView textView;
    //添加用户布局参数
    private LinearLayout.LayoutParams addparams;
    //添加布局
    private LinearLayout addlinear;
    //添加图片
    private ImageView imageView;

    private String greenColor = "#25f368";
    private String grayColor = "#6a6a6a";
    private String redColor = "#ec2e2e";
    private ImageView mPromptImg;
    private TextView mPromptTv;
    private TextView mHeightBloodTv;
    private TextView mLowBloodTv;
    private TextView mTem;
    private SimpleDateFormat simpleDateFormat;
    private List<TextView> tvlist = new ArrayList<>();

    //确定弹框
    private AlertDialog.Builder mSureBuilder;
    private AlertDialog mSureAlertDialog;
    private View mSureAlertView;
    private TextView mPrompt;//去完善
    private TextView mPrompt_Cancel;//取消
    public FirstPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_page, container, false);
        initView(view);
        initLayout(view);
        initHttp();//请求网络数据
        checkPermission();//检测定位权限
        return view;
    }

    /**
     * 初始化网络数据接口
     */
    public void initHttp() {
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getFirstSixDrugData(mHttpHandler);//首页常用药品6条数据
        mHttptools.getFirstPageInformationTwoData(mHttpHandler, 0, 2);//首页资讯2条数据
        mHttptools.getAdData(mHttpHandler);//广告接口
        mHttptools.getFirstPageUserDataData(mHttpHandler, user.token);//首页用户数据u
    }

    /**
     * 出事化数据
     *
     * @param view
     */
    public void initView(View view) {
        //首页下拉刷新
        mSwipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.first_page_swiperefesh);
        mSwipeRefresh.setColorSchemeResources(R.color.color_delete, R.color.color_username, R.color.trans2);
        mSwipeRefresh.setRefreshing(true);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHttptools.getFirstSixDrugData(mHttpHandler);//首页常用药品6条数据
                mHttptools.getFirstPageInformationTwoData(mHttpHandler, 0, 2);//首页资讯2条数据
                tvlist.clear();
                heightBloodData.clear();
                lowBloodData.clear();
                XdateNum.clear();
                temData.clear();
                XTemdateNum.clear();
                mHttptools.getFirstPageUserDataData(mHttpHandler, user.token);//首页用户数据u
            }
        });
        //时间
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //跳转到定位页面
        mLocate_tv = (TextView) view.findViewById(R.id.tv_beijing);
        mLocate_tv.setOnClickListener(this);
        //搜索
        mEdit_rl = (RelativeLayout) view.findViewById(R.id.edit_rl);
        mEdit_rl.setOnClickListener(this);
        //scrollview
        mScrollRelative = (RelativeLayout) view.findViewById(R.id.search_rl);
        mScrollRelative.setFocusable(true);
        mScrollRelative.setFocusableInTouchMode(true);
        mScrollRelative.requestFocus();
        //医药商城进入
        drugmall_ll = (LinearLayout) view.findViewById(R.id.drugmall_ll);
        drugmall_ll.setOnClickListener(this);
        //常用药品设置adapter
        mGridview = (GridView) view.findViewById(R.id.firstpage_gridview_id);
        mUseDrugAdapter = new UseDrugGridViewAdapter(this.getContext(), mGridList);
        mGridview.setAdapter(mUseDrugAdapter);
        mGridview.setOnItemClickListener(this);
        //给资讯设置adapter
        mFirstPageListView = (InformationListView) view.findViewById(R.id.listview_firstpage);
        mListViewAdapter = new FirstPageListViewAdapter(this.getContext(), mInforList);
        mFirstPageListView.setAdapter(mListViewAdapter);
        mFirstPageListView.setOnItemClickListener(this);

        //广告viewpager
        mViewPagerAD = (ViewPager) view.findViewById(R.id.viewpager_title);
        AdAdapter = new ViewPagerAdAdapter(getContext(), mListAd);
        //初始化存放小圆点的容器(广告)
        mGroup = (ViewGroup) view.findViewById(R.id.viewGroup);

        //血压图和体温图viewpager
        initBloodData();//初始化血压数据
        initTemData();//初始化体温
        mViewPagerBlood = (ViewPager) view.findViewById(R.id.viewpager_blood_tem);
        mBloodView = new BloodView(this.getContext());
        mTemView = new TemView(this.getContext());
        mViewList.add(mBloodView);
        mViewList.add(mTemView);
        mBloodTemAdapter = new ViewPagerBloodTemAdapter(this.getContext(), mViewList);
        //初始化存放小圆点的容器(血压体温)
        mBloodGroup = (ViewGroup) view.findViewById(R.id.blood_viewGroup);
        setBloodTemImg();//血压和体温底部小图标初始化
        mViewPagerBlood.setAdapter(mBloodTemAdapter);
        mViewPagerBlood.setCurrentItem(0);
        mViewPagerBlood.addOnPageChangeListener(new BloodTemImpl(mBloodImageViewArr));
        //用户最后一条数据显示在标题那
        mPromptImg = (ImageView) view.findViewById(R.id.normal_btn_img);
        mPromptTv = (TextView) view.findViewById(R.id.normal_tv);
        mHeightBloodTv = (TextView) view.findViewById(R.id.heightPress_message_tv);
        mLowBloodTv = (TextView) view.findViewById(R.id.lowPress_message_tv);
        mTem = (TextView) view.findViewById(R.id.temperature_message_tv);

        //跳转资讯
        mInformation_rl = (RelativeLayout) view.findViewById(R.id.relative_information);
        mInformation_rl.setOnClickListener(this);
        //预约挂号
        mRegister_ll = (LinearLayout) view.findViewById(R.id.register_ll);
        mRegister_ll.setOnClickListener(this);
        //常用药品跳转
        mStaple_drug_rl = (RelativeLayout) view.findViewById(R.id.relative_drug);
        mStaple_drug_rl.setOnClickListener(this);

        //信息不完整弹框
        mSureBuilder = new AlertDialog.Builder(getContext());
        mSureAlertDialog = mSureBuilder.create();
        mSureAlertDialog.setCanceledOnTouchOutside(false);
        mSureAlertView = LayoutInflater.from(getContext()).inflate(R.layout.alert_sure, null);
        mSureAlertDialog.setView(mSureAlertView);
        //去完善、取消
        mPrompt = (TextView) mSureAlertView.findViewById(R.id.alert_sure_prompt);
        mPrompt.setOnClickListener(this);
        mPrompt_Cancel = (TextView) mSureAlertView.findViewById(R.id.alert_sure_cancel);
        mPrompt_Cancel.setOnClickListener(this);
    }

    /**
     * 初始化广告轮播图的小图标，以及设置viewpager滑动监听和自动轮播
     */
    public void setADCircleImg() {
        //只有轮播的图片张数不为0时，才执行下面内容
        if (mListAd.size() != 0) {
            //将小圆点根据条件添加到容器中
            mArrImageView = new ImageView[mListAd.size()];
            for (int i = 0; i < mListAd.size(); i++) {
                mCircleImg = new ImageView(this.getContext());
                //小圆点的布局
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginStart(14);
                //给小圆点设置布局
                mCircleImg.setLayoutParams(layoutParams);
                //为存放小圆点的数组赋值
                mArrImageView[i] = mCircleImg;
                //当轮播的图片为一张时，不用设置小圆圈
                if (mListAd.size() == 1) {
                    break;
                }
                //默认第一页为白色的小圆圈(前提必须是轮播的图片大于1张)
                if (i == 0) {
                    mCircleImg.setBackgroundResource(R.mipmap.select_ad);
                } else {
                    mCircleImg.setBackgroundResource(R.mipmap.no_select_ad);
                }
                //将每一个小圆点添加到容器中
                mGroup.addView(mCircleImg);
            }
            //当数据大于1条时，才可以滑动监听，才可以延迟发送消息进行轮播
            if (mListAd.size() > 1) {
                mViewPagerAD.addOnPageChangeListener(new AdListenerImpl(mArrImageView, handler, mViewPagerAD, mListAd, mSwipeRefresh));
                //开始轮播效果
                handler.sendEmptyMessageDelayed(1, 3000);
            }
        }
    }

    /**
     * 血压和体温底部小图标初始化，viewpager设置监听
     */
    public void setBloodTemImg() {
        //只有轮播的图片张数不为0时，才执行下面内容
        if (mViewList.size() != 0) {
            //将小圆点根据条件添加到容器中
            mBloodImageViewArr = new ImageView[mViewList.size()];
            for (int i = 0; i < mViewList.size(); i++) {
                mCircleImg = new ImageView(this.getContext());
                //小圆点的布局
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMarginStart(14);
                //给小圆点设置布局
                mCircleImg.setLayoutParams(layoutParams);

                //为存放小圆点的数组赋值
                mBloodImageViewArr[i] = mCircleImg;
                //当轮播的图片为一张时，不用设置小圆圈
                if (mViewList.size() == 1) {
                    break;
                }
                //默认第一页为白色的小圆圈(前提必须是轮播的图片大于1张)
                if (i == 0) {
                    mCircleImg.setBackgroundResource(R.mipmap.select_ad);
                } else {
                    mCircleImg.setBackgroundResource(R.mipmap.no_select_ad);
                }
                //将每一个小圆点添加到容器中
                mBloodGroup.addView(mCircleImg);
            }

        }
    }

    /**
     * 初始化血压折线图数据
     */
    public void initBloodData() {
        //y轴血压数据
        for (int i = 40; i <= 180; i += 20) {
            YbloodNum.add(i);
        }
    }

    /**
     * 初始化体温折线图数据
     */

    public void initTemData() {
        for (int i = 35; i < 43; i++) {
            YTemData.add(i);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mInformation_rl.getId()) {//资讯跳转
            startActivity(new Intent(this.getContext(), InformationActivity.class));
        } else if (id == mRegister_ll.getId()) {//预约挂号
            startActivity(new Intent(this.getContext(), AppointmentActivity.class));
        } else if (id == R.id.drugmall_ll) {//医药商城
            Intent intent = new Intent();
            intent.setClass(getActivity(), MS_home_Activity.class);
            startActivity(intent);
        } else if (id == mEdit_rl.getId()) {//跳转到搜索页
            Intent intent = new Intent(this.getContext(), SearchActivity.class);
            intent.putExtra("type", "drug");
            startActivity(intent);

        } else if (id == mStaple_drug_rl.getId()) { //跳转到常用药品
            Intent intent = new Intent(this.getContext(), MS_allkinds_activity.class);
            intent.putExtra("type", 2);
            intent.putExtra("name", "常用药品");
            intent.putExtra("Cid", 1);
            startActivity(intent);
        } else if (id == mLocate_tv.getId()) {//跳转到定位页面
            Intent intent = new Intent(this.getActivity(), GaoDeLocateActivity.class);
            intent.putExtra("isNull", mLocate_tv.getText());
            startActivityForResult(intent, 66);
        }else if (id == mPrompt.getId()) {//去完善
            startActivity(new Intent(getContext(),UserEditorActivity.class));
            mSureAlertDialog.dismiss();
        } else if (id == mPrompt_Cancel.getId()) {
            mSureAlertDialog.dismiss();
        }
    }

    //获取到的定位页面传过来的城市
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 66 && resultCode == 1) {//点击定位页面的某个城市，传过来的城市
            mLocate_tv.setText(data.getStringExtra("city"));
        } else if (requestCode == 66 && resultCode == 2) {//点击返回按钮，传过来的信息
            mLocate_tv.setText(data.getStringExtra("cityResult"));
        } else if (requestCode == 66 && resultCode == 3) {//点击搜索到的城市，传过来的城市
            mLocate_tv.setText(data.getStringExtra("citySearchResult"));
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent == mGridview) {//跳转到药品详情页
            Intent intent = new Intent(getContext(), MS_drugInfo_activity.class);
            intent.putExtra("id", mGridList.get(position).getId());
            startActivity(intent);
        } else if (parent == mFirstPageListView) {//跳转到资讯详情页
            Intent intent = new Intent(getContext(), InformationDetailsActivity.class);
            intent.putExtra("id", mInforList.get(position).getId());
            intent.putExtra("type", "information");
            startActivity(intent);
        }
    }

    //初始化高德定位数据
    public void gaoDeMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this.getContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取最近3s内精度最高的一次定位结果：
        //mLocationOption.setOnceLocationLatest(true);
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(6000);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        mLocationOption.setWifiActiveScan(false);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //只定位一次,如果设置只定位一次的话， mLocationOption.setInterval（）这个方法就不会执行了，也就是即使隔6秒也不会重新定位
        mLocationOption.setOnceLocation(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        Log.e("MainActivity当前线程id=", Thread.currentThread().getId() + "");

    }

    /**
     * 检查定位权限
     */
    public void checkPermission() {
        //sdk版本>=23时，
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION);
            //如果没有授权定位权限
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //请求授权，点击允许或者拒绝时会回调onRequestPermissionsResult（），
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATE_CODE);//位置信息
                return;
                //如果已经授权，执行业务逻辑
            } else {
                gaoDeMap();
                Toast.makeText(this.getContext(), "定位授权成功", Toast.LENGTH_SHORT).show();
            }
            //版本小于23时，执行业务逻辑
        } else {
            Toast.makeText(this.getContext(), "版本小于23", Toast.LENGTH_SHORT).show();
            gaoDeMap();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATE_CODE:
                //点击了允许
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    Toast.makeText(this.getContext(), "允许定位", Toast.LENGTH_SHORT).show();
                    gaoDeMap();
                    //点击了拒绝
                } else {
                    // Permission Denied
                    mLocate_tv.setText("未定位");
                    Toast.makeText(this.getContext(), "无法获取定位权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //定位回调接口
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //可在其中解析aMapLocation获取相应内容。
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                user.Latitude = aMapLocation.getLatitude();//获取纬度
                user.Longitude = aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                aMapLocation.getAoiName();//获取当前定位点的AOI信息
                aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                aMapLocation.getFloor();//获取当前室内定位的楼层
                aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                //获取定位时间
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);
                Log.e("街道信息当前线程id=", Thread.currentThread().getId() + "");
                Log.e("当前城市信息：", aMapLocation.getCity());
                Log.e("当前城区信息：", aMapLocation.getDistrict());
                mLocate_tv.setText(aMapLocation.getDistrict());
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }


    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue
     * @return
     */
    public int dip2px(int dpValue) {
        DisplayMetrics mDisplayMetrics = getResources().getDisplayMetrics();
        float scale = mDisplayMetrics.density;
        return (int) (dpValue * scale + 0.5f);
    }

    //初始化首页用户布局
    public void initLayout(View view) {
        //首页全部用户布局
        mAllUser_ll = (LinearLayout) view.findViewById(R.id.user_ll);
        //用户头像，昵称外层的布局参数
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(dip2px(10), 0, dip2px(10), 0);
        //用户头像布局参数
        paramsImg = new LinearLayout.LayoutParams(dip2px(37), dip2px(37));
        //用户昵称布局参数
        paramsTV = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTV.setMargins(0, dip2px(5), 0, 0);
        //添加按钮的外层布局参数
        addparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addparams.setMargins(dip2px(10), 0, dip2px(10), 0);
        //添加布局
        addlinear = new LinearLayout(this.getContext());
        //添加图片
        imageView = new ImageView(this.getContext());
    }

    /**
     * 初始化用户数据
     */
    public void initUserMessage() {
        for (int i = 0; i < mUserData.size(); i++) {
            final int k = i;
            //用户布局
            linearLayout = new LinearLayout(this.getContext());
            linearLayout.setId(View.generateViewId());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setLayoutParams(params);

            //用户头像
            roundImageView = new RoundImageView(this.getContext());
            Picasso.with(getContext()).load(UrlTools.BASE + mUserData.get(i).getAvatar()).error(R.mipmap.error_small).into(roundImageView);
            roundImageView.setLayoutParams(paramsImg);
            Picasso.with(getContext()).load(UrlTools.BASE + mUserData.get(i).getAvatar()).error(R.mipmap.error_small).into(roundImageView);

            //用户真实姓名
            textView = new TextView(this.getContext());
            textView.setId(View.generateViewId());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            if (i == 0) {
                textView.setTextColor(Color.parseColor(greenColor));
            } else {
                textView.setTextColor(Color.parseColor(grayColor));
            }

            textView.setLayoutParams(paramsTV);
            tvlist.add(textView);
            if (mUserData.get(i).getTrueName().length() > 3) {
                textView.setText(mUserData.get(i).getTrueName().toCharArray(), 0, 3);
            } else {
                textView.setText(mUserData.get(i).getTrueName());
            }

            linearLayout.addView(roundImageView);
            linearLayout.addView(textView);
            mAllUser_ll.addView(linearLayout);

            //点击用户头像
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyDialog.showDialog(getContext());
                    mHttptools.getClickUserDataData(mHttpHandler, user.token, mUserData.get(k).getId());
                    //点击头像时，文字变换颜色
                    for (int i = 0; i < tvlist.size(); i++) {
                        if (k == i) {
                            tvlist.get(k).setTextColor(Color.parseColor(greenColor));
                        } else {
                            tvlist.get(i).setTextColor(Color.parseColor(grayColor));
                        }
                    }

                }
            });
        }
        //首页添加用户按钮
        if (mUserData.size() < 6) {
            //添加布局
            addlinear = new LinearLayout(this.getContext());
            addlinear.setId(View.generateViewId());
            addlinear.setOrientation(LinearLayout.VERTICAL);
            addlinear.setLayoutParams(addparams);
            addlinear.setGravity(Gravity.CENTER);

            //添加图片
            imageView = new ImageView(this.getContext());
            imageView.setImageResource(R.mipmap.add_icon1);
            imageView.setLayoutParams(paramsImg);

            addlinear.addView(imageView);
            mAllUser_ll.addView(addlinear);
            //点击添加按钮
            addlinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            //判断主用户信息是否完善
                    if (mUserData.get(0).getAge() == 0 || mUserData.get(0).getTrueName().equals("")) {
                        mSureAlertDialog.show();

                    } else {
                        Intent intent = new Intent(getContext(), AddFamilyUserActivity.class);
                        intent.putExtra("type", "0");
                        startActivity(intent);
                    }
                }
            });
        }

        if (mUserData.size() != 0) {
            //为默认用户初始化数据
            List<BloodpressureList> listBlood = mUserData.get(0).getBloodpressureList();
            List<TemperatureList> listTem = mUserData.get(0).getTemperatureList();
            //默认用户血压数据
            if (listBlood.size() != 0) {
                int month = 0;
                int day = 0;
                for (int i = 0; i < listBlood.size(); i++) {
                    heightBloodData.add(listBlood.get(i).getSystolic());//高
                    lowBloodData.add(listBlood.get(i).getDiastolic());
                    try {
                        Date date = simpleDateFormat.parse(listBlood.get(i).getCreateTimeString());
                        month = date.getMonth() + 1;
                        day = date.getDate();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String date = month + "月" + day + "日";
                    XdateNum.add(date);
                }

            }


            //填补血压日期
            if (XdateNum.size() != 7) {
                int dayNum = 7 - XdateNum.size();
                Calendar calendarBlood = Calendar.getInstance();

                if (XdateNum.size() == 0) {
                    for (int i = 0; i < dayNum; i++) {
                        int month2 = calendarBlood.get(Calendar.MONTH) + 1;
                        int day2 = calendarBlood.get(Calendar.DAY_OF_MONTH);
                        String date2 = month2 + "月" + day2 + "日";
                        XdateNum.add(date2);
                        calendarBlood.add(Calendar.DAY_OF_MONTH, 1);
                    }
                } else {
                    for (int i = 0; i < dayNum; i++) {
                        calendarBlood.add(Calendar.DAY_OF_MONTH, 1);
                        int month2 = calendarBlood.get(Calendar.MONTH) + 1;
                        int day2 = calendarBlood.get(Calendar.DAY_OF_MONTH);
                        String date2 = month2 + "月" + day2 + "日";
                        XdateNum.add(date2);
                    }
                }

            }


            //默认用户体温数据
            if (listTem.size() != 0) {
                int month = 0;
                int day = 0;
                for (int i = 0; i < listTem.size(); i++) {
                    temData.add(listTem.get(i).getTemperaturet());
                    try {
                        Date date = simpleDateFormat.parse(listTem.get(i).getCreateTimeString());
                        month = date.getMonth() + 1;
                        day = date.getDate();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String date = month + "月" + day + "日";
                    XTemdateNum.add(date);
                }
            }


            //填补体温日期
            if (XTemdateNum.size() != 7) {
                Calendar calendarTem = Calendar.getInstance();
                int dayNum = 7 - XTemdateNum.size();
                //如果日期数据为0，日期从当天开始
                if (XTemdateNum.size() == 0) {
                    for (int i = 0; i < dayNum; i++) {
                        int month2 = calendarTem.get(Calendar.MONTH) + 1;
                        int day2 = calendarTem.get(Calendar.DAY_OF_MONTH);
                        String date2 = month2 + "月" + day2 + "日";
                        XTemdateNum.add(date2);
                        calendarTem.add(Calendar.DAY_OF_MONTH, 1);
                    }
                    //如果日期数据不为0，日期从当天的下一天开始
                } else {
                    for (int i = 0; i < dayNum; i++) {
                        calendarTem.add(Calendar.DAY_OF_MONTH, 1);
                        int month2 = calendarTem.get(Calendar.MONTH) + 1;
                        int day2 = calendarTem.get(Calendar.DAY_OF_MONTH);
                        String date2 = month2 + "月" + day2 + "日";
                        XTemdateNum.add(date2);
                    }
                }

            }
            //判断数据是否正常，设置文字图片提示
            if (listBlood.size() != 0 && listTem.size() != 0) {
                checkBlood(listBlood.get(listBlood.size() - 1).getSystolic(), listBlood.get(listBlood.size() - 1).getDiastolic(), listTem.get(listTem.size() - 1).getTemperaturet());
            } else if (listBlood.size() == 0 && listTem.size() != 0) {
                checkBlood(0, 0, listTem.get(listTem.size() - 1).getTemperaturet());
            } else if (listBlood.size() != 0 && listTem.size() == 0) {
                checkBlood(listBlood.get(listBlood.size() - 1).getSystolic(), listBlood.get(listBlood.size() - 1).getDiastolic(), 0);
            } else {
                checkBlood(0, 0, 0);
            }
        }
        mBloodView.setInfo(YbloodNum, XdateNum, heightBloodData, lowBloodData);
        mBloodView.invalidate();
        mTemView.setTemInfo(YTemData, XTemdateNum, temData);
        mTemView.invalidate();


    }

    /**
     * 判断血压,体温设置提示
     * height 高压
     * low 低压
     * tem 体温
     */
    public void checkBlood(int height, int low, float tem) {

        if (height == 0 && low == 0 && tem == 0) {
            mPromptImg.setImageResource(R.mipmap.normal);
            mPromptTv.setText("待测");
            mPromptTv.setTextColor(Color.parseColor(grayColor));
        }
        //显示不正常
        else if (height > 139 || height < 90 || low > 89 || low < 60 || tem < 36 || tem > 37) {

            mPromptImg.setImageResource(R.mipmap.normal_error);
            mPromptTv.setText("不正常");
            mPromptTv.setTextColor(Color.parseColor(redColor));

        } else {
            mPromptImg.setImageResource(R.mipmap.normal);
            mPromptTv.setText("正常");
            mPromptTv.setTextColor(Color.parseColor(grayColor));
        }
        mHeightBloodTv.setText(height + "");
        mLowBloodTv.setText(low + "");
        mTem.setText(tem + "°C");
    }
}
