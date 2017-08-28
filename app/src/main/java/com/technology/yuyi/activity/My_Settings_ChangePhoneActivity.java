package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.Presenter.My_Settings_ChangePhonePresenter;
import com.technology.yuyi.R;
import com.technology.yuyi.bean.BeanChangePhone;
import com.technology.yuyi.bean.bean_SMScode;
import com.technology.yuyi.lzh_utils.user;

//修改绑定手机号
public class My_Settings_ChangePhoneActivity extends AppCompatActivity implements View.OnClickListener,My_Settings_ChangePhonePresenter.IChangePhonePresenter {
    My_Settings_ChangePhonePresenter presenter;
    TextView activity_include_title_btn;//标题
    TextView titleInclude_btn;//顶部确认按钮
    TextView text_changePhone;//显示的当前手机号
    EditText eidt_phoneNumber,edit_smsCode;//手机号，验证码输入框
    TextView text_smsCode_btn;//发送验证码按钮
    String phone;//当前的手机号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__settings__change_phone);
        initView();
        presenter=new My_Settings_ChangePhonePresenter(this,this);
    }

    private void initView() {
        activity_include_title_btn= (TextView) findViewById(R.id.activity_include_title_btn);
        activity_include_title_btn.setText("修改绑定手机号");
        text_changePhone= (TextView) findViewById(R.id.text_changePhone);
        text_changePhone.setText(user.userName);
        titleInclude_btn= (TextView) findViewById(R.id.titleInclude_btn);
        titleInclude_btn.setText("确认更改");
        titleInclude_btn.setOnClickListener(this);
        eidt_phoneNumber= (EditText) findViewById(R.id.eidt_phoneNumber);
        edit_smsCode= (EditText) findViewById(R.id.edit_smsCode);
        text_smsCode_btn= (TextView) findViewById(R.id.text_smsCode_btn);
        text_smsCode_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titleInclude_btn://确认按钮
                phone=eidt_phoneNumber.getText().toString();
                presenter.changePhone(phone,edit_smsCode.getText().toString());
                break;
            case R.id.text_smsCode_btn:
                presenter.getSmsCode(eidt_phoneNumber.getText().toString());
                break;
        }
    }

    //读秒器tm=-2时从新计时，可以被点击
    @Override
    public void onClickAble(int tm) {
        if (tm>0){
            text_smsCode_btn.setText("剩余 "+tm+"s");
        }
        else if (tm==0){
            text_smsCode_btn.setText("发送验证码");
        }
        else if (tm==-2){
            text_smsCode_btn.setClickable(true);
            text_smsCode_btn.setBackground(getResources().getDrawable(R.drawable.textrightbtn));
            text_smsCode_btn.setText("发送验证码");
        }
    }

    @Override
    public void onInputPhoneError() {
        Toast.makeText(this,"手机号码不正确",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onInputSMSCodeError() {
        Toast.makeText(this,"验证码不正确",Toast.LENGTH_SHORT).show();
    }
    @Override
    public void ongetSMSError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onChangePhoneError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void ongetSMSSuccess() {
        text_smsCode_btn.setClickable(false);
        text_smsCode_btn.setBackground(getResources().getDrawable(R.drawable.textright));
    }
    @Override
    public void onChangePhoneSuccess() {
        text_changePhone.setText(phone+"");
        Toast.makeText(this,"更改成功",Toast.LENGTH_SHORT).show();
    }
}
