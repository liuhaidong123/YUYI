package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.technology.yuyi.Model.FamilyUserMessageEmeu;
import com.technology.yuyi.Model.FamilyUserMessageModel;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.ElectronicMessListViewAdapter;
import com.technology.yuyi.bean.bean_DeleteFamilyUser;
import com.technology.yuyi.bean.bean_HomeUserTempAndPress;
import com.technology.yuyi.bean.bean_ListFamilyUser;
import com.technology.yuyi.bean.bean_MedicalRecordList;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyEmptyListView;
import com.technology.yuyi.lzh_utils.MyListView;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.FormView;
import com.technology.yuyi.myview.MyAdapter;
import com.technology.yuyi.myview.MyFrameLyout;
import com.technology.yuyi.myview.RoundImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

//if (position==0){
//        intent.putExtra("type","0");
//        }
//        else {
//        intent.putExtra("type","1");
//        }
//0：用户本人的，1用户家人的
//家庭用户信息页面
public class FamilyUserMessageActivity extends AppCompatActivity implements View.OnClickListener,FamilyUserMessageModel.IElectMsg{
    MyFrameLyout framLayout;
    FamilyUserMessageModel model;
    String[]str1={"体温"};
    String[]str2={"高压","低压"};
    FormView mFormViewPress,mFromViewTemp;//压力，温度的表格图
    ViewPager mViewpager;//血压，温度所在的viewpager
    RelativeLayout userMessage_rela_data;//我的数据分析layout
    ImageView userMessage_rela_data_image;//我的数据分析下拉的image
    RelativeLayout mLayout;//包含表格与说明的layout；
    ImageView msgImageView;//表示偏高，偏低正常的view
    TextView pressHigh,pressLow,temp;//显示高压，低压，体温的view

    RelativeLayout userMessage_rela_ele;//我的电子病例
    ImageView userMessage_rela_ele_image;


    List<FormView> list;//viewpager数据源(血压体温)
    PagerAdapter adapter;

    MyEmptyListView userMessage_rela_ele_listview;//电子病例的listview
    ElectronicMessListViewAdapter mEleAdapter;//w我的电子病例的适配器
    List<bean_MedicalRecordList.ResultBean>li;//数据源

    private String type;
    private ImageView mBack;//返回
    private ImageView mEditImg;
    private RoundImageView user_img_head;//头像
    private TextView user_name_tv;//名称
    private TextView user_name_age;//年龄
    private TextView user_telnum;//电话
    TextView user_name_nickName;//关系
    private bean_ListFamilyUser.ResultBean userInfo;//存放用户信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_user_message);
        type = getIntent().getStringExtra("type");//本人0，家人1
        Bundle b = getIntent().getBundleExtra("family");
        if (b != null) {
            userInfo = (bean_ListFamilyUser.ResultBean) b.getSerializable("family");
        }

