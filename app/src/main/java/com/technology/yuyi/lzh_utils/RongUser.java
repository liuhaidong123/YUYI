package com.technology.yuyi.lzh_utils;

/**
 * Created by wanyu on 2017/3/24.
 */

public class RongUser {
    private String name;
    private String id;
    public RongUser(String name,String url,String id){
        this.id=id;
        this.url=url;
        this.name=name;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String url;
}
