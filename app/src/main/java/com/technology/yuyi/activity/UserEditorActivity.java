package com.technology.yuyi.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.technology.yuyi.Photo.PhotoPictureUtils;
import com.technology.yuyi.Photo.PhotoRSCode;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.bean_ChangeUserMsg;
import com.technology.yuyi.bean.bean_My_UserMsg;
import com.technology.yuyi.lzh_utils.BitmapTobase64;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.gson;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.toast;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.RoundImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserEditorActivity extends AppCompatActivity implements View.OnClickListener,PhotoPictureUtils.OnSavePictureListener {
    ImageView userInfo_sex_Women,userInfo_sex_man;//男，女的图标
    private ImageView mBack;//返回
    private EditText mAgeEdit;//年龄
    private String type;
    private LinearLayout select_head;//更换头像的view
    private TextView usereditor_textv_cancle,usereditor_textv_picture,usereditor_textv_camera;//弹窗中选择的照片选取方式
    private PopupWindow pop;
    private RoundImageView usereditor_image_userphoto;
    private EditText user_editor_userName;//姓名
    private Bitmap bit;
    private String bit64;
    private String resStr;
    private Boolean isBitChange=false;
    private File outputImage;
    private int SE;//性别标示0女，1男
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0://失败
                    toast.toast_faild(UserEditorActivity.this);
                    break;
                case 1://获取用户信息
                    try{
                        bean_My_UserMsg usMsg= gson.gson.fromJson(resStr,bean_My_UserMsg.class);
                        if ("0".equals(usMsg.getCode())){
                            bean_My_UserMsg.ResultBean bean=usMsg.getResult();
                            user_editor_userName.setText(bean.getTrueName());
                            Picasso.with(UserEditorActivity.this).load(Ip.url_F+bean.getAvatar()).error(R.mipmap.usererr).into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                                    bit64=BitmapTobase64.bitmapToBase64(bitmap);
                                    usereditor_image_userphoto.setImageBitmap(bitmap);
                                }

                                @Override
                                public void onBitmapFailed(Drawable drawable) {
                                    bit64=BitmapTobase64.bitmapToBase64(BitmapFactory.decodeResource(getResources(),R.mipmap.usererr));
                                    usereditor_image_userphoto.setImageResource(R.mipmap.usererr);
                                }

                                @Override
                                public void onPrepareLoad(Drawable drawable) {

                                }
                            });
                            int sx=bean.getGender();
                            SE=bean.getGender();
                            if (sx==0){//nv
                                userInfo_sex_Women.setSelected(true);
                                userInfo_sex_man.setSelected(false);
                            }
                            else if (sx==1){//nan
                                userInfo_sex_Women.setSelected(false);
                                userInfo_sex_man.setSelected(true);
                            }
                            mAgeEdit.setText(bean.getAge()+"");
                        }

                        else {
                            Toast.makeText(UserEditorActivity.this,"获取信息失败：",Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        toast.toast_gsonFaild(UserEditorActivity.this);
//                        Log.e("----gson-----",e.toString());
                        e.printStackTrace();
                    }
                    break;
                case 2://修改／添加用户信息
                    try{
                        bean_ChangeUserMsg changeUserMsg=gson.gson.fromJson(resStr,bean_ChangeUserMsg.class);
                        if ("0".equals(changeUserMsg.getCode())){
                            Toast.makeText(UserEditorActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                            if ("1".equals(type)){
                                startActivity(new Intent(UserEditorActivity.this,MainActivity.class));
                            }
                            finish();
                        }
                        else {
                            Toast.makeText(UserEditorActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        toast.toast_gsonFaild(UserEditorActivity.this);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editor);
        initView();
        type=getIntent().getStringExtra("type");
        getUserData();//获取用户个人信息
    }

    private void initView() {
        //用户名
        user_editor_userName= (EditText) findViewById(R.id.user_editor_userName);
        user_editor_userName.setSelection(user_editor_userName.getText().length());
        user_editor_userName.setOnClickListener(this);

        //性别选择
        userInfo_sex_man= (ImageView) findViewById(R.id.userInfo_sex_man);
        userInfo_sex_man.setOnClickListener(this);
        userInfo_sex_Women= (ImageView) findViewById(R.id.userInfo_sex_Women);
        userInfo_sex_Women.setOnClickListener(this);
        SE=0;//默认选择女
        userInfo_sex_Women.setSelected(true);

        //返回
        mBack = (ImageView) findViewById(R.id.editor_back);
        mBack.setOnClickListener(this);
        //年龄编辑框
        mAgeEdit = (EditText) findViewById(R.id.age_edit);
        mAgeEdit.setSelection(mAgeEdit.getText().length());

        usereditor_image_userphoto= (RoundImageView) findViewById(R.id.usereditor_image_userphoto);

        //头像更换
        select_head= (LinearLayout) findViewById(R.id.select_head);
        select_head.setOnClickListener(this);

        usereditor_image_userphoto= (RoundImageView) findViewById(R.id.usereditor_image_userphoto);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
//            0女1男
            case R.id.userInfo_sex_man://选择男
                SE=1;
                userInfo_sex_man.setSelected(true);
                userInfo_sex_Women.setSelected(false);
                break;
            case R.id.userInfo_sex_Women://选择女
                SE=0;
                userInfo_sex_man.setSelected(false);
                userInfo_sex_Women.setSelected(true);
                break;
            case R.id.editor_back://返回
                if ("0".equals(type)){
                    finish();
                }
                else if ("1".equals(type)){
                    startActivity(new Intent(UserEditorActivity.this,MainActivity.class));
                    finish();
                }
                break;
            case R.id.select_head://上传头像
                showWindowUploading();//
                break;
            case R.id.usereditor_textv_picture://图库选取头像
                if (pop!=null&&pop.isShowing()){
                    pop.dismiss();
                }
                PhotoPictureUtils.getInstance().searchPicture(this);
                break;
            case R.id.usereditor_textv_camera://拍照头像
                if (pop!=null&&pop.isShowing()){
                    pop.dismiss();
                }
                PhotoPictureUtils.getInstance().takePhoto(this);
                break;
            case R.id.usereditor_textv_cancle://取消
                pop.dismiss();
                break;
        }
    }

    //上传头像
    private void showWindowUploading() {
        View vi=LayoutInflater.from(UserEditorActivity.this).inflate(R.layout.usereditor_pop_uploading,null);
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

        RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_user_editor);
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

    //获取个人信息接口
    public void getUserData() {
        Map<String,String> mp=new HashMap<>();
        mp.put("token", user.token);
        okhttp.getCall(Ip.url_F+Ip.interface_UserMsg,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                    handler.sendEmptyMessage(0);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("获取用户个人信息--",resStr);
                    handler.sendEmptyMessage(1);
            }
        });
    }

    //提交用户信息http://localhost:8080/yuyi/personal/save.do?token=C0700876FB2F9BEC156AC039F894E92B&idCard=515251635262&age=26
    public void sendMsg(){
        Map<String,String>mp=new HashMap<>();
        mp.put("token",user.token);
        if (isBitChange){
            Log.e("000000000000","000000000000");
            mp.put("avatar",bit64);
        }
        else {
            mp.put("avatar","");
            Log.e("1111991000000011","1111111111111");
        }
        mp.put("trueName",user_editor_userName.getText().toString());//真实姓名
        String ag=mAgeEdit.getText().toString();
        if (Integer.parseInt(ag)<1|Integer.parseInt(ag)>150){
            Toast.makeText(UserEditorActivity.this,"您填写的年龄不正确",Toast.LENGTH_SHORT).show();
            return;
        }
        mp.put("age",ag);//年龄
        mp.put("gender",""+SE);//性别
        Log.i("---性别--","--------"+SE);
        okhttp.getCall(Ip.url_F+Ip.interface_UserMsgRevise,mp,okhttp.OK_POST).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.sendEmptyMessage(0);
            }
            @Override
            public void onResponse(Response response) throws IOException {
                    resStr=response.body().string();
                    Log.i("修改个人信息----",resStr);
                    handler.sendEmptyMessage(2);
            }
        });
    }
    // 提交／上传用户信息``
    public void Submit(View view) {
         int input=checkInput();
        if(input==0){
            sendMsg();
        }
        else if (input==1){
            Toast.makeText(UserEditorActivity.this,"头像未上传",Toast.LENGTH_SHORT).show();
        }
        else if (input==2){
            Toast.makeText(UserEditorActivity.this,"信息填写不完整",Toast.LENGTH_SHORT).show();
        }

    }
