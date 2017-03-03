package com.technology.yuyi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.technology.yuyi.GaoDe.adapter.ResultCityAdapter;
import com.technology.yuyi.R;
import com.technology.yuyi.hide.input.keyboard.HideKeyboardUtils;
import com.technology.yuyi.myview.MyLetterListView;

public class GaoDeLocateActivity extends AppCompatActivity {

    private ListView mAllCity_ListView;//定位页面所有城市列表
    private ListView mSearch_city_result_listview;//搜索城市时的城市列表
    private ResultCityAdapter mResultCityAdapter;
    private TextView mOverlay;//触摸右侧拼音时，弹出的字母提示
    private MyLetterListView mLetterListView;//A-Z listview

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gao_de_locate);

    }

    //覆写activity的点击事件的分发方法dispatchTouchEvent(触摸软键盘以外的地方，隐藏软键盘)
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (HideKeyboardUtils.isShouldHideInput(v, ev)) {
                if (HideKeyboardUtils.hideInputMethod(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
