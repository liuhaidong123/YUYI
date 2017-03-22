package com.technology.yuyi.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;


import com.technology.yuyi.R;

import com.technology.yuyi.lzh_utils.user;



import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by wanyu on 2017/3/20.
 */

public class Roung_imActivity extends FragmentActivity {
//    private View frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_rong);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        frag=findViewById(R.id.conversation);
//        if (RongIM.getInstance()!=null){
//            Log.e("targitID========",user.targetId);
//            ConversationFragment fragment = new ConversationFragment();
//            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                    .appendPath("conversation").appendPath(Conversation.ConversationType.CHATROOM.getName().toLowerCase())
//                    .appendQueryParameter("targetId", user.targetId).build();
//            fragment.setUri(uri);
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.add(frag.getId(), fragment);
//            transaction.commit();
//        }
    }
}
