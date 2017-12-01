package com.technology.yuyi.Photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wanyu on 2017/11/23.
 */

public class BitmapUtils {
    private static BitmapUtils utils;
    float MAX=700;//宽高度最大值
    float MAXSIZE=300;//文件大小的最大值
    private BitmapUtils(){}
    public static synchronized BitmapUtils getInstance(){
        if (utils==null){
            utils=new BitmapUtils();
        }
        return utils;
    }
    //根据路径返回压缩的bitmap并保存在指定文件下
    public Bitmap getReSizeBitmap(String filePath, File resultFile){
        if (resultFile==null){
            Log.e("BitmapUtils","根据路径对拍照或选取的图片进行二次采样，传入的要保存的resultFile的路径不能为空！");
            return null;
        }
        if (filePath!=null){
            Log.e("getBitmap","文件路径1："+filePath);
            filePath=filePath.replace(" ", "");
            Log.e("getBitmap","文件路径2："+filePath);
            //第一次采样
            BitmapFactory.Options options = new BitmapFactory.Options();
            //该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
            options.inJustDecodeBounds = true;
            //第一次加载图片，这时只会加载图片的边框进来，并不会加载图片中的像素点
            BitmapFactory.decodeFile(filePath, options);
            //获得原图的宽和高
            int outWidth = options.outWidth;
            int outHeight = options.outHeight;
            //定义缩放比例
            int sampleSize = 1;
            while (outHeight / sampleSize > MAX || outWidth / sampleSize > MAX) {
                //如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
                //sampleSize应该为2的n次幂，如果给sampleSize设置的数字不是2的n次幂，那么系统会就近取值
                sampleSize *= 2;
            }
            /********************************************************************************************/
            //至此，第一次采样已经结束，我们已经成功的计算出了sampleSize的大小
            /********************************************************************************************/
            //二次采样开始
            //二次采样时我需要将图片加载出来显示，不能只加载图片的框架，因此inJustDecodeBounds属性要设置为false
            options.inJustDecodeBounds = false;
            //设置缩放比例
            options.inSampleSize = sampleSize;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            //加载图片并返回
            Bitmap resultBitmap= BitmapFactory.decodeFile(filePath, options);
            double currentSize = getBitmapSize(resultBitmap)/1024f;
            Log.e("采样得到的bitmap大小:",currentSize+"kb");
            // 判断bitmap占用空间是否大于允许最大空间,如果大于则压缩,小于则不压缩
            while (currentSize > MAXSIZE*1.2) {
                // 计算bitmap的大小是maxSize的多少倍
                double multiple = currentSize / MAXSIZE*1.2;
                Log.e("multiple",multiple+"");
                // 开始压缩：将宽带和高度压缩掉对应的平方根倍
                // 1.保持新的宽度和高度，与bitmap原来的宽高比率一致
                // 2.压缩后达到了最大大小对应的新bitmap，显示效果最好
                resultBitmap = getZoomImage(resultBitmap, resultBitmap.getWidth() / Math.sqrt(multiple), resultBitmap.getHeight() / Math.sqrt(multiple));
                currentSize = getBitmapSize(resultBitmap)/1024f;
            }
            Log.i("二次采样的bitmap大小",""+getBitmapSize(resultBitmap)/1024f+"kb");
            if (saveBitmap(resultBitmap,resultFile)){
                return BitmapFactory.decodeFile(resultFile.getAbsolutePath());
            }
            else {
                return null;
                }
        }
        else {
            Log.e("BitmapUtils","根据路径对拍照或选取的图片进行二次采样，原图片的路径不能为空！");
            }
        return null;
    }
    //保存bitmap到文件中
    private boolean saveBitmap(Bitmap bm, File result){
        boolean flag=true;
        if (result.exists()) {
            result.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(result);
            bm.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            Log.i("BitmapUtils", "保存经过二次采样的bitmap成功");
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            Log.i("BitmapUtils", "保存经过二次采样的bitmap失败：FileNotFoundException");
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.i("BitmapUtils", "保存经过二次采样的bitmap失败：IOException");
            e.printStackTrace();
            return false;
        }
        return flag;
    }
    //获取bitmap大小
    private  int getBitmapSize(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {    //API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {//API 12
            return bitmap.getByteCount();
        }
        // 在低版本中用一行的字节x高度
        return bitmap.getRowBytes() * bitmap.getHeight();                //earlier version
    }


    public static Bitmap getZoomImage(Bitmap orgBitmap, double newWidth, double newHeight) {
        if (null == orgBitmap) {
            return null;
        }
        if (orgBitmap.isRecycled()) {
            return null;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            return null;
        }

        // 获取图片的宽和高
        float width = orgBitmap.getWidth();
        float height = orgBitmap.getHeight();
        // 创建操作图片的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(orgBitmap, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap, boolean needRecycle) {
        if (null == bitmap) {
            return null;
        }
        if (bitmap.isRecycled()) {
            return null;
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bitmap.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
           e.printStackTrace();
            return  null;
        }
        return result;
    }
}
