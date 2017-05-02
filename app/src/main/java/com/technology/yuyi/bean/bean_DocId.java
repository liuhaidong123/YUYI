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

    private String id;
    private String TrueName;
    private String Avatar;
    /**
     * code : 0
     * PermissionInfo : true
     * token : wR9ZDDuSHHXdXWd9uIqf3IuZqhNeP62jUkZowKgPQxuWVR/N71VJ9mdM5K0HZDaZb16sV0QxBE+E5LeCeESZ+A6RxRrPS2w8
     */

    private int code;
    private boolean PermissionInfo;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isPermissionInfo() {
        return PermissionInfo;
    }

    public void setPermissionInfo(boolean PermissionInfo) {
        this.PermissionInfo = PermissionInfo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
