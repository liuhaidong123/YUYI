package com.technology.yuyi.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.technology.yuyi.R;
import com.technology.yuyi.lzh_utils.user;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class StartActivity extends AppCompatActivity implements View.OnClickListener{
    private Button mBtn;
    private int mTime=3;//跳转时间
        private Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what==1){
                    if(mTime>=0){
                        mBtn.setText(mTime--+"S后跳转");
                        handler.sendEmptyMessageDelayed(1,1000);
                    }else {
                        if (user.isLogin(StartActivity.this)){
                            RongIM.connect(user.RongToken, new RongIMClient.ConnectCallback() {

                                /**
                                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                                 */
                                @Override
                                public void onTokenIncorrect() {

                                }

                                /**
                                 * 连接融云成功
                                 * @param userid 当前 token 对应的用户 id
                                 */
                                @Override
                                public void onSuccess(String userid) {
                                    user.userId=userid;
                                    Log.i("融云返回的id---",userid+"--StartActivity---");
                                    Toast.makeText(StartActivity.this,"融云token注册成功--"+userid,Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(StartActivity.this,MainActivity.class);
                                    startActivity(intent);
                                    handler.removeMessages(1);
                                    finish();
                                }

                                /**
                                 * 连接融云失败
                                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                                 */
                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                        Log.e("token错误--","startActivity----");
                                        Toast.makeText(StartActivity.this,"融云token失败--",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(StartActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        handler.removeMessages(1);
                                        finish();
                                }
                            });



                        }else {
                            Intent intent=new Intent(StartActivity.this,My_userLogin_Activity.class);
                            startActivity(intent);
                            finish();
                            handler.removeMessages(1);
                        }

                    }
                }
            }
        };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mBtn= (Button) findViewById(R.id.intent_id_btnt);
        mBtn.setOnClickListener(this);
        handler.sendEmptyMessageDelayed(1,200);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==mBtn.getId()){
            handler.removeMessages(1);
            if (user.isLogin(this)){
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }else {
                Intent intent=new Intent(StartActivity.this,My_userLogin_Activity.class);
                startActivity(intent);
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        handler.removeMessages(1);
    }
}
