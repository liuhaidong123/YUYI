package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by liuhaidong on 2017/3/16.
 */

public class UserRegisterRoot {
    private List<UserRegisterData> result;

    private String code;

    public void setResult(List<UserRegisterData> result){
        this.result = result;
    }
    public List<UserRegisterData> getResult(){
        return this.result;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
}
