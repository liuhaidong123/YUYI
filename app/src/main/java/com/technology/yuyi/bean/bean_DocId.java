package com.technology.yuyi.bean;

/**
 * Created by wanyu on 2017/3/30.
 */
//请求聊天医生的信息
public class bean_DocId {

    /**
     * id : 9
     * TrueName : 刘诗诗
     * Avatar : /static/image/2017/3/29/1490755041901.jpg
     */

    private int id;
    private String TrueName;
    private String Avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String TrueName) {
        this.TrueName = TrueName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }
}
