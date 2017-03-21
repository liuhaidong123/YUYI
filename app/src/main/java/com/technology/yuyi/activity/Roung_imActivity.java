package com.technology.yuyi.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;


import com.technology.yuyi.R;
import com.technology.yuyi.lzh_utils.RongUri;
import com.technology.yuyi.lzh_utils.user;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/**
 * Created by wanyu on 2017/3/20.
 */

public class Roung_imActivity extends FragmentActivity {
    private View frag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_im_rong);
//        frag=findViewById(R.id.conversation);
//        ConversationFragment fragment = new ConversationFragment();
//        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
//                .appendPath("conversation").appendPath(Conversation.ConversationType.CHATROOM.getName().toLowerCase())
//                .appendQueryParameter("targetId", user.targetId).build();
//        fragment.setUri(uri);
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.add(frag.getId(), fragment);
//        transaction.commit();
    }
}
