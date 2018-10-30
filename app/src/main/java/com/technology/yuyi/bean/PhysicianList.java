package com.technology.yuyi.bean;

/**
 * Created by liuhaidong on 2018/8/23.
 */

public class PhysicianList {
    private boolean PermissionInfo;

    private String id;

    private String TrueName;

    private String Avatar;

    private String token;

    public boolean isPermissionInfo() {
        return PermissionInfo;
    }

    public void setPermissionInfo(boolean permissionInfo) {
        PermissionInfo = permissionInfo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrueName() {
        return TrueName;
    }

    public void setTrueName(String trueName) {
        TrueName = trueName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String avatar) {
        Avatar = avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
