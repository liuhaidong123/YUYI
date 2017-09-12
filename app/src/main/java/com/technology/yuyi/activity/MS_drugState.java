package com.technology.yuyi.activity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.technology.yuyi.Presenter.MS_drugStatePresenter;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.DrugListAdapter;
import com.technology.yuyi.adapter.MS_DrugStateAdapter;
import com.technology.yuyi.adapter.MyDrugStatePagerAdapter;
import com.technology.yuyi.bean.BeanDrugStates;
import com.technology.yuyi.bean.bean_MyDrugState;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.MyActivity;
import com.technology.yuyi.lzh_utils.WindowUtils;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_view.MyTextView;
import com.technology.yuyi.myview.MyFrameLyout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//intent.putExtra("drug",MyDrugState);
public class MS_drugState extends MyActivity implements MS_drugStatePresenter.IMS_drugStateView{
    FrameLayout myfragment;
    MyFrameLyout myFrag;
    int[] res1=new int[]{R.mipmap.a1,R.mipmap.a2,R.mipmap.a3,R.mipmap.a4};
    int[]res2=new int[]{R.mipmap.picture1,R.mipmap.picture2,R.mipmap.picture3,R.mipmap.picture4};
    String[] str1=new String[]{"医务人员正在准备药材哦！","配药已完成，医务人员正在将药品火速送往煎药处！","熬制中药需要时间较长，请您耐心等候哦","您的中药已完成，请尽快到医院取药处取药！"};
    String[]str2=new String[]{"药材准备中","配药已完成","中药熬制中","中药熬制已完成"};
    List<BeanDrugStates.ResultBean>lt;//存放所有的药品
    MyDrugStatePagerAdapter adapter;
    List<View>li;
    ViewPager mViewpagerDrug;//
    MS_drugStatePresenter presenter;
    private TextView title;
    RelativeLayout my_drug_LayoutInfo;//更多的layout
    TextView my_drug_textVInfo;//显示药品编号的view

