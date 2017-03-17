package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by liuhaidong on 2017/3/16.
 */

public class HospitalDepartmentRoot {
    private List<HospitalDepartmentMessage> result;

    private String code;

    public void setResult(List<HospitalDepartmentMessage> result){
        this.result = result;
    }
    public List<HospitalDepartmentMessage> getResult(){
        return this.result;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }
}
