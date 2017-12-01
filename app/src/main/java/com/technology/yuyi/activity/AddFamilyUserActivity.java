package com.technology.yuyi.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.technology.yuyi.Photo.PhotoPictureUtils;
import com.technology.yuyi.Photo.PhotoRSCode;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_AddFamilyUser;
import com.technology.yuyi.bean.bean_DeleteFamilyUser;
import com.technology.yuyi.bean.bean_ListFamilyUser;
import com.technology.yuyi.bean.bean_SMScode;
import com.technology.yuyi.lzh_utils.BitmapTobase64;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyDialog;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.RoundImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 0添加,1xiugai
public class AddFamilyUserActivity extends AppCompatActivity implements View.OnClickListener,PhotoPictureUtils.OnSavePictureListener {
    private ImageView mBack;
    private TextView mSure_tv;
    private RoundImageView add_head_tv;
    private File file;
    private TextView usereditor_textv_cancle, usereditor_textv_picture, usereditor_textv_camera;
    private PopupWindow popupWindow;
    private TextView title;
    TextView textvDelete;//删除按钮

    private Bitmap bit;
    private Bitmap btn;
    private String cooki;
    private Headers header;
    private EditText edit_relation, edit_age, edit_name, edit_telnum;
    private CheckBox checkbox;
    private String relation, age, name, telnum;
    private String SMScode;//验证码
    private String bit64;
    private PopupWindow pop;
    private RadioGroup radioGroup;//性别
    private RadioButton add_fami_boy, add_fami_gril;
    private String gender;//性别参数0女1男
    TextView addfamily_getSmsCode;//获取验证码
    EditText addfamily_edit_smsCode;
    TextView addfamily_pop_submit;
    private String resStr;

    private Boolean isBitChange = false;
    private int first = 0;
    //--------------------------------
    private bean_ListFamilyUser.ResultBean userInfo;
    private String type;//0:添加家庭用户，1修改家庭用户

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://连接失败
                    MyDialog.stopDia();
                    toast.toast_faild(AddFamilyUserActivity.this);
                    break;
                case 1://获取验证码
                    try {
                        bean_SMScode code = gson.gson.fromJson(resStr, bean_SMScode.class);
                        String co = code.getCode();
                        if ("0".equals(co)) {
                            SMScode = code.getResult();
                            addfamily_edit_smsCode.setText(SMScode);
                        } else {
                            Toast.makeText(AddFamilyUserActivity.this, "获取验证码失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        toast.toast_gsonFaild(AddFamilyUserActivity.this);
                        Log.e("---gson-1-", e.toString());
                    }
                    break;
                case 2://添加返回
                    try {
                        bean_AddFamilyUser fami = gson.gson.fromJson(resStr, bean_AddFamilyUser.class);
                        if ("0".equals(fami.getCode())) {
                            if ("0".equals(type)) {
                                Toast.makeText(AddFamilyUserActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if ("1".equals(type)) {
                                Toast.makeText(AddFamilyUserActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                userInfo.setAge(Integer.parseInt(age));
                                userInfo.setNickName(relation);
                                userInfo.setBit64(bit64);
                                userInfo.setTrueName(name);
                                if (telnum != null && !"".equals(telnum)) {
                                    userInfo.setTelephone(Long.parseLong(telnum));
                                } else {
                                    userInfo.setTelephone(0);
                                }
                                userInfo.setGender(Integer.parseInt(gender));
                                Intent intent = new Intent();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("user", userInfo);
                                intent.putExtra("user", bundle);
                                setResult(200, intent);
                                finish();
                            }

                        } else {
                            if ("0".equals(type)) {
                                Toast.makeText(AddFamilyUserActivity.this, "添加失败："+fami.getMessage(), Toast.LENGTH_SHORT).show();
                                finish();
                            } else if ("1".equals(type)) {
                                Toast.makeText(AddFamilyUserActivity.this, "修改失败："+fami.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    } catch (Exception e) {
                        toast.toast_gsonFaild(AddFamilyUserActivity.this);
                        Log.e("---gson-2-", e.toString());
                    }
                    break;
                case 3:
                    MyDialog.stopDia();
                    try {
                        bean_DeleteFamilyUser del = gson.gson.fromJson(resStr, bean_DeleteFamilyUser.class);
                        if ("0".equals(del.getCode())) {
                            Toast.makeText(AddFamilyUserActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                            setResult(100,new Intent(AddFamilyUserActivity.this,FamilyUserMessageActivity.class));
                            finish();
                        } else {
                            Toast.makeText(AddFamilyUserActivity.this, "删除失败:"+del.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        toast.toast_gsonFaild(AddFamilyUserActivity.this);
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_user);
        initView();
    }

    public void initView() {
        textvDelete= (TextView) findViewById(R.id.textvDelete);
        textvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser();//删除用户
            }
        });
        radioGroup = (RadioGroup) findViewById(R.id.add_family_group_sex);
        gender = "0";
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.add_fami_boy://男1
                        gender = "1";//性别参数0女1男
                        break;
                    case R.id.add_fami_gril://女0
                        gender = "0";
                        break;
                }
            }
        });
        title = (TextView) findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));
        //返回
        mBack = (ImageView) findViewById(R.id.add_family_back);
        mBack.setOnClickListener(this);
        //确定
        mSure_tv = (TextView) findViewById(R.id.family_add_sure);
        mSure_tv.setOnClickListener(this);


        add_head_tv = (RoundImageView) findViewById(R.id.add_head_tv);
        add_head_tv.setOnClickListener(this);

        //-----------------------------------------------------------
        edit_relation = (EditText) findViewById(R.id.edit_relation);
        edit_age = (EditText) findViewById(R.id.edit_age);
        edit_name = (EditText) findViewById(R.id.edit_name);
        edit_telnum = (EditText) findViewById(R.id.edit_telnum);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        add_fami_gril = (RadioButton) findViewById(R.id.add_fami_gril);
        add_fami_boy = (RadioButton) findViewById(R.id.add_fami_boy);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (edit_telnum.getText() != null && isPhoneNum(edit_telnum.getText().toString())) {
                    telnum = edit_telnum.getText().toString();
                    if (first != 1) {
                        getSMSCode();
                    }

                } else {
                    Toast.makeText(AddFamilyUserActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    checkbox.setChecked(false);
                }
            }
        });


