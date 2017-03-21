package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.Ms_druginfo_popAdapter;
import com.technology.yuyi.bean.bean_AddFamilyUser;
import com.technology.yuyi.bean.bean_ListFamilyUser;
import com.technology.yuyi.bean.bean_SMScode;
import com.technology.yuyi.lzh_utils.BitmapTobase64;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.MyGridView;
import com.technology.yuyi.lzh_utils.ResCode;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.RoundImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Intent intent=  new Intent(this, AddFamilyUserActivity.class);
//        intent.putExtra("title","修改家庭用户");
//        Bundle b=new Bundle();
//        b.putSerializable("family",userInfo);
//        intent.putExtra("family",b);
//        startActivity(intent);
public class AddFamilyUserActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mSure_tv;
    private RoundImageView add_head_tv;
    private File file;
    private TextView usereditor_textv_cancle,usereditor_textv_picture,usereditor_textv_camera;
    private PopupWindow popupWindow;
    private TextView title;

    private Bitmap bit;



    private EditText edit_relation,edit_age,edit_name,edit_telnum;
    private CheckBox checkbox;
    private String relation,age,name,telnum;
    private String SMScode;//验证码
    private String bit64;
    private PopupWindow pop;
    TextView addfamily_getSmsCode;//获取验证码
    EditText addfamily_edit_smsCode;
    TextView addfamily_pop_submit;
    private String resStr;


    //--------------------------------
    private bean_ListFamilyUser.ResultBean userInfo;
    private String type;//0:添加家庭用户，1修改家庭用户

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://连接失败
                    toast.toast_faild(AddFamilyUserActivity.this);
                    break;
                case 1://获取验证码
                    try{
                       bean_SMScode code= gson.gson.fromJson(resStr,bean_SMScode.class);
                        String co=code.getCode();
                        if ("0".equals(co)){
                            SMScode=code.getResult();
                            addfamily_edit_smsCode.setText(SMScode);
                        }
                        else {
                            Toast.makeText(AddFamilyUserActivity.this,"获取验证码失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(AddFamilyUserActivity.this);
                        Log.e("---gson-1-",e.toString());
                    }
                    break;
                case 2://添加返回
                    try{
                        bean_AddFamilyUser fami=gson.gson.fromJson(resStr,bean_AddFamilyUser.class);
                        if ("0".equals(fami.getCode())){
                            if ("0".equals(type)){
                                Toast.makeText(AddFamilyUserActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                           else if ("1".equals(type)){
                                Toast.makeText(AddFamilyUserActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            if ("0".equals(type)){
                                Toast.makeText(AddFamilyUserActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else if ("1".equals(type)){
                                Toast.makeText(AddFamilyUserActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(AddFamilyUserActivity.this);
                        Log.e("---gson-2-",e.toString());
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
        title= (TextView) findViewById(R.id.title);
        title.setText(getIntent().getStringExtra("title"));
        //返回
        mBack = (ImageView) findViewById(R.id.add_family_back);
        mBack.setOnClickListener(this);
        //确定
        mSure_tv= (TextView) findViewById(R.id.family_add_sure);
        mSure_tv.setOnClickListener(this);


        add_head_tv= (RoundImageView) findViewById(R.id.add_head_tv);
        add_head_tv.setOnClickListener(this);

        //-----------------------------------------------------------
        edit_relation= (EditText) findViewById(R.id.edit_relation);
        edit_age= (EditText) findViewById(R.id.edit_age);
        edit_name= (EditText) findViewById(R.id.edit_name);
        edit_telnum= (EditText) findViewById(R.id.edit_telnum);
        checkbox= (CheckBox) findViewById(R.id.checkbox);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (edit_telnum.getText()!=null&&isPhoneNum(edit_telnum.getText().toString())){
                    telnum=edit_telnum.getText().toString();
                    getSMSCode();
                }
                else {
                    Toast.makeText(AddFamilyUserActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
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
                if (s!=null){
                    if (!"".equals(s.toString())&&!TextUtils.isEmpty(s.toString())){
                        if (isPhoneNum(s.toString())){
                            telnum=s.toString();
                        }
                        else {
                            checkbox.setChecked(false);
                        }
                    } else {
                        checkbox.setChecked(false);
                    }
                }
             else {
                    checkbox.setChecked(false);
                }
            }
        });

        //-------------------------------------------
        Bundle b=getIntent().getBundleExtra("family");
        if (b!=null){
            userInfo= (bean_ListFamilyUser.ResultBean) b.getSerializable("family");
            Picasso.with(AddFamilyUserActivity.this).load(Uri.parse(Ip.imagePth_F+userInfo.getAvatar())).error(R.mipmap.logo).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                    bit=bitmap;
                    bit64=BitmapTobase64.bitmapToBase64(bit);
                    add_head_tv.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable drawable) {
                        bit= BitmapFactory.decodeResource(getResources(),R.mipmap.logo);
                        add_head_tv.setImageBitmap(bit);
                    bit64=BitmapTobase64.bitmapToBase64(bit);
                }

                @Override
                public void onPrepareLoad(Drawable drawable) {

                }
            });

            edit_relation.setText(userInfo.getNickName());
            edit_age.setText(userInfo.getAge()+"");
            edit_name.setText(userInfo.getTrueName());
            if (isPhoneNum(userInfo.getTelephone()+"")){
                edit_telnum.setText(userInfo.getTelephone()+"");
            }
            type=getIntent().getStringExtra("type");
            if (!"1".equals(type)){//1xiugai 0添加
                type="0";
            }

        }

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
        }else if (id==mSure_tv.getId()){//确定
            if (!"".equals(bit64)&&!TextUtils.isEmpty(bit64)){
                //检查用户输入的信息是否完整
                if (checkInput()){
                   sendMsg();

                }
                else {
                    Toast.makeText(AddFamilyUserActivity.this,"信息填写不完整",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(AddFamilyUserActivity.this,"您还没有上传头像",Toast.LENGTH_SHORT).show();
            }

        }
        else if (id==add_head_tv.getId()){//上传头像
            showWindowUploading();
        }
        else if (id==usereditor_textv_picture.getId()){
            if (pop!=null) {
                pop.dismiss();
                SearchPhoto();
            }

        }
        else if (id==usereditor_textv_camera.getId()){

                TakePhoto();


        }
        else if (id==usereditor_textv_cancle.getId()){
                if (pop!=null){
                    pop.dismiss();
                }
        }
    }
    //检查用户输入信息是否完整
    private boolean checkInput() {
        relation=edit_relation.getText().toString();
        age=edit_age.getText().toString();
        name=edit_name.getText().toString();

        if (!"".equals(relation)&&!TextUtils.isEmpty(relation)&&
                !"".equals(age)&&!TextUtils.isEmpty(age)&&
                !"".equals(name)&&!TextUtils.isEmpty(name)){
            return true;
        }
        return false;
    }

    //确定上传家庭用户信息

  //token=6DD620E22A92AB0AED590DB66F84D064&nickName=aaab&trueName=bbba&age=12&vcode=212637&telephone=13712345678

//    http://192.168.1.55:8080/yuyi/homeuser/save.do?
    // token=6DD620E22A92AB0AED590DB66F84D064
    // &nickName=aaab
    // &trueName=bbba
    // &age=12
    // &vcode=212637
    // &telephone=13712345678
    private void sendMsg() {
        Map<String,String> mp=new HashMap<>();
        mp.put("token", user.userPsd);  mp.put("nickName",relation);//家庭关系
        mp.put("trueName",name);  mp.put("age",age);
        mp.put("avatar",bit64);
        telnum=edit_telnum.getText().toString();
        if (checkbox.isChecked()){//当选中了手机号可以查看时，验证码不能为空，手机号不能为空
            if (!"".equals(telnum)&&!TextUtils.isEmpty(telnum)&&isPhoneNum(telnum)&&!TextUtils.isEmpty(SMScode)&&!"".equals(SMScode)){
                mp.put("vcode",SMScode);
                mp.put("telephone",telnum);
            }
            else {
                Toast.makeText(AddFamilyUserActivity.this,"手机号或验证码不完整",Toast.LENGTH_SHORT).show();
            }
        }
        else{
            if (!"".equals(telnum)&&!TextUtils.isEmpty(telnum)&&isPhoneNum(telnum)){
              mp.put("telephone",telnum);
                Log.i("----------",telnum);
            }
        }
        String url;
        if ("1".equals(type)){//修改信息的时候多一个id字段
            mp.put("id",userInfo.getId()+"");
        }
        okhttp.getCall(Ip.url+ Ip.interface_addFamilyUser,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                resStr=response.body().string();
                handler.sendEmptyMessage(2);
                Log.i("添加家庭用户--",resStr);
            }
        });
    }


    private void showWindowUploading() {
        View vi= LayoutInflater.from(AddFamilyUserActivity.this).inflate(R.layout.usereditor_pop_uploading,null);
        LinearLayout usereditor_pop_layout= (LinearLayout) vi.findViewById(R.id.usereditor_pop_layout);
        usereditor_textv_cancle= (TextView) vi.findViewById(R.id.usereditor_textv_cancle);
        usereditor_textv_picture= (TextView) vi.findViewById(R.id.usereditor_textv_picture);
        usereditor_textv_camera= (TextView) vi.findViewById(R.id.usereditor_textv_camera);
        usereditor_textv_cancle.setOnClickListener(this);
        usereditor_textv_camera.setOnClickListener(this);
        usereditor_textv_picture.setOnClickListener(this);
        pop=new PopupWindow();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.alpha=0.6f;
        getWindow().setAttributes(params);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        pop.setContentView(vi);
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);

        RelativeLayout parent= (RelativeLayout) findViewById(R.id.ra);
        pop.setAnimationStyle(R.style.popup3_anim);
        pop.showAtLocation(parent, Gravity.CENTER, 0,0);

        ViewGroup.LayoutParams param=usereditor_pop_layout.getLayoutParams();
        param.width=(int)(getWindowManager().getDefaultDisplay().getWidth()*0.8);
        usereditor_pop_layout.setLayoutParams(param);
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams params=getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });
    }


    //浏览图片库
    private void TakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file=new File(getExternalFilesDir("DCIM").getAbsolutePath(),System.currentTimeMillis()+".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, ResCode.Request_takePhoto);
    }


    //拍照
    private void SearchPhoto() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, ResCode.Request_searchPhoto);
    }
    //图片裁剪
    public void cutPhoto(Uri uri){
        Uri u=uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        Log.i("uri=2==", uri.getPath());
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);//宽度
        intent.putExtra("outputY", 300);//高度
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent,ResCode.Request_cutPhoto);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            switch (requestCode) {
                case ResCode.Request_searchPhoto:
                    if (data != null) {
                        Uri uri = data.getData();//获取选中的图片Uri
                        if (uri != null) {
                            cutPhoto(uri);
                        } else {
                            Toast.makeText(AddFamilyUserActivity.this, "无法获取到图片路径", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddFamilyUserActivity.this, "从图库中选择照片失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ResCode.Request_takePhoto:
                    cutPhoto(Uri.fromFile(file));
                    break;
                case ResCode.Request_cutPhoto:
                    if (data != null) {
                        Bitmap btm = data.getExtras().getParcelable("data");
                        if (btm != null) {
                            add_head_tv.setImageBitmap(btm);
                            bit = data.getExtras().getParcelable("data");
                            if (bit != null) {
                                add_head_tv.setImageBitmap(bit);
                                bit64= BitmapTobase64.bitmapToBase64(bit);
                                if (pop != null) {
                                    pop.dismiss();
                                }
                            } else {
                                Toast.makeText(AddFamilyUserActivity.this, "图片裁剪失败", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddFamilyUserActivity.this, "图片裁剪失败", Toast.LENGTH_SHORT).show();

                        }
                        break;
                    } else {
                        Log.i("resultCode--", resultCode + "");
                    }
            }
        }}
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
        if (checkbox.isChecked()){
            View v=LayoutInflater.from(AddFamilyUserActivity.this).inflate(R.layout.addfamilyuser_pop_smscode, null);
            addfamily_getSmsCode= (TextView) v.findViewById(R.id.addfamily_getSmsCode);//获取验证码
            addfamily_edit_smsCode= (EditText) v.findViewById(R.id.addfamily_edit_smsCode);
            addfamily_pop_submit= (TextView) v.findViewById(R.id.addfamily_pop_submit);
            addfamily_getSmsCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//获取验证码
                    Map<String,String>mp=new HashMap<String, String>();
                    mp.put("id",telnum);
                    okhttp.getCall(Ip.url+Ip.interface_SmsCode,mp,okhttp.OK_GET).enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            handler.sendEmptyMessage(0);
                        }
                        @Override
                        public void onResponse(Response response) throws IOException {
                            resStr=response.body().string();
                            Log.i("添加家庭用户验证码---",resStr);
                            handler.sendEmptyMessage(1);
                        }
                    });
                }
            });

            addfamily_pop_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!"".equals(addfamily_edit_smsCode.getText())&&!TextUtils.isEmpty(addfamily_edit_smsCode.getText().toString())){
                        SMScode=addfamily_edit_smsCode.getText().toString();
                        popupWindow.dismiss();
                    }
                    else {
                        checkbox.setChecked(false);
                        Toast.makeText(AddFamilyUserActivity.this,"验证码不正确",Toast.LENGTH_SHORT).show();
                    }
                }
            });


            popupWindow=new PopupWindow();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            WindowManager.LayoutParams params=getWindow().getAttributes();
            params.alpha=0.6f;
            getWindow().setAttributes(params);
            popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            popupWindow.setContentView(v);
            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(000, 255, 255, 255)));
            popupWindow.setTouchable(true);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_add_family_user);
            popupWindow.setAnimationStyle(R.style.popup_anim);
            popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0,0);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                    WindowManager.LayoutParams params=getWindow().getAttributes();
                    params.alpha=1f;
                    getWindow().setAttributes(params);
                }
            });
        }

    }




}
