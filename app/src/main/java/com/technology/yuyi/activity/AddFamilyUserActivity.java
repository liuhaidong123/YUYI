package com.technology.yuyi.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import java.io.FileOutputStream;
import java.io.IOException;

public class AddFamilyUserActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mSure_tv;
    private RoundImageView add_head_tv;
    private File file;
    private TextView usereditor_textv_cancle,usereditor_textv_picture,usereditor_textv_camera;
    private PopupWindow pop;
    private TextView title;

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
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        //返回
        if (id == mBack.getId()) {
            finish();
        }else if (id==mSure_tv.getId()){//确定
            finish();
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

    /**
     * 将图片读到本地
     * @param url
     * @param bitmap
     */
    private void write2Local(String url, Bitmap bitmap) {
        String name="";
        FileOutputStream fos = null;
        try {
           // name = MD5Encoder.encode(url);
            File file = new File(getCacheDir(), name);
            fos = new FileOutputStream(file);

            // 将图像写到流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                    fos = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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


    //拍照
    private void TakePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file=new File(getExternalFilesDir("DCIM").getAbsolutePath(),System.currentTimeMillis()+".jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, ResCode.Request_takePhoto);
    }


    //浏览图片库
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
                        Bitmap btm=data.getExtras().getParcelable("data");
                        if (btm!=null){
                            add_head_tv.setImageBitmap(btm);

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
