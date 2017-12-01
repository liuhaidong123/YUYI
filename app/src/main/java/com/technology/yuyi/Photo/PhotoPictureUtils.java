package com.technology.yuyi.Photo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import java.io.File;


/**
 * Created by wanyu on 2017/11/22.
 */

public class PhotoPictureUtils {
    static PhotoPictureUtils utils;
    File PHOTO;//拍照的图片（调用相机后统一保存为该图片）
    private PhotoPictureUtils(){}
    public static synchronized PhotoPictureUtils getInstance(){
        if (utils==null){
            utils=new PhotoPictureUtils();
        }
        return utils;
    }
    //选取图片
    public void searchPicture(Activity ac){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if (PermissionCheck.getInstance().isPermissionGet(new String[]{PermissionNames.READ_SD,PermissionNames.WRITE_SD},ac)) {
                if (PHOTO!=null&&PHOTO.exists()){
                    PHOTO.delete();
                }
                PHOTO=new File(SDutils.getInstance().getFilePath(ac).getAbsolutePath(),getFileName());
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,getFileUri(ac,PHOTO,intent));
                Log.i("searchPicture","uri:"+getFileUri(ac,PHOTO,intent));
                ac.startActivityForResult(intent, PhotoRSCode.requestCode_Search);
            }
            else {
                ac.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},PhotoRSCode.requestCode_SearchPermission);
                }
        }
        else {
            if (PHOTO!=null&&PHOTO.exists()){
                PHOTO.delete();
            }
            PHOTO=new File(SDutils.getInstance().getFilePath(ac).getAbsolutePath(),getFileName());
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(PHOTO));
            ac.startActivityForResult(intent, PhotoRSCode.requestCode_Search);
        }
    }

    //选取图片(Fragment中使用)
    public void searchPictureFragment(Fragment ac){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if (PermissionCheck.getInstance().isPermissionGet(new String[]{PermissionNames.READ_SD,PermissionNames.WRITE_SD},ac.getActivity())) {
                if (PHOTO!=null&&PHOTO.exists()){
                    PHOTO.delete();
                }
                PHOTO=new File(SDutils.getInstance().getFilePath(ac.getActivity()).getAbsolutePath(),getFileName());
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,getFileUri(ac.getActivity(),PHOTO,intent));
                Log.i("searchPicture","uri:"+getFileUri(ac.getActivity(),PHOTO,intent));
                ac.startActivityForResult(intent, PhotoRSCode.requestCode_Search);
            }
            else {
                ac.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},PhotoRSCode.requestCode_SearchPermission);
            }
        }
        else {
            if (PHOTO!=null&&PHOTO.exists()){
                PHOTO.delete();
            }
            PHOTO=new File(SDutils.getInstance().getFilePath(ac.getActivity()).getAbsolutePath(),getFileName());
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(PHOTO));
            ac.startActivityForResult(intent, PhotoRSCode.requestCode_Search);
        }
    }



    //拍照
    public void takePhoto(Activity ac){
        if (Build.VERSION.SDK_INT>=23){
            if (PermissionCheck.getInstance().isPermissionGet(new String[]{PermissionNames.CAMERA,PermissionNames.READ_SD,PermissionNames.WRITE_SD},ac)){
                if (PHOTO!=null&&PHOTO.exists()){
                    PHOTO.delete();
                }
                PHOTO=new File(SDutils.getInstance().getFilePath(ac).getAbsolutePath(),getFileName());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,getFileUri(ac,PHOTO,intent));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                ac.startActivityForResult(intent,PhotoRSCode.requestCode_Camera);
            }
            else {
                ac.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},PhotoRSCode.requestCode_Camera);
            }
        }
        else {
            if (PHOTO!=null&&PHOTO.exists()){
                PHOTO.delete();
            }
            PHOTO=new File(SDutils.getInstance().getFilePath(ac).getAbsolutePath(),getFileName());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(PHOTO));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            ac.startActivityForResult(intent,PhotoRSCode.requestCode_Camera);
        }
    }
//    (Fragment中使用)
    public void takePhotoFragment(Fragment ac){
        if (Build.VERSION.SDK_INT>=23){
            if (PermissionCheck.getInstance().isPermissionGet(new String[]{PermissionNames.CAMERA,PermissionNames.READ_SD,PermissionNames.WRITE_SD},ac.getActivity())){
                if (PHOTO!=null&&PHOTO.exists()){
                    PHOTO.delete();
                }
                PHOTO=new File(SDutils.getInstance().getFilePath(ac.getActivity()).getAbsolutePath(),getFileName());
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,getFileUri(ac.getActivity(),PHOTO,intent));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                ac.startActivityForResult(intent,PhotoRSCode.requestCode_Camera);
            }
            else {
                ac.requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},PhotoRSCode.requestCode_Camera);
            }
        }
        else {
            if (PHOTO!=null&&PHOTO.exists()){
                PHOTO.delete();
            }
            PHOTO=new File(SDutils.getInstance().getFilePath(ac.getActivity()).getAbsolutePath(),getFileName());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(PHOTO));
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
            ac.startActivityForResult(intent,PhotoRSCode.requestCode_Camera);
        }
    }
    //图片压缩处理参数并显示
    public void savaPictureSearch(Uri uri, OnSavePictureListener listener, Activity ac){
        Uri u=getUri(ac,PHOTO);
        Log.i("u="+u.toString(),"uri="+uri.toString());
        File resutFile =new File(SDutils.getInstance().getFilePath(ac),getFileName());
        Bitmap resultBitmap=null;
        Log.e("savaPictureSearch","uri:"+u.toString());
        if (Build.VERSION.SDK_INT>=19){
            resultBitmap= BitmapUtils.getInstance().getReSizeBitmap(UriUtils.getPathFromUri(uri,ac),resutFile);
        }
        else {
            resultBitmap= BitmapUtils.getInstance().getReSizeBitmap(UriUtils.getPathAPI16(uri,ac),resutFile);
            }
        if (resultBitmap!=null){
            resultBitmap.recycle();
            listener.onSavePicture(true,resutFile);
                }
        else {
            resutFile=null;
            listener.onSavePicture(false,resutFile);
            }
    }

    public void savaPictureCamera(OnSavePictureListener listener,Activity ac){
        Bitmap resultBitmap=null;
        File resutFile=new File(SDutils.getInstance().getFilePath(ac),getFileName());
        resultBitmap=BitmapUtils.getInstance().getReSizeBitmap(PHOTO.getAbsolutePath(),resutFile);
        if (resultBitmap!=null){
            resultBitmap.recycle();
            listener.onSavePicture(true,resutFile);
        }
        else {
            resutFile=null;
            listener.onSavePicture(false,resutFile);
        }
    }

    //适配7.0的uri问题
    private Uri getFileUri(Context context, File f, Intent intent){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){//大于7.0版本
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".FileProvider",f);
        }
        else{
            return Uri.fromFile(f);
        }
    }
    public Uri getUri(Context context, File f){
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){//大于7.0版本
            return FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".FileProvider",f);
        }
        else{
            return Uri.fromFile(f);
        }
    }

    //给图片命名
    private String getFileName(){
        return System.currentTimeMillis()+".jpg";
    }

    public interface OnSavePictureListener{
        //参数isSuccess是否成功，result保存的文件
        void onSavePicture(boolean isSuccess, File result);
    }
}
