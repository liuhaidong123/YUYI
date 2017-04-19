package com.technology.yuyi.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.technology.yuyi.DBSqlite.DBHelper;
import com.technology.yuyi.GaoDe.adapter.ResultCityAdapter;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.City;
import com.technology.yuyi.hide.input.keyboard.HideKeyboardUtils;
import com.technology.yuyi.lhd.utils.PingYinUtil;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.MyLetterListView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class GaoDeLocateActivity extends AppCompatActivity implements TextWatcher, AbsListView.OnScrollListener, View.OnClickListener, AMapLocationListener {
    private RelativeLayout mRelative;
    private ImageView mBack;
    private ListView mAllCity_ListView;//定位页面所有城市列表ListView
    private AllCityListAdapter mAllCityAdapter;

    private ListView mSearch_city_result_listview;//搜索城市时的城市列表ListView
    private ResultCityAdapter mResultCityAdapter;

    private TextView mOverlay;//触摸右侧拼音时，弹出的字母提示
    private MyLetterListView mLetterListView;//A-Z listview

    private HashMap<String, Integer> mAlphaIndexer = new HashMap<String, Integer>();//当前汉语拼音首字母和与之对应的列表位
    private String[] mSections;//存放汉语拼音首字母

    private ArrayList<City> mAllCity_list = new ArrayList<>();//所有城市集合
    private ArrayList<City> mSearch_city_result_list = new ArrayList<>();//搜索城市结果集合

    private EditText mEditText;//输入编辑框
    private TextView mSearch_result_tv;//搜索城市时，搜索的结果提示
    private TextView tv_city;
    private boolean mReady;//初始化汉语拼音首字母弹出提示框的标志
    private boolean isScroll = false;//触摸右侧拼音的监听事件的标志
    private Handler handler;
    private OverlayThread overlayThread;
    private final int LOCATE_CODE = 123;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    //将全部城市按拼音排序时所需要的对象
    private Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City o1, City o2) {
            String a = o1.getPinyi().substring(0, 1);
            String b = o2.getPinyi().substring(0, 1);
            //如果前面的大那么返回1，后面的大返回-1；此位置相同，继续比较下一位，直到最后一位，如果都相同的话，就返回0；
            int flag = a.compareTo(b);
            return flag;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gao_de_locate);
        initData();
        checkPermission();//检测定位权限
    }

    //初始化数据
    public void initData() {
        mBack = (ImageView) findViewById(R.id.locate_back);
        mBack.setOnClickListener(this);

        tv_city = (TextView) findViewById(R.id.tv_city);//定位的信息
        getAllCityList();//初始化定位页面的所有城市数据
        initOverlay();
        //initLoc();//初始化定位
        mRelative = (RelativeLayout) findViewById(R.id.locare_rl);

        mAllCity_ListView = (ListView) findViewById(R.id.all_city_listview);//定位页面所有城市列表ListView
        mAllCityAdapter = new AllCityListAdapter(this, mAllCity_list);
        mAllCity_ListView.setAdapter(mAllCityAdapter);
        mAllCity_ListView.setOnScrollListener(this);//滚动监听
        //点击某个城市，将这个城市返回到首页
        mAllCity_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.name);
                Toast.makeText(GaoDeLocateActivity.this, "" + textView.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                intent.putExtra("city", textView.getText());
                setResult(1, intent);
                finish();
            }
        });

        mSearch_city_result_listview = (ListView) findViewById(R.id.search_listview);//搜索城市时的城市列表ListView
        mResultCityAdapter = new ResultCityAdapter(mSearch_city_result_list, this);//搜索城市Adapter
        mSearch_city_result_listview.setAdapter(mResultCityAdapter);//设置搜索城市Adapter
        mSearch_city_result_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.name);
                Toast.makeText(GaoDeLocateActivity.this, "" + textView.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                intent.putExtra("citySearchResult", textView.getText());
                setResult(3, intent);
                finish();
            }
        });

        mSearch_result_tv = (TextView) findViewById(R.id.search_prompt_tv);//搜索城市时，搜索的结果提示
        mEditText = (EditText) findViewById(R.id.loacte_edit_box);//输入编辑框
        mEditText.addTextChangedListener(this);//监听搜索框变化

        mLetterListView = (MyLetterListView) findViewById(R.id.MyLetterListView01);//A-Z listview
        mLetterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());

        handler = new Handler();
        overlayThread = new OverlayThread();


    }

    /**
     * 从city表中获取定位页面所有城市集合（包含城市名称和城市拼音）
     */
    private void getAllCityList() {
        DBHelper dbHelper = new DBHelper(this);
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from city", null);
            City city;
            while (cursor.moveToNext()) {
                city = new City(cursor.getString(1), cursor.getString(2));
                mAllCity_list.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将集合中的城市排序
        Collections.sort(mAllCity_list, comparator);
    }

    /**
     * 获取搜索时的城市，并排序
     * 参数：EditText输入的内容
     *
     * @return
     */
    private void getSearchResultCityList(String keyword) {
        DBHelper dbHelper = new DBHelper(this);
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from city where name like \"%" + keyword + "%\" or pinyin like \"%" + keyword + "%\"", null);
            City city;
            while (cursor.moveToNext()) {
                city = new City(cursor.getString(1), cursor.getString(2));
                mSearch_city_result_list.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(mSearch_city_result_list, comparator);
    }

    // 获得汉语拼音首字母
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式，判断首字母是否是英文字母
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase();
        } else {
            return "#";
        }
    }

    //初始化汉语拼音首字母弹出提示框
    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        mOverlay = (TextView) inflater.inflate(R.layout.overlay, null);
        mOverlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(mOverlay, lp);
    }


    //触摸右侧拼音的监听事件
    public class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(String s) {
            isScroll = false;
            if (mAlphaIndexer.get(s) != null) {
                int position = mAlphaIndexer.get(s);
                mAllCity_ListView.setSelection(position);
                mOverlay.setText(s);
                mOverlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    // 设置overlay不可见
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            mOverlay.setVisibility(View.GONE);
        }
    }


    /**
     * 定位页面所有城市adapter
     */
    public class AllCityListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<City> list = new ArrayList<City>();//定位页面所有的城市

        public AllCityListAdapter(Context context, List<City> list) {
            this.context = context;
            this.list = list;
            this.inflater = LayoutInflater.from(this.context);
            mSections = new String[list.size()];

            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = getAlpha(list.get(i).getPinyi());
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? getAlpha(list.get(i - 1).getPinyi()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String name = getAlpha(list.get(i).getPinyi());
                    mAlphaIndexer.put(name, i);
                    mSections[i] = name;
                }
            }
        }


        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.gs_search_result_listview_item, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.name.setText(list.get(position).getName());
            String currentStr = getAlpha(list.get(position).getPinyi());
            String previewStr = (position - 1) >= 0 ? getAlpha(list.get(position - 1).getPinyi()) : " ";
            if (!previewStr.equals(currentStr)) {
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(currentStr);
            } else {
                holder.alpha.setVisibility(View.GONE);
            }
            return convertView;
        }
    }

    class ViewHolder {
        TextView alpha;// 首字母标题
        TextView name;// 城市名字
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {
            Intent intent = getIntent();
            String cityName = intent.getStringExtra("isNull");
            if (cityName.equals("未定位")) {
                intent.putExtra("cityResult", tv_city.getText());
            } else {
                if (cityName.equals(tv_city.getText())) {
                    intent.putExtra("cityResult", tv_city.getText());
                } else {
                    intent.putExtra("cityResult", cityName);
                }
            }
            setResult(2, intent);
            finish();
        }
    }

    //搜索框监听
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    //当搜索框变化时，回调这个监听方法
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //搜索框中没有文字时
        if (s.toString() == null || "".equals(s.toString())) {
            mLetterListView.setVisibility(View.VISIBLE);//右侧拼音显示
            mAllCity_ListView.setVisibility(View.VISIBLE);//定位页面中的所有城市数据显示
            mRelative.setVisibility(View.VISIBLE);
            mSearch_city_result_listview.setVisibility(View.GONE);//搜索的城市数据隐藏
            mSearch_result_tv.setVisibility(View.GONE);//搜索结果提示隐藏
            // 搜索框中有文字时
        } else {
            mSearch_city_result_list.clear();//先将以前搜索过的数据清空
            mLetterListView.setVisibility(View.GONE);//右侧拼音隐藏
            mAllCity_ListView.setVisibility(View.GONE);//定位页面中所有城市数据隐藏
            mRelative.setVisibility(View.GONE);
            getSearchResultCityList(s.toString());////根据搜索框的数据，获取搜索到的城市集合
            //没有搜索到城市
            if (mSearch_city_result_list.size() <= 0) {
                mSearch_result_tv.setVisibility(View.VISIBLE);//textview显示(没有搜索到城市)
                mSearch_city_result_listview.setVisibility(View.GONE);//隐藏搜索时的listview
                //搜索到城市
            } else {
                mSearch_result_tv.setVisibility(View.GONE);//textview隐藏
                mSearch_city_result_listview.setVisibility(View.VISIBLE);//显示搜索到城市的listview显示
                mResultCityAdapter.setmResultList(mSearch_city_result_list);//给adapter设置搜索到城市集合
                mResultCityAdapter.notifyDataSetChanged();//更新适配器
            }
        }
    }

    //搜索框监听
    @Override
    public void afterTextChanged(Editable s) {

    }

    //定位页面ListView滚动时的监听
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String text;
            String pinyin = mAllCity_list.get(firstVisibleItem).getPinyi();
            text = PingYinUtil.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
            mOverlay.setText(text);
            mOverlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟一秒后执行，让overlay为不可见
            handler.postDelayed(overlayThread, 1000);
        }
    }

    /**
     * 检查定位权限
     */
    public void checkPermission() {
        //sdk版本>=23时，
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            //如果读取电话权限没有授权
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //请求授权， 点击允许或者拒绝时会回调onRequestPermissionsResult（），
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATE_CODE);//位置信息
                return;
                //如果已经授权，执行业务逻辑
            } else {
                gaoDeMap();
                Toast.makeText(this, "定位授权成功", Toast.LENGTH_SHORT).show();
            }
            //版本小于23时，执行业务逻辑
        } else {
            Toast.makeText(this, "版本小于23", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(GaoDeLocateActivity.this, "允许定位", Toast.LENGTH_SHORT).show();
                    gaoDeMap();
                    //点击了拒绝
                } else {
                    // Permission Denied
                    tv_city.setText("未定位");
                    Toast.makeText(GaoDeLocateActivity.this, "拒绝定位", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    //初始化高德定位数据
    public void gaoDeMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
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

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
        Log.e("MainActivity当前线程id=", Thread.currentThread().getId() + "");

    }


    //定位监听回调
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
                tv_city.setText(aMapLocation.getDistrict());
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + aMapLocation.getErrorCode() + ", errInfo:" + aMapLocation.getErrorInfo());
            }
        }

    }

    //覆写activity的点击事件的分发方法dispatchTouchEvent(触摸软键盘以外的地方，隐藏软键盘)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (HideKeyboardUtils.isShouldHideInput(v, ev)) {
                if (HideKeyboardUtils.hideInputMethod(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
