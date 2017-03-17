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
import com.technology.yuyi.adapter.FirstPageListViewAdapter;
import com.technology.yuyi.adapter.UseDrugGridViewAdapter;
import com.technology.yuyi.adapter.ViewPagerAdAdapter;
import com.technology.yuyi.adapter.ViewPagerBloodTemAdapter;
import com.technology.yuyi.bean.FirstPageDrugSixData;
import com.technology.yuyi.bean.FirstPageDrugSixDataRoot;
import com.technology.yuyi.bean.FirstPageInformationTwoData;
import com.technology.yuyi.bean.FirstPageInformationTwoDataRoot;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.BloodView;
import com.technology.yuyi.myview.InformationListView;
import com.technology.yuyi.myview.RoundImageView;
import com.technology.yuyi.myview.TemView;
import com.technology.yuyi.viewpager.impl.AdListenerImpl;
import com.technology.yuyi.viewpager.impl.BloodTemImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private List<FirstPageInformationTwoData> mInforList = new ArrayList<>();

    private GridView mGridview;//常用药品Gridview
    private UseDrugGridViewAdapter mUseDrugAdapter;//常用药品adapter
    private List<FirstPageDrugSixData> mGridList = new ArrayList<>();

    private ViewPager mViewPagerAD;
    private List<Integer> mListAd = new ArrayList<>();//广告图片集合
    private ImageView mCircleImg;
    private ImageView[] mArrImageView;//存放广告小圆点的数组
    private ViewGroup mGroup;//存放小圆点容器

    private ViewPager mViewPagerBlood;
    private ViewPagerBloodTemAdapter mBloodTemAdapter;
    private BloodView mBloodView;
    private TemView mTemView;
    private LinearLayout drugmall_ll;
    private ArrayList<Integer> YbloodNum = new ArrayList<>();//y轴血压数据
    private ArrayList<Integer> XdateNum = new ArrayList<>();//x轴日期数据
    private ArrayList<Integer> heightBloodData = new ArrayList<>();  //血压高压数据
    private ArrayList<Integer> lowBloodData = new ArrayList<>();//血压低压数据

    private ArrayList<Integer> YTemData = new ArrayList<>();//Y轴温度
    private ArrayList<Integer> XTemdateNum = new ArrayList<>();  //x轴日期数据
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
    private ArrayList mUserData = new ArrayList();

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
                //首页资讯2条数据
            } else if (msg.what == 22) {
                Object o = msg.obj;
                if (o != null && o instanceof FirstPageInformationTwoDataRoot) {
                    FirstPageInformationTwoDataRoot root = (FirstPageInformationTwoDataRoot) o;
                    mInforList = root.getRows();
                    mListViewAdapter.setList(mInforList);
                    mListViewAdapter.notifyDataSetChanged();
                    mSwipeRefresh.setRefreshing(false);
                }
            }else if (msg.what==101){
                mSwipeRefresh.setRefreshing(false);
            }
        }
    };

    public FirstPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_page, container, false);
        initView(view);
        initHttp();//请求网络数据
        initUserMessage();//初始化用户的头像和昵称
        checkPermission();//检测定位权限
        return view;
    }

    /**
     * 初始化网络数据接口
     */
    public void initHttp() {
        mHttptools = HttpTools.getHttpToolsInstance();
        mHttptools.getFirstSixDrugData(mHttpHandler);//首页常用药品6条数据
        mHttptools.getFirstPageInformationTwoData(mHttpHandler,0,2);//首页资讯2条数据
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
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                        mHttptools.getFirstSixDrugData(mHttpHandler);//首页常用药品6条数据
                        mHttptools.getFirstPageInformationTwoData(mHttpHandler,0,2);//首页资讯2条数据
            }
        });
        //首页全部用户布局
        mAllUser_ll = (LinearLayout) view.findViewById(R.id.user_ll);
        mUserData.add(1);
        mUserData.add(2);
        mUserData.add(3);
        mUserData.add(4);
        mUserData.add(5);
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
        //初始化存放小圆点的容器与viewpager
        mGroup = (ViewGroup) view.findViewById(R.id.viewGroup);
        mListAd.add(R.mipmap.item01);
        mListAd.add(R.mipmap.item02);
        mListAd.add(R.mipmap.item03);
        mListAd.add(R.mipmap.item04);
        mListAd.add(R.mipmap.item05);
        //初始化广告轮播图的小图标，以及设置viewpager滑动监听和自动轮播
        setADCircleImg();

        //血压图和体温图viewpager
        initBloodData();//初始化血压数据
        initTemData();//初始化体温
        mViewPagerBlood = (ViewPager) view.findViewById(R.id.viewpager_blood_tem);
        mBloodView = new BloodView(this.getContext());
        mBloodView.setInfo(YbloodNum, XdateNum, heightBloodData, lowBloodData);
        mTemView = new TemView(this.getContext());
        mTemView.setTemInfo(YTemData, XTemdateNum, temData);


        mViewList.add(mBloodView);
        mViewList.add(mTemView);
        mBloodTemAdapter = new ViewPagerBloodTemAdapter(this.getContext(), mViewList);
        mBloodGroup = (ViewGroup) view.findViewById(R.id.blood_viewGroup);
        setBloodTemImg();

        //跳转资讯
        mInformation_rl = (RelativeLayout) view.findViewById(R.id.relative_information);
        mInformation_rl.setOnClickListener(this);
        //预约挂号
        mRegister_ll = (LinearLayout) view.findViewById(R.id.register_ll);
        mRegister_ll.setOnClickListener(this);
        //常用药品跳转
        mStaple_drug_rl = (RelativeLayout) view.findViewById(R.id.relative_drug);
        mStaple_drug_rl.setOnClickListener(this);
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
            mViewPagerAD.setAdapter(new ViewPagerAdAdapter(this.getContext(), mListAd));
            mViewPagerAD.setCurrentItem(0);
            //当数据大于1条时，才可以滑动监听，才可以延迟发送消息进行轮播
            if (mListAd.size() > 1) {
                mViewPagerAD.addOnPageChangeListener(new AdListenerImpl(mArrImageView, handler, mViewPagerAD, mListAd, mSwipeRefresh));
                //开始轮播效果
                mViewPagerAD.setFocusable(true);
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

            mViewPagerBlood.setAdapter(mBloodTemAdapter);
            mViewPagerBlood.setCurrentItem(0);
            //当数据大于1条时，才可以滑动监听
            if (mViewList.size() > 1) {
                mViewPagerBlood.addOnPageChangeListener(new BloodTemImpl(mBloodImageViewArr));
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

        //x轴日期数据
        for (int i = 1; i <= 7; i += 1) {
            XdateNum.add(i);
        }

        //血压高压数据
        for (int i = 1; i <= 7; i += 1) {
            int data = (int) (Math.random() * 100) + 50;
            heightBloodData.add(data);
            Log.e("高压血压数据", data + "");
        }

        //血压低压数据
        for (int i = 1; i <= 7; i += 1) {
            int data = (int) (Math.random() * 20) + 50;
            lowBloodData.add(data);
            Log.e("低压血压数据", data + "");
        }
    }

    /**
     * 初始化体温折线图数据
     */

    public void initTemData() {

        for (int i = 35; i < 43; i++) {
            YTemData.add(i);
        }

        for (int i = 1; i <= 7; i += 1) {
            XTemdateNum.add(i);
        }


        temData.add(40.0f);
        temData.add(36.5f);
        temData.add(42f);
        temData.add(38.7f);
        temData.add(36.9f);
        temData.add(40.5f);
        temData.add(35.7f);

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
            intent.putExtra("hint", "搜索药品");
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

    /**
     * 初始化用户数据
     */
    public void initUserMessage() {
        //用户头像，昵称外层的布局参数
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMarginEnd(dip2px(20));
        //用户头像布局参数
        LinearLayout.LayoutParams paramsImg = new LinearLayout.LayoutParams(dip2px(37), dip2px(37));
        //用户昵称布局参数
        LinearLayout.LayoutParams paramsTV = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsTV.setMargins(0, dip2px(5), 0, 0);

        for (int i = 0; i < mUserData.size(); i++) {
            final int k = i;
            //用户布局
            LinearLayout linearLayout = new LinearLayout(this.getContext());
            linearLayout.setId(View.generateViewId());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setLayoutParams(params);

            //用户头像
            RoundImageView roundImageView = new RoundImageView(this.getContext());
            //roundImageView.setImageResource(R.mipmap.logo);
            Picasso.with(getContext()).load(R.mipmap.logo).into(roundImageView);
            roundImageView.setLayoutParams(paramsImg);

            //用户昵称

            TextView textView = new TextView(this.getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            textView.setTextColor(Color.parseColor("#25f368"));
            textView.setText("用户" + i);
            textView.setLayoutParams(paramsTV);

            linearLayout.addView(roundImageView);
            linearLayout.addView(textView);
            mAllUser_ll.addView(linearLayout);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), mUserData.get(k) + "", Toast.LENGTH_SHORT).show();
                    heightBloodData.clear();
                    heightBloodData.add(120);
                    heightBloodData.add(80);
                    heightBloodData.add(90);
                    heightBloodData.add(75);
                    heightBloodData.add(130);
                    heightBloodData.add(110);
                    heightBloodData.add(95);
                    lowBloodData.clear();
                    lowBloodData.add(50);
                    lowBloodData.add(58);
                    lowBloodData.add(59);
                    lowBloodData.add(50);
                    lowBloodData.add(51);
                    lowBloodData.add(52);
                    lowBloodData.add(58);
                    mBloodView.setInfo(YbloodNum, XdateNum, heightBloodData, lowBloodData);
                    mBloodView.invalidate();
                }
            });
        }

        //首页添加用户按钮
        if (mUserData.size() < 6) {
            //添加按钮的外层布局参数
            LinearLayout.LayoutParams addparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //添加布局
            LinearLayout addlinear = new LinearLayout(this.getContext());
            addlinear.setId(View.generateViewId());
            addlinear.setOrientation(LinearLayout.VERTICAL);
            addlinear.setLayoutParams(addparams);
            addlinear.setGravity(Gravity.CENTER);

            //添加图片
            ImageView imageView = new ImageView(this.getContext());
            imageView.setImageResource(R.mipmap.add_icon1);
            imageView.setLayoutParams(paramsImg);

            addlinear.addView(imageView);
            mAllUser_ll.addView(addlinear);
            //点击添加按钮
            addlinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Intent intent=  new Intent(getContext(), AddFamilyUserActivity.class);
                    intent.putExtra("title","添加家庭用户");
                    startActivity(intent);
                }
            });
        }


    }
}
