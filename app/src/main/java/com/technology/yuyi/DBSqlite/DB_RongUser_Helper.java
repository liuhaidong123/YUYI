package com.technology.yuyi.DBSqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by wanyu on 2017/3/28.
 */

public class DB_RongUser_Helper extends SQLiteOpenHelper {
    public DB_RongUser_Helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "RongUser", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        token,Rid,name,url
        String sql="create table user( Rid varchar(20) primary key,token varchar(300),name varchar(50),url varchar(200))";
        try {
            db.execSQL(sql);
            Log.i("数据库创建成功--openhelper--","-------数据库创建成功-----");
        }
        catch (Exception e){
            Log.e("数据库创建失败--openhelper--",e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
