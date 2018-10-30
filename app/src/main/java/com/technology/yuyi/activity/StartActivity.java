package com.technology.yuyi.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.VersionRoot;
import com.technology.yuyi.lzh_utils.user;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;

public class StartActivity extends AppCompatActivity {
    private Button mBtn;
    private int mTime = 3;//跳转时间
    private String usid;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                if (mTime >= 0) {
                    mTime--;
                    handler.sendEmptyMessageDelayed(1, 1000);
                } else {
                    if (user.isLogin(StartActivity.this)) {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                        handler.removeMessages(1);
                        finish();
                    } else {
                        Intent intent = new Intent(StartActivity.this, My_userLogin_Activity.class);
                        startActivity(intent);
                        finish();
                        handler.removeMessages(1);
                    }

                }
            } else if (msg.what == 909) {//检测版本号

                Object o = msg.obj;
                if (o != null && o instanceof VersionRoot) {
                    VersionRoot versionRoot = (VersionRoot) o;
                    if (versionRoot != null && "0".equals(versionRoot.getCode())) {
                        int versionC = Integer.valueOf(versionRoot.getResult().getVersion());
                        if (versionC > getVersionCode()) {//服务器版本号大于当前版本号，需要更新软件
                            Log.e("服务器版本号=", versionC + "");
                            alertDialog.show();

                        } else {//不需要更新软件
                            handler.sendEmptyMessage(1);
                        }
                    }
                }

            }
        }
    };
    private HttpTools httpTools;
    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        httpTools = HttpTools.getHttpToolsInstance();

        if (isNetworkConnected()){
            httpTools.CheckVersion(handler);//检测版本号
        }else {
            handler.sendEmptyMessage(1);

        }
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("检测到最新版本,请更新");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://sj.qq.com/myapp/search.htm?kw=%E5%AE%87%E5%8C%BB"));
                startActivity(intent);
                alertDialog.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
                finish();
            }
        });
        alertDialog = builder.create();
    }


    private int getVersionCode() {
        // 获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int versionCode = packInfo.versionCode;
        Log.e("本地软件版本号=", versionCode + "");
        return versionCode;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeMessages(1);
    }

    /**
     * 判断有没有网
     *
     * @return
     */
    public boolean isNetworkConnected() {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }

        return false;
    }
}