    //切换更多的listview
    ListView drug_state_ListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ms_drug_state);
        initView();
        presenter=new MS_drugStatePresenter(this);
        presenter.getMyDrugStates();//获取我的药品状态
    }
    private void initView() {
        myfragment= (FrameLayout) findViewById(R.id.myfragment);
        myfragment.setVisibility(View.GONE);
        myFrag= (MyFrameLyout) findViewById(R.id.myFrag);

        drug_state_ListView= (ListView) findViewById(R.id.drug_state_ListView);
        drug_state_ListView.setVisibility(View.GONE);

        title= (TextView) findViewById(R.id.activity_include_title);
        title.setText("我的药品状态");
        my_drug_LayoutInfo= (RelativeLayout) findViewById(R.id.my_drug_LayoutInfo);
        my_drug_LayoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lt!=null&&lt.size()>0){
                    if (drug_state_ListView.getVisibility()==View.VISIBLE){
                        TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
                                Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1f);
                        animation.setDuration(500);//设置动画持续时间
                        animation.setRepeatCount(0);//设置重复次数
                        drug_state_ListView.setAnimation(animation);
                        drug_state_ListView.setVisibility(View.GONE);
                    }
                    else {
                        drug_state_ListView.setVisibility(View.VISIBLE);
                        TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
                                Animation.RELATIVE_TO_SELF,-1f,Animation.RELATIVE_TO_SELF,0f);
                        animation.setDuration(500);//设置动画持续时间
                        animation.setRepeatCount(0);//设置重复次数
                        drug_state_ListView.setAnimation(animation);
                    }
                }
            }
        });
        my_drug_textVInfo= (TextView) findViewById(R.id.my_drug_textVInfo);
        mViewpagerDrug= (ViewPager) findViewById(R.id.mViewpagerDrug);
        ViewGroup.LayoutParams layoutParams = mViewpagerDrug.getLayoutParams();
        layoutParams.width = WindowUtils.getInstance().getWindowWidth(this)-(int)(getResources().getDimension(R.dimen.drugPadding));
        mViewpagerDrug.setLayoutParams(layoutParams);
        mViewpagerDrug.setOffscreenPageLimit(2);
    }

    @Override
    public void onGetMyDrugStates(BeanDrugStates bean) {
        if (bean!=null){
            if ("0".equals(bean.getCode())){
                lt=bean.getResult();
                if (lt!=null&&lt.size()>0){
                    myfragment.setVisibility(View.VISIBLE);
                    DrugListAdapter ada=new DrugListAdapter(this,lt);
                    drug_state_ListView.setAdapter(ada);
                    drug_state_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if (position==lt.size()){//最后一个是取消
                                TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
                                        Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1f);
                                animation.setDuration(500);//设置动画持续时间
                                animation.setRepeatCount(0);//设置重复次数
                                drug_state_ListView.setAnimation(animation);
                                drug_state_ListView.setVisibility(View.GONE);
                            }
                            else {
                                init(position);
                                TranslateAnimation animation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,
                                        Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1f);
                                animation.setDuration(500);//设置动画持续时间
                                animation.setRepeatCount(0);//设置重复次数
                                drug_state_ListView.setAnimation(animation);
                                drug_state_ListView.setVisibility(View.GONE);
                            }
                        }
                    });
                    init(0);
                }
                else {
                    myFrag.setEmptyView("没有查询到记录！");
                }
            }
            else {
                myFrag.setEmptyView(!"".equals(bean.getMessage())&&!TextUtils.isEmpty(bean.getMessage())?"失败："+bean.getMessage():"失败：未知原因");
            }
        }
        else {
            myFrag.setEmptyView("没有查询到记录！");
        }
    }

    @Override
    public void onGetMyDrugStatesError(String msg) {
        myFrag.setEmptyView(msg);
    }

    public void init(int pos){
        if (lt!=null&&pos<=lt.size()-1){
            li=new ArrayList<>();
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try{
                Date date=format.parse(lt.get(pos).getCreateTimeString());
                Calendar c=Calendar.getInstance();
                c.setTime(date);
                my_drug_textVInfo.setText(c.get(Calendar.YEAR)+""+(c.get(Calendar.MONTH)+1)+""+c.get(Calendar.DAY_OF_MONTH)+"-"+lt.get(pos).getPersinalId());
            }
            catch (Exception e){
                my_drug_textVInfo.setText(lt.get(pos).getCreateTimeString()+"-"+lt.get(pos).getPersinalId());
                e.printStackTrace();
            }
            finally {
                int state=lt.get(pos).getState();//state药品的状态，从1开始最后一个为4对应res1，res2的长度
                for (int i=0;i<state;i++) {
                    View vi = LayoutInflater.from(this).inflate(R.layout.drugstate, null);
                    TextView bottomText = (TextView) vi.findViewById(R.id.bottomText);//底部的状态提示信息
                    bottomText.setText(str1[i]);

                    TextView drug_state = (TextView) vi.findViewById(R.id.drug_state);//顶部当前药品的状态的步骤提示信息
                    drug_state.setText(str2[i]);
                    ImageView drug_stupimage = (ImageView) vi.findViewById(R.id.drug_stupimage);//当前的步骤数字提示的image
                    drug_stupimage.setImageResource(res1[i]);
                    ImageView drug_infoImage = (ImageView) vi.findViewById(R.id.drug_infoImage);//当前的煎药步骤的图片
                    drug_infoImage.setImageResource(res2[i]);

                    TextView drug_left = (TextView) vi.findViewById(R.id.drug_left);
                    if (i == 0) {//第一个不显示左边线
                        drug_left.setBackground(null);
                    }
                    TextView drug_right = (TextView) vi.findViewById(R.id.drug_right);
                    if (i == state - 1) {//最后一个不显示右边
                        drug_right.setBackground(null);
                    }
                    li.add(vi);
                }
                adapter=new MyDrugStatePagerAdapter(li);
                mViewpagerDrug.setAdapter(adapter);
            }
        }
    }
}
