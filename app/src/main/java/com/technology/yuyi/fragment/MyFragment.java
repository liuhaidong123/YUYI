package com.technology.yuyi.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.technology.yuyi.R;
import com.technology.yuyi.activity.ElectronicMessActivity;
import com.technology.yuyi.activity.EquipmentManageActivity;
import com.technology.yuyi.activity.FamilyManageActivity;
import com.technology.yuyi.activity.MyOrderActivity;
import com.technology.yuyi.activity.My_address_Activity;
import com.technology.yuyi.activity.My_message_Activity;
import com.technology.yuyi.activity.My_shoppingCart_Activity;
import com.technology.yuyi.activity.OrderMessageActivity;
import com.technology.yuyi.activity.My_userLogin_Activity;
import com.technology.yuyi.activity.SetActivity;
import com.technology.yuyi.activity.UserEditorActivity;
import com.technology.yuyi.lzh_utils.user;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mUserEditor;//用户信息编辑
    private RelativeLayout mElectronicMess;//电子病历
    private RelativeLayout mSetBtn;//设置
    private RelativeLayout mEquipment;//设备管理
    private RelativeLayout mFamily;//家庭用户管理
    private RelativeLayout mOrder;//订单详情
    private RelativeLayout my_rela_userLogin,my_rela_userNotLogin;
    private RelativeLayout thing_rl;//gouwuche
    private RelativeLayout address_rl;//收货地址
    private RelativeLayout my_message;//消息
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
        //用户编辑
        mUserEditor = (RelativeLayout) view.findViewById(R.id.rl_title);
        mUserEditor.setOnClickListener(this);
        //电子病历
        mElectronicMess = (RelativeLayout) view.findViewById(R.id.elec_rl);
        mElectronicMess.setOnClickListener(this);
        //设置
        mSetBtn= (RelativeLayout) view.findViewById(R.id.set_relative);
        mSetBtn.setOnClickListener(this);
        //设备管理
        mEquipment=(RelativeLayout) view.findViewById(R.id.equtment_rl);
        mEquipment.setOnClickListener(this);
        //家庭用户管理
        mFamily=(RelativeLayout) view.findViewById(R.id.home_rl);
        mFamily.setOnClickListener(this);

        //订单详情
        mOrder= (RelativeLayout) view.findViewById(R.id.order_rl);
        mOrder.setOnClickListener(this);

        thing_rl= (RelativeLayout) view.findViewById(R.id.thing_rl);
        thing_rl.setOnClickListener(this);

        address_rl= (RelativeLayout) view.findViewById(R.id.address_rl);
        address_rl.setOnClickListener(this);

        my_message= (RelativeLayout) view.findViewById(R.id.my_message);
        my_message.setOnClickListener(this);

        my_rela_userLogin= (RelativeLayout) view.findViewById(R.id.my_rela_userLogin);
        my_rela_userNotLogin= (RelativeLayout) view.findViewById(R.id.my_rela_userNotLogin);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (user.isLogin(getActivity())){
            my_rela_userLogin.setVisibility(View.VISIBLE);
            my_rela_userNotLogin.setVisibility(View.GONE);
        }
        else {
            my_rela_userLogin.setVisibility(View.GONE);
            my_rela_userNotLogin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //用户信息编辑
        if (id == mUserEditor.getId()) {
            if (user.isLogin(getActivity())){
                startActivity(new Intent(this.getContext(), UserEditorActivity.class));
            }
            else {
                startActivity(new Intent(this.getContext(), My_userLogin_Activity.class));
            }
            //电子病历
        } else if (id == mElectronicMess.getId()) {
            startActivity(new Intent(this.getContext(), ElectronicMessActivity.class));
            //设置
        } else if (id == mSetBtn.getId()) {
            startActivity(new Intent(this.getContext(), SetActivity.class));
            //设备管理
        }else if (id == mEquipment.getId()) {
            startActivity(new Intent(this.getContext(), EquipmentManageActivity.class));
            //家庭用户管理
        }else if (id == mFamily.getId()) {
            startActivity(new Intent(this.getContext(), FamilyManageActivity.class));
            //订单详情
        }else if (id == mOrder.getId()) {
            startActivity(new Intent(this.getContext(), MyOrderActivity.class));
        }
        else if (id==R.id.thing_rl){
            startActivity(new Intent(this.getContext(), My_shoppingCart_Activity.class));
        }
        else if (id==R.id.address_rl){//收货地址
            startActivity(new Intent(this.getContext(), My_address_Activity.class));
        }
        else if (id==R.id.my_message){//消息
            startActivity(new Intent(this.getContext(), My_message_Activity.class));
        }
    }
}
