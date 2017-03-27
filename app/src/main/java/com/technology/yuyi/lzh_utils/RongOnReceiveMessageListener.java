package com.technology.yuyi.lzh_utils;

import io.rong.imkit.model.Event;
import io.rong.imlib.model.Message;

/**
 * Created by wanyu on 2017/3/24.
 */

public class RongOnReceiveMessageListener extends Event.OnReceiveMessageEvent {
    public RongOnReceiveMessageListener(Message message, int left) {
        super(message, left);
    }
}
