package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.technology.yuyi.R;
import com.technology.yuyi.lzh_utils.Ip;
import com.technology.yuyi.lzh_utils.ResCode;
import com.technology.yuyi.lzh_utils.okhttp;
import com.technology.yuyi.lzh_utils.user;
import com.technology.yuyi.myview.RoundImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddFamilyUserActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mSure_tv;
    private RoundImageView add_head_tv;
    private File file;
    private TextView usereditor_textv_cancle,usereditor_textv_picture,usereditor_textv_camera;
    private PopupWindow pop;
    private Bitmap bit;

    private EditText edit_relation,edit_age,edit_name,edit_telnum;
    private CheckBox checkbox;
    private String relation,age,name,telnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_family_user);
        initView();
    }

    public void initView() {
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

                }
                else {
                    Toast.makeText(AddFamilyUserActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                    checkbox.setChecked(false);
                }
            }
        });

    }
    //判断是否输入的为手机号
    public boolean isPhoneNum(String str) {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
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
          //检查用户输入的信息是否完整
           if (checkInput()){
               sendMsg();//上传信息
           }
            else {
               Toast.makeText(AddFamilyUserActivity.this,"信息填写不完整",Toast.LENGTH_SHORT).show();
           }
        }
        else if (id==add_head_tv.getId()){//上传头像
            showWindowUploading();
        }
        else if (id==usereditor_textv_picture.getId()){
            SearchPhoto();
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
//        private EditText edit_relation,edit_age,edit_name,edit_telnum;
//        private CheckBox checkbox;
//        private String relation,age,name,telnum;
        relation=edit_relation.getText().toString();
        age=edit_age.getText().toString();
        name=edit_name.getText().toString();
        telnum=edit_telnum.getText().toString();
        return false;
    }

    //确定上传家庭用户信息
    private void sendMsg() {
        Map<String,String> mp=new HashMap<>();
//        参数：
////令牌
//        String token
////家人手机号
//        Long familyId
////称呼
//        String nickName
//        15:54
//        李朋伟
//                朋伟
////真实姓名
//        private String trueName;
//        //年龄
//        private Integer age;
//        李朋伟
//                朋伟
////头像
//        private String avatar;
        mp.put("token", user.userPsd);  mp.put("familyId", user.userPsd);
        mp.put("nickName", user.userPsd);  mp.put("trueName", user.userPsd);
        mp.put("age", user.userPsd);  mp.put("avatar", user.userPsd);
        okhttp.getCall(Ip.url+ Ip.interface_addFamilyUser,mp,okhttp.OK_GET).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {

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

        RelativeLayout parent= (RelativeLayout) findViewById(R.id.activity_add_family_user);
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
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case ResCode.Request_searchPhoto:
                    if (data!=null){
                        Uri uri=data.getData();//获取选中的图片Uri
                        if (uri!=null){
                            cutPhoto(uri);
                        }
                        else {
                            Toast.makeText(AddFamilyUserActivity.this,"无法获取到图片路径",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(AddFamilyUserActivity.this,"从图库中选择照片失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ResCode.Request_takePhoto:
                    cutPhoto(Uri.fromFile(file));
                    break;
                case ResCode.Request_cutPhoto:
                    if (data!=null){
                        bit=data.getExtras().getParcelable("data");
                        if (bit!=null){
                            add_head_tv.setImageBitmap(bit);
                            if (pop!=null){
                                pop.dismiss();
                            }
                        }
                        else {
                            Toast.makeText(AddFamilyUserActivity.this,"图片裁剪失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    else{
                        Toast.makeText(AddFamilyUserActivity.this,"图片裁剪失败",Toast.LENGTH_SHORT).show();

                    }
                    break;
            }
        }
        else {
            Log.i("resultCode--",resultCode+"");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (file!=null){
            if (file.exists()&&file.isFile()){
                file.delete();
            }
        }

    }
}