//检查
    private int checkInput() {
        if (!"".equals(bit64)&&!TextUtils.isEmpty(bit64)){
            if (!"".equals(user_editor_userName.getText().toString())&&!TextUtils.isEmpty(user_editor_userName.getText().toString())
                    &&!"".equals(mAgeEdit.getText().toString())&&!TextUtils.isEmpty(mAgeEdit.getText().toString()
            )){
                return 0;
            }
            else {
                return 2;//其他信息不完整
            }
        }
        else {
            return 1;//头像不完整
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (outputImage != null) {
            if (outputImage.exists() && outputImage.isFile()) {
                outputImage.delete();
            }
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            if ("0".equals(type)){
                finish();
            }
            else if ("1".equals(type)){
                startActivity(new Intent(UserEditorActivity.this,MainActivity.class));
                finish();
            }
        }
        return false;
    }

    @Override
    public void onSavePicture(boolean isSuccess, File result) {
        if (isSuccess){
            isBitChange=true;
            bit=BitmapFactory.decodeFile(result.getAbsolutePath());
            bit64=BitmapTobase64.bitmapToBase64(bit);
            usereditor_image_userphoto.setImageBitmap(bit);
        }
        else {
            Toast.makeText(this,"图片保存失败！",Toast.LENGTH_SHORT).show();
        }
    }
}
