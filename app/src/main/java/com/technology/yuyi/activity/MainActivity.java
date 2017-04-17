package com.technology.yuyi.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.beanRongToken;
import com.technology.yuyi.fragment.AskFragment;
import com.technology.yuyi.fragment.FirstPageFragment;
import com.technology.yuyi.fragment.MeasureFragment;
import com.technology.yuyi.fragment.MyFragment;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.JPshAliasAndTags;
import com.technology.yuyi.lzh_utils.RongUSerProvider;
import com.technology.yuyi.lzh_utils.RongUser;
import com.technology.yuyi.lzh_utils.RongUserList;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

import static io.rong.imkit.utils.SystemUtils.getCurProcessName;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private LinearLayout mFirstPage_ll;
    private LinearLayout mMeasure_ll;
    private LinearLayout mAsk_ll;
    private LinearLayout mMy_ll;
    private TextView mFirstPage_tv;
    private TextView mMeasure_tv;
    private TextView mAsk_tv;
    private TextView mMy_tv;
    private ImageView mFirstPafe_img;
    private ImageView mMeasure_img;
    private ImageView mAsk_img;
    private ImageView mMy_img;
    private RelativeLayout mFragment_rl;
    private FragmentManager mFragmentManager;
    public final String firstPageTag = "firstPageFragment";
    public final String measureTag = "measureFragment";
    public final String askTag = "askFragment";
    public final String myTag = "myFragment";

    public final String pressColor = "#25f368";
    public final String noPressColor = "#666666";
    private long time = 0;
    private String resStr;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    toast.toast_faild(MainActivity.this);
                    break;
                case 1:
                    try{
                        beanRongToken tok= gson.gson.fromJson(resStr,beanRongToken.class);
                        if ("1".equals(tok.getCode())){
                            user.RongToken=tok.getToken();
                            Log.i("Rong--",tok.getId()+"--"+tok.getTrueName());
                            RongIM.getInstance().setCurrentUserInfo(new UserInfo(tok.getId()+"",tok.getTrueName()+"",Uri.parse(Ip.imagePth+tok.getAvatar())));
                            initRongCon();
                        }
                        else if ("0".equals(tok.getCode())){
                            Log.e("融云获取token错误--1--main-","---后台无法返回token----");
                        }
                        else {
                            Log.e("融云获取token错误--2-main-","---后台无法返回token----");
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(MainActivity.this);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showFirstPageFragment();
//        RongIM.setUserInfoProvider(this,true);
        getRongUserInfo();//向服务器请求融云token
        if (JPshAliasAndTags.isJPSHSucc(MainActivity.this)==false){
            JPshAliasAndTags.setAlias(MainActivity.this,user.userName);
            Log.e("激光推送在MainActivity注册----","Login激光推送注册失败，重新注册");
        }
        else {
            Log.e("激光推送在LoginActiity注册----","Login激光推送注册成功");
        }
    }



    //初始化数据
    public void initView() {
        mFragment_rl = (RelativeLayout) findViewById(R.id.fragment_relative);
        mFragmentManager = getSupportFragmentManager();
        //底部按钮
        mFirstPage_ll = (LinearLayout) findViewById(R.id.firstpage_btn_ll);
        mMeasure_ll = (LinearLayout) findViewById(R.id.measure_btn_ll);
        mAsk_ll = (LinearLayout) findViewById(R.id.ask_btn_ll);
        mMy_ll = (LinearLayout) findViewById(R.id.my_btn_ll);
        mFirstPage_ll.setOnClickListener(this);
        mMeasure_ll.setOnClickListener(this);
        mAsk_ll.setOnClickListener(this);
        mMy_ll.setOnClickListener(this);
        //底部文字
        mFirstPage_tv = (TextView) findViewById(R.id.firstpage_tv);
        mMeasure_tv = (TextView) findViewById(R.id.measure_tv);
        mAsk_tv = (TextView) findViewById(R.id.ask_tv);
        mMy_tv = (TextView) findViewById(R.id.my_tv);
        //底部图片
        mFirstPafe_img = (ImageView) findViewById(R.id.firstpage_btn_img);
        mMeasure_img = (ImageView) findViewById(R.id.measure_btn_img);
        mAsk_img = (ImageView) findViewById(R.id.ask_btn_img);
        mMy_img = (ImageView) findViewById(R.id.my_btn_img);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mFirstPage_ll.getId()) {
            showFirstPageFragment();
        } else if (id == mMeasure_ll.getId()) {
            showMeasureFragment();
        } else if (id == mAsk_ll.getId()) {
            showAskFragment();
        } else if (id == mMy_ll.getId()) {
            showMyFragment();
        }
    }

    //显示首页
    public void showFirstPageFragment() {
        clickFirstBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment firstPageFragment = mFragmentManager.findFragmentByTag(firstPageTag);
        Fragment measureFragment = mFragmentManager.findFragmentByTag(measureTag);
        Fragment askFragment = mFragmentManager.findFragmentByTag(askTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (firstPageFragment != null) {//显示首页
            fragmentTransaction.show(firstPageFragment);
        } else {
            FirstPageFragment firstPageF = new FirstPageFragment();
            fragmentTransaction.add(mFragment_rl.getId(), firstPageF, firstPageTag);
        }
        if (measureFragment != null) {//隐藏测量
            fragmentTransaction.hide(measureFragment);
        }
        if (askFragment != null) {//隐藏咨询
            fragmentTransaction.hide(askFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();
    }

    //显示测量页面
    public void showMeasureFragment() {
        clickMeasureBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment firstPageFragment = mFragmentManager.findFragmentByTag(firstPageTag);
        Fragment measureFragment = mFragmentManager.findFragmentByTag(measureTag);
        Fragment askFragment = mFragmentManager.findFragmentByTag(askTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (measureFragment != null) {//显示测量
            fragmentTransaction.show(measureFragment);
        } else {
            MeasureFragment measureF = new MeasureFragment();
            fragmentTransaction.add(mFragment_rl.getId(), measureF, measureTag);
        }
        if (firstPageFragment != null) {//隐藏首页
            fragmentTransaction.hide(firstPageFragment);
        }
        if (askFragment != null) {//隐藏咨询
            fragmentTransaction.hide(askFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();

    }

    //显示咨询页面
    public void showAskFragment() {
        clickAskBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment firstPageFragment = mFragmentManager.findFragmentByTag(firstPageTag);
        Fragment measureFragment = mFragmentManager.findFragmentByTag(measureTag);
        Fragment askFragment = mFragmentManager.findFragmentByTag(askTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (askFragment != null) {//显示咨询
            fragmentTransaction.show(askFragment);
        } else {
            AskFragment askF = new AskFragment();
            fragmentTransaction.add(mFragment_rl.getId(), askF, askTag);
        }
        if (firstPageFragment != null) {//隐藏首页
            fragmentTransaction.hide(firstPageFragment);
        }
        if (measureFragment != null) {//隐藏测量
            fragmentTransaction.hide(measureFragment);
        }
        if (myFragment != null) {//隐藏我的
            fragmentTransaction.hide(myFragment);
        }
        fragmentTransaction.commit();

    }

    //显示我的页面
    public void showMyFragment() {
        clickMyBtnChangeTvColor();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment firstPageFragment = mFragmentManager.findFragmentByTag(firstPageTag);
        Fragment measureFragment = mFragmentManager.findFragmentByTag(measureTag);
        Fragment askFragment = mFragmentManager.findFragmentByTag(askTag);
        Fragment myFragment = mFragmentManager.findFragmentByTag(myTag);

        if (myFragment != null) {//显示我的
            fragmentTransaction.show(myFragment);
        } else {
            MyFragment myF = new MyFragment();
            fragmentTransaction.add(mFragment_rl.getId(), myF, myTag);
        }
        if (firstPageFragment != null) {//隐藏首页
            fragmentTransaction.hide(firstPageFragment);
        }
        if (measureFragment != null) {//隐藏测量
            fragmentTransaction.hide(measureFragment);
        }
        if (askFragment != null) {//隐藏咨询
            fragmentTransaction.hide(askFragment);
        }
        fragmentTransaction.commit();
    }

    //点击首页文字图片变化
    public void clickFirstBtnChangeTvColor() {
        mFirstPage_tv.setTextColor(Color.parseColor(pressColor));
        mFirstPafe_img.setImageResource(R.mipmap.first_page_press);

        mMeasure_tv.setTextColor(Color.parseColor(noPressColor));
        mMeasure_img.setImageResource(R.mipmap.measure_no_press);

        mAsk_tv.setTextColor(Color.parseColor(noPressColor));
        mAsk_img.setImageResource(R.mipmap.ask_no_press);

        mMy_tv.setTextColor(Color.parseColor(noPressColor));
        mMy_img.setImageResource(R.mipmap.my_no_press);

    }

    //点击测量文字图片变化
    public void clickMeasureBtnChangeTvColor() {
        mFirstPage_tv.setTextColor(Color.parseColor(noPressColor));
        mFirstPafe_img.setImageResource(R.mipmap.first_page_no_press);

        mMeasure_tv.setTextColor(Color.parseColor(pressColor));
        mMeasure_img.setImageResource(R.mipmap.measure_press);

        mAsk_tv.setTextColor(Color.parseColor(noPressColor));
        mAsk_img.setImageResource(R.mipmap.ask_no_press);

        mMy_tv.setTextColor(Color.parseColor(noPressColor));
        mMy_img.setImageResource(R.mipmap.my_no_press);
    }

    //点击咨询文字图片变化
    public void clickAskBtnChangeTvColor() {
        mFirstPage_tv.setTextColor(Color.parseColor(noPressColor));
        mFirstPafe_img.setImageResource(R.mipmap.first_page_no_press);

        mMeasure_tv.setTextColor(Color.parseColor(noPressColor));
        mMeasure_img.setImageResource(R.mipmap.measure_no_press);

        mAsk_tv.setTextColor(Color.parseColor(pressColor));
        mAsk_img.setImageResource(R.mipmap.ask_press);

        mMy_tv.setTextColor(Color.parseColor(noPressColor));
        mMy_img.setImageResource(R.mipmap.my_no_press);
    }

    //点击我的文字图片变化
    public void clickMyBtnChangeTvColor() {
        mFirstPage_tv.setTextColor(Color.parseColor(noPressColor));
        mFirstPafe_img.setImageResource(R.mipmap.first_page_no_press);

        mMeasure_tv.setTextColor(Color.parseColor(noPressColor));
        mMeasure_img.setImageResource(R.mipmap.measure_no_press);

        mAsk_tv.setTextColor(Color.parseColor(noPressColor));
        mAsk_img.setImageResource(R.mipmap.ask_no_press);

        mMy_tv.setTextColor(Color.parseColor(pressColor));
        mMy_img.setImageResource(R.mipmap.my_press);

    }

    @Override
    public void onBackPressed() {
        if (time > 0) {
            if (System.currentTimeMillis() - time < 2000) {
                super.onBackPressed();
            } else {
                time = System.currentTimeMillis();
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }

        } else {
            time = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();

        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String usname=getIntent().getStringExtra("username");
        String userpsd=getIntent().getStringExtra("userpsd");
        if (!"".equals(userpsd)&&!TextUtils.isEmpty(userpsd)&&!"".equals(usname)&&!TextUtils.isEmpty(usname)){
            user.userName=usname;
            user.userPsd=userpsd;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initRongCon() {
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))){
            RongIM.connect(user.RongToken, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    user.RonguserId=userid;
                    Log.i("融云返回的id--main-",userid+"----");
                }
                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Toast.makeText(MainActivity.this,"信息注册失败，无法启动咨询程序",Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


    //获取融云tokenhttp://localhost:8080/yuyi/personal/post.do?personalid=18881882888
    public void getRongUserInfo() {
        if (user.userName!=null&&!"0".equals(user.userName)&&!"".equals(user.userName)){
                Map<String,String> mp=new HashMap<>();
                mp.put("personalid",user.userName);
            okhttp.getCall(Ip.url_F+Ip.interface_RongToken,mp,okhttp.OK_POST).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
                }

                @Override
                public void onResponse(Response response) throws IOException {
                        resStr=response.body().string();
                        Log.i("-融云token请求-Main-"+user.userName,resStr);
                        handler.sendEmptyMessage(1);
                }
            });
        }
    }
    @Override
    protected void onStop() {
        super.onStop();

    }
}
