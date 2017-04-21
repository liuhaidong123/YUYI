package com.technology.yuyi.fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.technology.yuyi.HttpTools.UrlTools;
import com.technology.yuyi.R;
import com.technology.yuyi.activity.ElectronicMessActivity;
import com.technology.yuyi.activity.EquipmentManageActivity;
import com.technology.yuyi.activity.FamilyManageActivity;
import com.technology.yuyi.activity.MyOrderActivity;
import com.technology.yuyi.activity.My_address_Activity;
import com.technology.yuyi.activity.My_message_Activity;
import com.technology.yuyi.activity.My_shoppingCart_Activity;
import com.technology.yuyi.activity.My_userLogin_Activity;
import com.technology.yuyi.activity.SetActivity;
import com.technology.yuyi.activity.UserEditorActivity;
import com.technology.yuyi.bean.UserMessage;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.checkNotificationAllowed;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.RoundImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener {
    private RoundImageView mHead_img;
    private TextView mNikName;
    private TextView mUsername;
    private RelativeLayout mUserEditor;//用户信息编辑
    private RelativeLayout mElectronicMess;//电子病历
    private RelativeLayout mSetBtn;//设置
    private RelativeLayout mEquipment;//设备管理
    private RelativeLayout mFamily;//家庭用户管理
    private RelativeLayout mOrder;//订单详情
    private RelativeLayout my_rela_userLogin, my_rela_userNotLogin;
    private RelativeLayout thing_rl;//gouwuche
    private RelativeLayout address_rl;//收货地址
    private RelativeLayout my_message;//消息
    private String resStr;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                toast.toast_faild(getActivity());
            } else if (msg.what == 1) {
                try {
                    UserMessage userMessage = gson.gson.fromJson(resStr, UserMessage.class);
                    UserMessage.ResultBean bean = userMessage.getResult();
                    Picasso.with(getContext()).load(UrlTools.BASE + bean.getAvatar()).error(R.mipmap.usererr).into(mHead_img);
//                    Picasso.with(getContext()).load(UrlTools.BASE+bean.getAvatar()).error(R.mipmap.usererr).into(mHead_img);
                    if (!"".equals(bean.getTrueName()) && !TextUtils.isEmpty(bean.getTrueName())) {
                        mNikName.setText(bean.getTrueName() + "");
                        mNikName.setVisibility(View.VISIBLE);
                    } else {
                        mNikName.setVisibility(View.GONE);
                    }
                    mUsername.setText(bean.getId() + "");
                } catch (Exception e) {
                    toast.toast_gsonFaild(getActivity());
                }
            }
        }
    };


    public MyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        return view;
    }

    //初始化数据
    public void initView(View view) {

        //用户信息
        mHead_img = (RoundImageView) view.findViewById(R.id.my_head_img);
        mNikName = (TextView) view.findViewById(R.id.my_name);
        mUsername = (TextView) view.findViewById(R.id.my_name2);
        //用户编辑
        mUserEditor = (RelativeLayout) view.findViewById(R.id.rl_title);
        mUserEditor.setOnClickListener(this);
        //电子病历
        mElectronicMess = (RelativeLayout) view.findViewById(R.id.elec_rl);
        mElectronicMess.setOnClickListener(this);
        //设置
        mSetBtn = (RelativeLayout) view.findViewById(R.id.set_relative);
        mSetBtn.setOnClickListener(this);
        //设备管理
        mEquipment = (RelativeLayout) view.findViewById(R.id.equtment_rl);
        mEquipment.setOnClickListener(this);
        //家庭用户管理
        mFamily = (RelativeLayout) view.findViewById(R.id.home_rl);
        mFamily.setOnClickListener(this);

        //订单详情
        mOrder = (RelativeLayout) view.findViewById(R.id.order_rl);
        mOrder.setOnClickListener(this);

        thing_rl = (RelativeLayout) view.findViewById(R.id.thing_rl);
        thing_rl.setOnClickListener(this);

        address_rl = (RelativeLayout) view.findViewById(R.id.address_rl);
        address_rl.setOnClickListener(this);

        my_message = (RelativeLayout) view.findViewById(R.id.my_message);
        my_message.setOnClickListener(this);

        my_rela_userLogin = (RelativeLayout) view.findViewById(R.id.my_rela_userLogin);
        my_rela_userNotLogin = (RelativeLayout) view.findViewById(R.id.my_rela_userNotLogin);
    }

    @Override
    public void onResume() {
        super.onResume();
        Map<String, String> mp = new HashMap<>();
        mp.put("token", user.token);
        okhttp.getCall(Ip.url + Ip.interface_UserMsg, mp, okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                Log.i("获取用户信息返回---", resStr);

                handler.sendEmptyMessage(1);
            }
        });
        if (user.isLogin(getActivity())) {
            my_rela_userLogin.setVisibility(View.VISIBLE);
            my_rela_userNotLogin.setVisibility(View.GONE);
        } else {
            my_rela_userLogin.setVisibility(View.GONE);
            my_rela_userNotLogin.setVisibility(View.VISIBLE);
        }

        if (checkNotificationAllowed.isNOtificationOpen(getActivity()) == false) {//当用户没有通知栏权限时
            SharedPreferences preferences = getActivity().getSharedPreferences("NOTIFICATION", Context.MODE_APPEND);
            if (preferences.contains("notifi") == false) {//用户第一次点击修改权限弹窗时写入，用于判断是否显示跳转到权限修改到界面（true：用户之前已经进入过修改权限到页面，但不给予通知但权限，false：用户没有进入过）
                showWindowRevampLimit();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    //跳转到修改权限页面到弹窗
    private void showWindowRevampLimit() {
        new AlertDialog.Builder(getActivity()).setTitle("应用通知栏权限被禁止").
                setMessage("无法接收到应用发送的相关通知，需要您手动打开通知权限，是否现在去打开？").setIcon(R.mipmap.logo).
                setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().getSharedPreferences("NOTIFICATION", Context.MODE_APPEND).edit().putBoolean("notifi", true).commit();
                        checkNotificationAllowed.goToSet(getActivity());
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(true).show();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //用户信息编辑
        if (id == mUserEditor.getId()) {
            if (user.isLogin(getActivity())) {
                Intent intent = new Intent();
                intent.putExtra("type", "0");
                intent.setClass(getActivity(), UserEditorActivity.class);
                startActivity(intent);
            } else {
                startActivity(new Intent(this.getActivity(), My_userLogin_Activity.class));
            }
            //电子病历
        } else if (id == mElectronicMess.getId()) {
            Intent intent = new Intent(this.getActivity(), ElectronicMessActivity.class);
            intent.putExtra("type", "0");
            startActivity(intent);
            //设置
        } else if (id == mSetBtn.getId()) {
            startActivity(new Intent(this.getActivity(), SetActivity.class));
            //设备管理
        } else if (id == mEquipment.getId()) {
            startActivity(new Intent(this.getActivity(), EquipmentManageActivity.class));
            //家庭用户管理
        } else if (id == mFamily.getId()) {
            startActivity(new Intent(this.getActivity(), FamilyManageActivity.class));
            //订单详情
        } else if (id == mOrder.getId()) {
            startActivity(new Intent(this.getActivity(), MyOrderActivity.class));
        } else if (id == R.id.thing_rl) {
            startActivity(new Intent(this.getActivity(), My_shoppingCart_Activity.class));
        } else if (id == R.id.address_rl) {//收货地址
            startActivity(new Intent(this.getActivity(), My_address_Activity.class));
        } else if (id == R.id.my_message) {//消息
            startActivity(new Intent(this.getActivity(), My_message_Activity.class));
        }
    }
}
