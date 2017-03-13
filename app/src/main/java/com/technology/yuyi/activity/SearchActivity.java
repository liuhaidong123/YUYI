package com.technology.yuyi.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.R;
import com.technology.yuyi.adapter.MyOrderListViewAdapter;
import com.technology.yuyi.adapter.SearchHistoryListViewAdapter;
import com.technology.yuyi.hide.input.keyboard.HideKeyboardUtils;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener ,AdapterView.OnItemClickListener{
    private ListView mListView;
    private SearchHistoryListViewAdapter mAdapter;
    private TextView mCancel;
    private EditText mEdit;
    private View mFooterView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    public void initView() {


        //搜索的历史数据
        mFooterView = LayoutInflater.from(this).inflate(R.layout.search_footer_item, null);
        mListView = (ListView) findViewById(R.id.search_history_listview);
        mListView.addFooterView(mFooterView);
        mAdapter = new SearchHistoryListViewAdapter(this);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);
        //取消按钮
        mCancel = (TextView) findViewById(R.id.cancel_tv);
        mCancel.setOnClickListener(this);
        //输入框
        mEdit = (EditText) findViewById(R.id.edit_box);
        mEdit.setHint(getIntent().getStringExtra("hint"));
        //点击搜索按钮，回调这个方法
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //do something;
                    Toast.makeText(SearchActivity.this, "点击了搜索", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mCancel.getId()) {
            //隐藏软键盘
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            finish();

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(SearchActivity.this, "点击了listview:"+position, Toast.LENGTH_SHORT).show();
    }

}
