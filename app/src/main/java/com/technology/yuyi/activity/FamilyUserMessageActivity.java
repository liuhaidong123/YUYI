package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_DeleteFamilyUser;
import com.technology.yuyi.bean.bean_ListFamilyUser;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.RoundImageView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//if (position==0){
//        intent.putExtra("type","0");
//        }
//        else {
//        intent.putExtra("type","1");
//        }
//0：用户本人的，1用户家人的
//家庭用户信息页面
public class FamilyUserMessageActivity extends AppCompatActivity implements View.OnClickListener {
    private String type;
    private ImageView mBack;//返回
    private RelativeLayout mMyData_rl;//我的数据分析
    private RelativeLayout mMyFiles_rl;//我的病历档案
    private ImageView mEditImg;
    private Button mDelete_btn;//删除用户
    private AlertDialog.Builder mBuilder;//删除设备的弹框
    private AlertDialog mAlertDialog;
    private View mAlertView;
    private TextView mSure_btn;
    private TextView mCancel_btn;
    private RoundImageView user_img_head;//头像
    private TextView user_name_tv;//名称（与关系）;
    private TextView user_name_age;//年龄
    private TextView user_telnum;//电话
    private bean_ListFamilyUser.ResultBean userInfo;
    private String resultStr;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    toast.toast_faild(FamilyUserMessageActivity.this);
                    break;
                case 1:
                    try {
                        bean_DeleteFamilyUser del = gson.gson.fromJson(resultStr, bean_DeleteFamilyUser.class);
                        if ("0".equals(del.getCode())) {
                            Toast.makeText(FamilyUserMessageActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(FamilyUserMessageActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        toast.toast_gsonFaild(FamilyUserMessageActivity.this);
                    }
                    break;
            }
        }
    };

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
            user_name_tv.setText(userInfo.getTrueName() + "（" + userInfo.getNickName() + "）");
            user_name_age.setText(userInfo.getAge() + "岁");
            user_telnum.setText(userInfo.getTelephone() + "");
        }
    }

    public void initView() {
        user_img_head = (RoundImageView) findViewById(R.id.user_img_head);
        user_name_tv = (TextView) findViewById(R.id.user_name_tv);
        user_name_age = (TextView) findViewById(R.id.user_name_age);
        user_telnum = (TextView) findViewById(R.id.user_telnum);

        //返回
        mBack = (ImageView) findViewById(R.id.family_user_back);
        mBack.setOnClickListener(this);
        //我的数据分析
        mMyData_rl = (RelativeLayout) findViewById(R.id.my_data_rl);
        mMyData_rl.setOnClickListener(this);

        //我的病历档案
        mMyFiles_rl = (RelativeLayout) findViewById(R.id.my_files_rl);
        mMyFiles_rl.setOnClickListener(this);
        //编辑图片
        mEditImg = (ImageView) findViewById(R.id.edit_img);
        mEditImg.setOnClickListener(this);
        //删除用户
        mDelete_btn = (Button) findViewById(R.id.delete_user);
        mDelete_btn.setOnClickListener(this);

        //删除提示弹框
        mAlertView = LayoutInflater.from(this).inflate(R.layout.alert_delete_user_box, null);
        //确定按钮
        mSure_btn = (TextView) mAlertView.findViewById(R.id.alert_sure);
        mSure_btn.setOnClickListener(this);
        //取消按钮
        mCancel_btn = (TextView) mAlertView.findViewById(R.id.alert_cancel);
        mCancel_btn.setOnClickListener(this);
        //长安设备的弹框
        mBuilder = new AlertDialog.Builder(this);
        mAlertDialog = mBuilder.create();
        mAlertDialog.setView(mAlertView);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBack.getId()) {//返回
            finish();
        } else if (id == mMyData_rl.getId()) {//我的数据分析
            Intent intent = new Intent(this, MyDataAnalyseActivity.class);
            intent.putExtra("id", userInfo.getId());
            startActivity(intent);
        } else if (id == mEditImg.getId()) {//编辑图片(将用户信息传过去)
            Intent intent = new Intent(this, AddFamilyUserActivity.class);
            intent.putExtra("title", "修改家庭用户");
            intent.putExtra("type", "1");
            Bundle b = new Bundle();
            b.putSerializable("family", userInfo);
            intent.putExtra("family", b);
            startActivityForResult(intent, 100);
        } else if (id == mMyFiles_rl.getId()) {//病历档案(将用户信息传过去)
            if ("0".equals(type)) {//用户的电子病历
                Intent intent = new Intent();
                intent.putExtra("type", "0");
                intent.setClass(this, ElectronicMessActivity.class);
                startActivity(intent);
            } else if ("1".equals(type)) {//用户家人的电子病历
                Intent intent = new Intent();
                intent.putExtra("type", "1");
                intent.putExtra("id", "" + userInfo.getId());
                intent.setClass(this, ElectronicMessActivity.class);
                startActivity(intent);
            }

        } else if (id == mDelete_btn.getId()) {//删除用户
            mAlertDialog.show();
        } else if (id == mSure_btn.getId()) {//确定按钮
            deleteUser();
            mAlertDialog.dismiss();
        } else if (id == mCancel_btn.getId()) {//取消按钮
            mAlertDialog.dismiss();
        }
    }

    //删除用户http://192.168.1.55:8080/yuyi/homeuser/delete.do?token=6DD620E22A92AB0AED590DB66F84D064&id=123
    private void deleteUser() {
        Map<String, String> mp = new HashMap<>();
        mp.put("token", user.userPsd);
        mp.put("id", userInfo.getId() + "");
        okhttp.getCall(Ip.url + Ip.interfce_DeleteFamilyUser, mp, okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resultStr = response.body().string();
                handler.sendEmptyMessage(1);
                Log.i("删除家庭用户--", resultStr);
            }
        });
    }

    //处里修改信息后的显示问题
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            if (requestCode == 100) {
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
    }
}