        edit_telnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null) {
                    if (!"".equals(s.toString()) && !TextUtils.isEmpty(s.toString())) {
                        if (isPhoneNum(s.toString())) {
                            telnum = s.toString();
                        } else {
                            checkbox.setChecked(false);
                        }
                    } else {
                        checkbox.setChecked(false);
                    }
                } else {
                    checkbox.setChecked(false);
                }
            }
        });
        first = 0;
        //-------------------------------------------
        Bundle b = getIntent().getBundleExtra("family");
        if (b != null) {
            userInfo = (bean_ListFamilyUser.ResultBean) b.getSerializable("family");
            gender = userInfo.getGender() + "";
            switch (gender) {
                case "0":
                    add_fami_gril.setChecked(true);
                    break;
                case "1":
                    add_fami_boy.setChecked(true);
                    break;
            }
            if (!"".equals(userInfo.getBit64()) && !TextUtils.isEmpty(userInfo.getBit64())) {
                bit64 = userInfo.getBit64();
                byte[] bytes = Base64.decode(userInfo.getBit64(), Base64.DEFAULT);
                add_head_tv.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
            } else {
                Picasso.with(AddFamilyUserActivity.this).load(Uri.parse(Ip.imagePth + userInfo.getAvatar())).error(R.mipmap.usererr).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                        bit = bitmap;
                        bit64 = BitmapTobase64.bitmapToBase64(bit);
                        add_head_tv.setImageBitmap(bitmap);
                        Log.e("--bit64--load--1-", bit64.toString());
                    }

                    @Override
                    public void onBitmapFailed(Drawable drawable) {
                        bit = BitmapFactory.decodeResource(getResources(), R.mipmap.usererr);
                        add_head_tv.setImageBitmap(bit);
                        bit64 = BitmapTobase64.bitmapToBase64(bit);
                        Log.e("--bit64--load--2-", bit64.toString());
                    }

                    @Override
                    public void onPrepareLoad(Drawable drawable) {

                    }
                });
            }


            edit_relation.setText(userInfo.getNickName());
            edit_age.setText(userInfo.getAge() + "");
            edit_name.setText(userInfo.getTrueName());
            if (isPhoneNum(userInfo.getTelephone() + "")) {
                edit_telnum.setText(userInfo.getTelephone() + "");
            }
            if (userInfo.getRole() == 1) {//1的时候同意查看
                first = 1;
                checkbox.setChecked(true);
            }
        }
        type = getIntent().getStringExtra("type");
        if (!"1".equals(type)) {// 0添加,1xiugai
            type = "0";
            textvDelete.setVisibility(View.GONE);//添加时隐藏删除按钮
        }
    }

    //删除用户http://192.168.1.55:8080/yuyi/homeuser/delete.do?token=6DD620E22A92AB0AED590DB66F84D064&id=123
    private void deleteUser() {
        MyDialog.showDialog(this);
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
                resStr = response.body().string();
                handler.sendEmptyMessage(3);
                Log.i("删除家庭用户--", resStr);
            }
        });
    }

    //判断是否输入的为手机号
    public boolean isPhoneNum(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //返回
        if (id == mBack.getId()) {
            finish();
        } else if (id == mSure_tv.getId()) {//确定
            if (!"".equals(bit64) && !TextUtils.isEmpty(bit64)) {
                //检查用户输入的信息是否完整
                if (checkInput()) {
                    sendMsg();
                } else {
                    Toast.makeText(AddFamilyUserActivity.this, "信息填写不完整", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddFamilyUserActivity.this, "您还没有上传头像", Toast.LENGTH_SHORT).show();
            }

        } else if (id == add_head_tv.getId()) {//上传头像
            showWindowUploading();
        } else if (id == usereditor_textv_picture.getId()) {
            if (pop != null) {
                pop.dismiss();
                PhotoPictureUtils.getInstance().searchPicture(this);
            }

        } else if (id == usereditor_textv_camera.getId()) {
            PhotoPictureUtils.getInstance().takePhoto(this);


        } else if (id == usereditor_textv_cancle.getId()) {
            if (pop != null) {
                pop.dismiss();
            }
        }
    }

    //检查用户输入信息是否完整
    private boolean checkInput() {
        relation = edit_relation.getText().toString();
        age = edit_age.getText().toString();
        name = edit_name.getText().toString();
        if (!"".equals(relation) && !TextUtils.isEmpty(relation) &&
                !"".equals(age) && !TextUtils.isEmpty(age) &&
                !"".equals(name) && !TextUtils.isEmpty(name)) {
            return true;
        }
        return false;
    }
    private void sendMsg() {
        Map<String, String> mp = new HashMap<>();
        mp.put("token", user.userPsd);
        mp.put("nickName", relation);//家庭关系
        mp.put("trueName", name);
        if (Integer.parseInt(age) < 1 | Integer.parseInt(age) > 150) {
            Toast.makeText(AddFamilyUserActivity.this, "您填写的年龄不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        mp.put("age", age);

        mp.put("gender", gender);
        Log.e("bit64----map---", bit64.toString());
        telnum = edit_telnum.getText().toString();
        checkbox.setChecked(false);
        if (checkbox.isChecked()) {//当选中了手机号可以查看时，验证码不能为空，手机号不能为空
            if (!"".equals(telnum) && !TextUtils.isEmpty(telnum) && isPhoneNum(telnum) && !TextUtils.isEmpty(SMScode) && !"".equals(SMScode)) {
                mp.put("vcode", SMScode);
                mp.put("telephone", telnum);
            } else {
                Toast.makeText(AddFamilyUserActivity.this, "手机号或验证码不完整", Toast.LENGTH_SHORT).show();
                checkbox.setChecked(false);
                return;
            }
        } else {
            if (!"".equals(telnum) && !TextUtils.isEmpty(telnum) && isPhoneNum(telnum)) {
                mp.put("telephone", telnum);
                Log.i("----------", telnum);
            }
        }
        String url;
        if ("1".equals(type)) {//修改信息的时候多一个id字段
            mp.put("id", userInfo.getId() + "");
            if (isBitChange) {
                mp.put("avatar", bit64);
            } else {
                mp.put("avatar", "");
            }
        } else {
            mp.put("avatar", bit64);
            }
        okhttp.getCallCookie(Ip.url + Ip.interface_addFamilyUser, mp, cooki).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr = response.body().string();
                handler.sendEmptyMessage(2);
                Log.i("添加家庭用户--", resStr);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        first = 0;
    }

    private void showWindowUploading() {
        View vi = LayoutInflater.from(AddFamilyUserActivity.this).inflate(R.layout.usereditor_pop_uploading, null);
        LinearLayout usereditor_pop_layout = (LinearLayout) vi.findViewById(R.id.usereditor_pop_layout);
        usereditor_textv_cancle = (TextView) vi.findViewById(R.id.usereditor_textv_cancle);
        usereditor_textv_picture = (TextView) vi.findViewById(R.id.usereditor_textv_picture);
        usereditor_textv_camera = (TextView) vi.findViewById(R.id.usereditor_textv_camera);
        usereditor_textv_cancle.setOnClickListener(this);
        usereditor_textv_camera.setOnClickListener(this);
        usereditor_textv_picture.setOnClickListener(this);
        pop = new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.6f;
        getWindow().setAttributes(params);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(vi);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);

        RelativeLayout parent = (RelativeLayout) findViewById(R.id.ra);
        pop.setAnimationStyle(R.style.popup3_anim);
        pop.showAtLocation(parent, Gravity.CENTER, 0, 0);

        ViewGroup.LayoutParams param = usereditor_pop_layout.getLayoutParams();
        param.width = (int) (getWindowManager().getDefaultDisplay().getWidth() * 0.8);
        usereditor_pop_layout.setLayoutParams(param);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== PhotoRSCode.requestCode_SearchPermission){//选取图片的权限请求
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                PhotoPictureUtils.getInstance().searchPicture(this);
            }
            else {
                Toast.makeText(this,"请打开存储卡权限！",Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode==PhotoRSCode.requestCode_CameraPermission){//拍照的权限请求
            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
                PhotoPictureUtils.getInstance().takePhoto(this);
            }
            else {
                Toast.makeText(this,"请打开相机权限！",Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case PhotoRSCode.requestCode_Search://相册选取返回
                    PhotoPictureUtils.getInstance().savaPictureSearch(data.getData(),this,this);
                    break;
                case PhotoRSCode.requestCode_Camera://拍照
                    //cameraFile为保存后的文件，mImg：需要显示图片的ImageView
                    PhotoPictureUtils.getInstance().savaPictureCamera(this,this);
                    break;
            }
        }
    }


    @Override
    public void onSavePicture(boolean isSuccess, File result) {
        if (isSuccess){
            isBitChange=true;
            bit=BitmapFactory.decodeFile(result.getAbsolutePath());
            bit64=BitmapTobase64.bitmapToBase64(bit);
            add_head_tv.setImageBitmap(bit);
        }
        else {
            Toast.makeText(this,"图片保存失败！",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (file != null) {
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }

    }

    //获取验证码的方法
    public void getSMSCode() {
        if (checkbox.isChecked()) {
            View v = LayoutInflater.from(AddFamilyUserActivity.this).inflate(R.layout.addfamilyuser_pop_smscode, null);
            addfamily_getSmsCode = (TextView) v.findViewById(R.id.addfamily_getSmsCode);//获取验证码
            addfamily_edit_smsCode = (EditText) v.findViewById(R.id.addfamily_edit_smsCode);
            addfamily_pop_submit = (TextView) v.findViewById(R.id.addfamily_pop_submit);
            addfamily_getSmsCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//获取验证码
                    Map<String, String> mp = new HashMap<String, String>();
                    mp.put("id", telnum);
                    okhttp.getCall(Ip.url + Ip.interface_SmsCode, mp, okhttp.OK_GET).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            handler.sendEmptyMessage(0);
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            Log.i("------", response.header("Set-Cookie"));
                            header = response.headers();
                            for (String s : header.names()) {
                                Log.i("name--" + s, "--" + header.get(s));
                            }
                            cooki = header.get("Set-Cookie");
                            resStr = response.body().string();
                            Log.i("添加家庭用户验证码---", resStr);
                            Log.i("添加家庭用户验证码---", response.toString());
                            handler.sendEmptyMessage(1);
                        }
                    });
                }
            });

            addfamily_pop_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!"".equals(addfamily_edit_smsCode.getText()) && !TextUtils.isEmpty(addfamily_edit_smsCode.getText().toString())) {
                        SMScode = addfamily_edit_smsCode.getText().toString();
                        popupWindow.dismiss();
                    } else {
                        checkbox.setChecked(false);
                        Toast.makeText(AddFamilyUserActivity.this, "验证码不正确", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            popupWindow = new PopupWindow();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams params = getWindow().getAttributes();
            params.alpha = 0.6f;
            getWindow().setAttributes(params);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popupWindow.setContentView(v);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            RelativeLayout parent = (RelativeLayout) findViewById(R.id.activity_add_family_user);
            popupWindow.setAnimationStyle(R.style.popup_anim);
            popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.alpha = 1f;
                    getWindow().setAttributes(params);
                }
            });
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);

    }
}
