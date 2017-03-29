package com.technology.yuyi.bean;

/**
 * Created by wanyu on 2017/3/28.
 */
//获取融云token接口
public class beanRongToken {
//code=1成功，0失败
    /**
     * code : 1
     * id : 17734862622
     * TrueName : null
     * Avatar : /static/image/avatar.jpeg
     * token : Dn8kXt1C1KvU1wB4K3ViJSm+/4mVRkjkKe63vV33TZdIRtvhLu3OGbfoxCZ3wz2opU+yRkFaBOup69/S6T5CakGKL8P8leKr
     */

    private String code;
    private long id;
    private Object TrueName;
    private String Avatar;
    private String token;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Object getTrueName() {
        return TrueName;
    }

    public void setTrueName(Object TrueName) {
        this.TrueName = TrueName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
