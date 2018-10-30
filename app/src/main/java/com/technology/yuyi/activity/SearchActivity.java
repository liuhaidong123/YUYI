package com.technology.yuyi.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.technology.yuyi.DBSqlite.ResultHospitalDB;
import com.technology.yuyi.HttpTools.HttpTools;
import com.technology.yuyi.R;
import com.technology.yuyi.adapter.HospitalSearchHistoryAdapter;
import com.technology.yuyi.adapter.HospitalSearchResultAdapter;
import com.technology.yuyi.bean.SearchHospital.Result;
import com.technology.yuyi.bean.SearchHospital.Root;
import com.technology.yuyi.lhd.utils.ToastUtils;
import com.technology.yuyi.lzh_utils.MyDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView mHistoryListView, mResultListView;
    private View clearFooter;
    private HospitalSearchResultAdapter mResultAdapter;//医院搜索结果
    private HospitalSearchHistoryAdapter mHistoryAdapter;//医院历史
    private List<Result> mResultList = new ArrayList<>();
    private List<String> mHistoryList = new ArrayList<>();
    private TextView mCancel;
    private EditText mEdit;
    //医院数据库
    private ResultHospitalDB mResultHospitalDB;
    private SQLiteDatabase mSqliteHospitalDB;
    private TextView mPrompt_Tv;
    private HttpTools mHttptools;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyDialog.stopDia();
            if (msg.what == 33) {//医院
                Object o = msg.obj;
                if (o != null && o instanceof Root) {
                    Root root = (Root) o;
                    if (root.getCode().equals("0")) {
                        mResultList = root.getResult();
                        if (mResultList.size() == 0) {
                            mPrompt_Tv.setText("抱歉，没有搜索到此医院");
                            mHistoryListView.setVisibility(View.GONE);

                        } else {
                            mPrompt_Tv.setText("");
                            mResultAdapter.setmList(mResultList);
                            // mResultListView.setAdapter(mResultAdapter);
                            mResultAdapter.notifyDataSetChanged();
                            mResultListView.setVisibility(View.VISIBLE);
                            mHistoryListView.setVisibility(View.GONE);
                        }

                    }
                }
            } else if (msg.what == 222) {//json解析错误
                MyDialog.stopDia();
            } else if (msg.what == 223) {//请求数据错误
                MyDialog.stopDia();
                ToastUtils.myToast(SearchActivity.this, "请求数据失败");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
    }

    public void initView() {
        mHttptools = HttpTools.getHttpToolsInstance();
        //医院数据库
        mResultHospitalDB = new ResultHospitalDB(this, "hospital_db", null, 1);
        mSqliteHospitalDB = mResultHospitalDB.getWritableDatabase();
        //查看hospital表中所有数据
        Cursor cursor = mSqliteHospitalDB.rawQuery("select * from hospital order by _id desc", null);
        while (cursor.moveToNext()) {
            String hospitalname = cursor.getString(cursor.getColumnIndex("hospitalname"));
            Log.e("医院名：", hospitalname);
            mHistoryList.add(hospitalname);
        }
        cursor.close();

        mHistoryListView = (ListView) findViewById(R.id.history_listview);
        mResultListView = (ListView) findViewById(R.id.result_listview);
        mHistoryAdapter = new HospitalSearchHistoryAdapter(this, mHistoryList);
        mResultAdapter = new HospitalSearchResultAdapter(this, mResultList);
        clearFooter = LayoutInflater.from(this).inflate(R.layout.clear_history_footer, null);//清除历史
        mHistoryListView.setAdapter(mHistoryAdapter);
        mResultListView.setAdapter(mResultAdapter);
        //搜索结果提示
        mPrompt_Tv = (TextView) findViewById(R.id.prompt_result);
        if (mHistoryList.size() != 0) {
            mHistoryListView.setVisibility(View.VISIBLE);
            mHistoryListView.addFooterView(clearFooter);
        } else {
            mPrompt_Tv.setText("暂无搜索历史");
        }

        //取消按钮
        mCancel = (TextView) findViewById(R.id.cancel_tv);
        mCancel.setOnClickListener(this);
        //输入框
        mEdit = (EditText) findViewById(R.id.edit_box);
        //点击搜索按钮，回调这个方法(注意：如果不处理的话，此方法会被调用2次，搜索按下和松开都会被调用)
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            // || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    //搜索医院接口
                    if (!getEditTxt().equals("")) {
                        if (event.getAction() == KeyEvent.ACTION_UP) {
                            MyDialog.showDialog(SearchActivity.this);
                            Map<String,String> map=new HashMap<String, String>();
                            map.put("vague",getEditTxt());
                            mHttptools.getSearchHospitalData(mHandler, map);
                            ContentValues valuesHospital = new ContentValues();
                            valuesHospital.put("hospitalname", getEditTxt());
                            mSqliteHospitalDB.insert("hospital", null, valuesHospital);
                            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    } else {
                        Toast.makeText(SearchActivity.this, "请输入查询的医院名称", Toast.LENGTH_SHORT).show();
                    }

                    return true;
                }
                return false;
            }
        });

        //点击历史搜索医院
        mHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mHistoryList.size()) {//清空历史
                    mSqliteHospitalDB.execSQL("delete from hospital");
                    mHistoryList.clear();
                    mHistoryAdapter.notifyDataSetChanged();
                    mHistoryListView.removeFooterView(clearFooter);
                    mPrompt_Tv.setText("暂无搜索历史");
                } else {//搜索医院
                    Map<String,String> map=new HashMap<String, String>();
                    map.put("vague",mHistoryList.get(position));
                    mHttptools.getSearchHospitalData(mHandler,map );
                    MyDialog.showDialog(SearchActivity.this);
                }
            }
        });
        //点击搜索到的医院，跳转详情
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchActivity.this, HospitalDetailsActivity.class);
                intent.putExtra("id", mResultList.get(position).getId());
                startActivity(intent);
                finish();
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

    //判断搜索框里是否有内容
    public String getEditTxt() {
        if (mEdit.getText().toString().trim() != null && !mEdit.getText().toString().trim().equals("")) {
            return mEdit.getText().toString().trim();
        }
        return "";
    }
}
