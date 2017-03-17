package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
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

import com.technology.yuyi.R;
import com.technology.yuyi.lzh_utils.ResCode;
import com.technology.yuyi.myview.RoundImageView;

import java.io.File;

public class UserEditorActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout mSex;
    private AlertDialog.Builder mBuilder;
    private AlertDialog mAlertDialog;
    private View mSexAlertView;
    private ImageView mBack;
    private EditText mAgeEdit;
    private EditText mNikName;
    private EditText mAdEdit;
    private EditText mAddressEdit;

    private RelativeLayout select_head;
    private TextView usereditor_textv_cancle,usereditor_textv_picture,usereditor_textv_camera;
    private PopupWindow pop;
    private RoundImageView usereditor_image_userphoto;
    private File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_editor);
        initView();

    }

    private void initView() {
        //选择性别
        mSex = (RelativeLayout) findViewById(R.id.user_sex_rl);
        mSex.setOnClickListener(this);
        mBuilder = new AlertDialog.Builder(this);
        mAlertDialog = mBuilder.create();
        mSexAlertView = LayoutInflater.from(this).inflate(R.layout.sex_alert_box, null);
        mAlertDialog.setView(mSexAlertView);
        //返回
        mBack = (ImageView) findViewById(R.id.editor_back);
        mBack.setOnClickListener(this);
        //年龄编辑框
        mAgeEdit = (EditText) findViewById(R.id.age_edit);
        mAgeEdit.setSelection(mAgeEdit.getText().length());

        select_head= (RelativeLayout) findViewById(R.id.select_head);
        select_head.setOnClickListener(this);

        usereditor_image_userphoto= (RoundImageView) findViewById(R.id.usereditor_image_userphoto);
        //昵称
        mNikName=(EditText) findViewById(R.id.nikname_edit);
        mNikName.setSelection(mNikName.getText().length());
        //省份证号
        mAdEdit=(EditText) findViewById(R.id.ad_edit);
        mAdEdit.setSelection(mAdEdit.getText().length());
        //籍贯
        mAddressEdit=(EditText) findViewById(R.id.jg_edit);
        mAddressEdit.setSelection(mAddressEdit.getText().length());

        select_head= (RelativeLayout) findViewById(R.id.select_head);
        select_head.setOnClickListener(this);

        usereditor_image_userphoto= (RoundImageView) findViewById(R.id.usereditor_image_userphoto);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.user_sex_rl://点击个人信息编辑
                mAlertDialog.show();
            setAlertWidth();
                break;
            case R.id.editor_back://返回
                finish();
                break;
            case R.id.select_head://上传头像
                showWindowUploading();//
                break;

            case R.id.usereditor_textv_picture://图库选取头像
                SearchPhoto();
                break;
            case R.id.usereditor_textv_camera://拍照头像
                TakePhoto();
                break;
            case R.id.usereditor_textv_cancle://取消
                pop.dismiss();
                break;
        }
    }


    //设置alert宽度
    public void setAlertWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager m = getWindowManager();
        m.getDefaultDisplay().getMetrics(dm);
        android.view.WindowManager.LayoutParams p = mAlertDialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (dm.widthPixels * (0.7));
        mAlertDialog.getWindow().setAttributes(p);//设置生效
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


    //浏览图片库
    private void TakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file=new File(getExternalFilesDir("DCIM").getAbsolutePath(),System.currentTimeMillis()+".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(file));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent,ResCode.Request_takePhoto);
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
                            Toast.makeText(UserEditorActivity.this,"无法获取到图片路径",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(UserEditorActivity.this,"从图库中选择照片失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case ResCode.Request_takePhoto:
                    cutPhoto(Uri.fromFile(file));
                    break;
                case ResCode.Request_cutPhoto:
                        if (data!=null){
                            Bitmap btm=data.getExtras().getParcelable("data");
                            if (btm!=null){
                                usereditor_image_userphoto.setImageBitmap(btm);
                                if (pop!=null){
                                    pop.dismiss();
                                }
                            }
                            else {
                                Toast.makeText(UserEditorActivity.this,"图片裁剪失败",Toast.LENGTH_SHORT).show();
                            }
                        }

                    else{
                            Toast.makeText(UserEditorActivity.this,"图片裁剪失败",Toast.LENGTH_SHORT).show();

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