        initView();
        if (userInfo != null) {
            Picasso.with(FamilyUserMessageActivity.this).load(Uri.parse(Ip.imagePth + userInfo.getAvatar())).error(R.mipmap.usererr).memoryPolicy(MemoryPolicy.NO_CACHE)
                    .networkPolicy(NetworkPolicy.NO_CACHE).into(user_img_head);
            user_name_tv.setText(userInfo.getTrueName() );
            user_name_age.setText(userInfo.getAge() + "岁");
            user_telnum.setText(userInfo.getTelephone() + "");
            user_name_nickName.setText("(" + userInfo.getNickName() + ")");
        }
        initData();
        model=new FamilyUserMessageModel(this);
        if (userInfo!=null){
            if ("0".equals(type)){
                model.getMyEleMsg();
            }
            else if ("1".equals(type)){
                model.getFamilyUserEleMsg(userInfo.getId()+"");
            }
            model.getTempAndPress(userInfo.getId()+"");
        }
    }

    private void initData() {
        //初始化血压表
        list=new ArrayList<>();
        mFormViewPress=new FormView(this);
        mFormViewPress.drawRightTextView(str2);//血压
        List<Integer>li=new ArrayList<>();//血压Y轴数据源
        int min=40;//最低血压
        for (int i=0;i<8;i++){
            li.add(min);
            min+=20;
        }
        mFormViewPress.drawTopView(li);
        //初始化温度表
        mFromViewTemp=new FormView(this);
        mFromViewTemp.drawRightTextView(str1);//温度
        List<Integer>lt=new ArrayList<>();
        int minTemp=35;//最低温度
        for (int i=0;i<8;i++){
            lt.add(minTemp);
            minTemp+=1;
        }
        mFromViewTemp.setColor(getResources().getColor(R.color.colorOtherSource));
        mFromViewTemp.drawTopView(lt);

        list.add(mFormViewPress);
        list.add(mFromViewTemp);
        adapter=new FormPagerAdapter();
        mViewpager.setAdapter(adapter);
    }

    public void initView() {
        framLayout= (MyFrameLyout) findViewById(R.id.framLayout);
//        TextView pressHigh,pressLow,temp;//显示高压，低压，体温的view
        pressHigh= (TextView) findViewById(R.id.pressHigh);
        pressLow= (TextView) findViewById(R.id.pressLow);
        temp= (TextView) findViewById(R.id.temp);
        msgImageView= (ImageView) findViewById(R.id.msgImageView);
        mLayout= (RelativeLayout) findViewById(R.id.mLayout);
        //电子病例的listview
        li=new ArrayList<>();
        userMessage_rela_ele_listview= (MyEmptyListView) findViewById(R.id.userMessage_rela_ele_listview);
        mEleAdapter=new ElectronicMessListViewAdapter(this,li);
        userMessage_rela_ele_listview.setAdapter(mEleAdapter);
        userMessage_rela_ele_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              //我的电子病例与家人的电子病例接口不同，但返回但数据相同，因此此处使用了同一个bean代替,此处均采用个人的电子病例的bean替代
                Intent intent = new Intent();
                intent.setClass(FamilyUserMessageActivity.this, LookElectronicMessActivity.class);
                intent.putExtra("id",li.get(position));
                intent.putExtra("type", "0");
                startActivity(intent);
            }
        });
        //电子病例
        userMessage_rela_ele= (RelativeLayout) findViewById(R.id.userMessage_rela_ele);
        userMessage_rela_ele.setOnClickListener(this);
        userMessage_rela_ele_image= (ImageView) findViewById(R.id.userMessage_rela_ele_image);
        //数据分析
        userMessage_rela_data= (RelativeLayout) findViewById(R.id.userMessage_rela_data);
        userMessage_rela_data.setOnClickListener(this);
        userMessage_rela_data_image= (ImageView) findViewById(R.id.userMessage_rela_data_image);


        user_name_nickName= (TextView) findViewById(R.id.user_name_nickName);
        mViewpager= (ViewPager) findViewById(R.id.mViewpager);
        ViewGroup.LayoutParams param=mViewpager.getLayoutParams();
        param.height=getWindowManager().getDefaultDisplay().getWidth();
        param.width=getWindowManager().getDefaultDisplay().getWidth();
        mViewpager.setLayoutParams(param);
        user_img_head = (RoundImageView) findViewById(R.id.user_img_head);
        user_name_tv = (TextView) findViewById(R.id.user_name_tv);
        user_name_age = (TextView) findViewById(R.id.user_name_age);
        user_telnum = (TextView) findViewById(R.id.user_telnum);
        //返回
        mBack = (ImageView) findViewById(R.id.family_user_back);
        mBack.setOnClickListener(this);
        mEditImg = (ImageView) findViewById(R.id.edit_img);
        mEditImg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.family_user_back:
                finish();
                break;
            case R.id.edit_img:
                Intent intent = new Intent(this, AddFamilyUserActivity.class);
                intent.putExtra("title", "修改家庭用户");
                intent.putExtra("type", "1");
                Bundle b = new Bundle();
                b.putSerializable("family", userInfo);
                intent.putExtra("family", b);
                startActivityForResult(intent, 100);
                break;
            case R.id.userMessage_rela_data:
                if (mLayout.getVisibility()==View.VISIBLE){//当前电子病例的列表处于显示状态
                    userMessage_rela_data_image.setSelected(false);//关闭
                    mLayout.setVisibility(View.GONE);
                }
                else {
                    userMessage_rela_data_image.setSelected(true);//关闭
                    mLayout.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.userMessage_rela_ele://我的电子病例
                if (framLayout.getVisibility()==View.VISIBLE){//当前电子病例的列表处于显示状态
                    userMessage_rela_ele_image.setSelected(false);//关闭
                    framLayout.setVisibility(View.GONE);
                }
                else {
                    userMessage_rela_ele_image.setSelected(true);//关闭
                    framLayout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    //处里修改信息后的显示问题
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            if (requestCode == 100) {//用户信息修改
                if (data != null) {
                    Bundle bundle = data.getBundleExtra("user");
                    if (bundle != null) {
                        userInfo = (bean_ListFamilyUser.ResultBean) bundle.getSerializable("user");
                        if (userInfo != null) {
                            try {
                                byte[] bytes = Base64.decode(userInfo.getBit64(), Base64.DEFAULT);
                                user_img_head.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                            } catch (Exception e) {
                                Picasso.with(FamilyUserMessageActivity.this).load(Uri.parse(Ip.imagePth + userInfo.getAvatar())).error(R.mipmap.logo).memoryPolicy(MemoryPolicy.NO_CACHE)
                                        .networkPolicy(NetworkPolicy.NO_CACHE).into(user_img_head);
                            }
                            user_name_tv.setText(userInfo.getTrueName() + "（" + userInfo.getNickName() + "）");
                            user_name_age.setText(userInfo.getAge() + "岁");
                            if (userInfo.getTelephone() == 0) {
                                user_telnum.setText("");
                            } else {
                                user_telnum.setText(userInfo.getTelephone() + "");
                            }
                        }
                    }
                }
            }
        }
        else if (resultCode==100){
            //删除用户成功后
                finish();
        }
    }

    public class FormPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list==null?0: list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(list.get(position));
            return list.get(position);
        }
    }


    //网络请求返回
    @Override
    public void onNetWorkError() {
        Log.e("网络出错了","无法进行联网／服务器拒绝链接");
        framLayout.setNoNetWorkView();
    }
    //获取电子病例
    @Override
    public void onGetElectMsg(bean_MedicalRecordList recordList){
        framLayout.setNormal();
        if ("0".equals(recordList.getCode())){
           List<bean_MedicalRecordList.ResultBean>list= recordList.getResult();
            if (list!=null&&list.size()>0){
                li.addAll(list);
                mEleAdapter.notifyDataSetChanged();
            }
            else {
                framLayout.setEmptyView("没有病例记录！");
//                userMessage_rela_ele_listview.setEmpty();
            }
        }
        else {
            framLayout.setEmptyView(!"".equals(recordList.getMessage())&&!TextUtils.isEmpty(recordList.getMessage())?"失败："+recordList.getMessage():"失败：未知原因");
//            userMessage_rela_ele_listview.setEmpty();
        }
    }
    //获取温度与血压
    @Override
    public void onGetUserTempAndPress(bean_HomeUserTempAndPress bean) {
        if ("0".equals(bean.getCode())){
           bean_HomeUserTempAndPress.ResultBean result= bean.getResult();
            if (result!=null){
                //最后一条数据的高压，低压，体温值（用于页面显示）
                float HighPress=0.0f;
                float LowPress=0.0f;
                float Temp=0.0f;
                List<bean_HomeUserTempAndPress.ResultBean.BloodpressureListBean>listBlood=result.getBloodpressureList();//血压
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
                //处理血压中的数据
                if (listBlood!=null&&listBlood.size()>0){
                    LowPress=listBlood.get(listBlood.size()-1).getDiastolic();
                    HighPress=listBlood.get(listBlood.size()-1).getSystolic();
                    List<String>listTime=new ArrayList<>();//血压测量的日期的集合
                    List<Float>listSource=new ArrayList<>();//高压集合值
                    List<Float>listOhtherSource=new ArrayList<>();//低压集合值
                    for (int i=0;i<listBlood.size();i++){
                        try{
                            String dstr=listBlood.get(i).getCreateTimeString();
                            Date date=sdf.parse(dstr);
                            SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                            String time=format.format(date);
                            listTime.add(time);
                            listSource.add(Float.parseFloat(listBlood.get(i).getSystolic()+""));//高压
                            listOhtherSource.add(Float.parseFloat(listBlood.get(i).getDiastolic()+""));//低压
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (listTime.size()<7){//补全不足7天的日期
                        try{
                            int tp=7-listTime.size();
                            SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                            Calendar c = Calendar.getInstance();
                            String dstr=listBlood.get(listBlood.size()-1).getCreateTimeString();
                            Date date=sdf.parse(dstr);
                            c.setTime(date);//以最后一个测量日期为准加够7天
                            for (int i=0;i<tp;i++){//填充日期，最后一个日期加1天
                                c.add(Calendar.DAY_OF_MONTH,1);
                                listTime.add(format.format(c.getTime()));
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (listSource.size()<7){//补全不足7天的高压
                        int tp=7-listSource.size();
                        for (int i=0;i<tp;i++){
                            listSource.add(-1f);
                        }
                    }
                    if (listOhtherSource.size()<7){//补全不足7天的低压
                        int tp=7-listOhtherSource.size();
                        for (int i=0;i<tp;i++){
                            listOhtherSource.add(-1f);
                        }
                    }
                    mFormViewPress.drawBottomView(listTime);
                    mFormViewPress.drawFirstDataView(listSource);
                    mFormViewPress.drawOtherDataView(listOhtherSource);
                }

                //处理温度的中的数据
                List<bean_HomeUserTempAndPress.ResultBean.TemperatureListBean>listTemp=result.getTemperatureList();//温度
                if (listTemp!=null&&listTemp.size()>0){
                    Temp=listTemp.get(listTemp.size()-1).getTemperaturet();
                    List<String>listTime=new ArrayList<>();//测量的日期
                    List<Float>listSource=new ArrayList<>();//测量到的血压数据
                    for (int i=0;i<listTemp.size();i++){
                        try{
                            String dstr=listTemp.get(i).getCreateTimeString();
                            Date date=sdf.parse(dstr);
                            SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                            String time=format.format(date);
                            listTime.add(time);
                            listSource.add(Float.parseFloat(listTemp.get(i).getTemperaturet()+""));
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                    if (listTime!=null&&listTime.size()<7){//补全不足7天的日期
                        try{
                            int tp=7-listTime.size();
                            SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                            Calendar c = Calendar.getInstance();
                            String dstr=listTemp.get(listTemp.size()-1).getCreateTimeString();
                            Date date=sdf.parse(dstr);
                            c.setTime(date);//以最后一个测量日期为准加够7天
                            for (int i=0;i<tp;i++){//填充日期，最后一个日期加1天
                                c.add(Calendar.DAY_OF_MONTH,1);
                                listTime.add(format.format(c.getTime()));
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }

                        try{
                            int tp=7-listTime.size();
                            SimpleDateFormat format=new SimpleDateFormat("MM月dd日");
                            Calendar c = Calendar.getInstance();
                            String dstr=listTemp.get(listTemp.size()-1).getCreateTimeString();
                            Date date=sdf.parse(dstr);
                            c.setTime(date);//以最后一个测量日期为准加够7天
                            for (int i=0;i<tp;i++){//填充日期，最后一个日期加1天
                                c.add(Calendar.DAY_OF_MONTH,1);
                                listTime.add(format.format(c.getTime()));
                            }
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if (listSource!=null&&listSource.size()<7){//补全不足7天的体温
                        int tp=7-listSource.size();
                        for (int i=0;i<tp;i++){
                            listSource.add(-1f);//-1f表示当天没有数据
                        }
                    }
                    mFromViewTemp.drawBottomView(listTime);
                    mFromViewTemp.drawFirstDataView(listSource);
                }
//
//                (result!=null){
//                    //最后一条数据的高压，低压，体温值（用于页面显示）
//                    float HighPress=0.0f;
//                    float LowPress=0.0f;
//                    float Temp=0.0f;
//                pressHigh,pressLow,temp
                String preH=getText(HighPress);
                String preL=getText(LowPress);
                String tem=getText(Temp)+"°c";
                pressHigh.setText(preH);
                pressLow.setText(preL);
                temp.setText(tem);
                msgImageView.setImageResource(FamilyUserMessageEmeu.getRes(FamilyUserMessageEmeu.getInfo(Temp,HighPress,LowPress)));
            }
        }
    }

    public String getText(float tex){
        String text=tex+"";
        String tt=text.substring(0,text.indexOf("."));
        String last=text.substring(text.indexOf(".")+1,text.length());
        if ("0".equals(last)|"00".equals(last)){
            text=tt;
        }
        return text;
    }
}
