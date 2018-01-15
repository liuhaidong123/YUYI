package com.technology.yuyi.Photo;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by wanyu on 2017/11/22.
 */

public class SDutils {
    String TAG=SDutils.class.getSimpleName();
    String directoryName_out="outFile";
    String directoryName_in="inFile";
    private static SDutils utils;
    private SDutils() {
    }
    public static synchronized SDutils getInstance() {
        if (utils == null) {
            utils = new SDutils();
        }
        return utils;
    }
    public File getFilePath(Context con) {
        String cachePath = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                &&!Environment.isExternalStorageRemovable()) {
            cachePath = con.getExternalFilesDir(directoryName_out).getAbsolutePath();
        } else {
            cachePath = con.getFilesDir().getAbsolutePath()+ File.separator+directoryName_in;
            }
        File parent=new File(cachePath);
        if (!parent.exists()){
            parent.mkdirs();
        }
        return parent;
    }
    //创建文件
    public File initFile(Context con){
        return new File(getFilePath(con),getFileName());
    }

    private String getFileName(){
        return System.currentTimeMillis()+".jpg";
    }
}
