package com.technology.yuyi.bean.FirstPageUserDataBean;

/**
 * Created by liuhaidong on 2017/3/23.
 */

public class BloodpressureList {
    private String createTimeString;

    private int diastolic;

    private int systolic;

    private String updateTimeString;

    private int humeuserId;

    private int id;

    public void setCreateTimeString(String createTimeString){
        this.createTimeString = createTimeString;
    }
    public String getCreateTimeString(){
        return this.createTimeString;
    }
    public void setDiastolic(int diastolic){
        this.diastolic = diastolic;
    }
    public int getDiastolic(){
        return this.diastolic;
    }
    public void setSystolic(int systolic){
        this.systolic = systolic;
    }
    public int getSystolic(){
        return this.systolic;
    }
    public void setUpdateTimeString(String updateTimeString){
        this.updateTimeString = updateTimeString;
    }
    public String getUpdateTimeString(){
        return this.updateTimeString;
    }
    public void setHumeuserId(int humeuserId){
        this.humeuserId = humeuserId;
    }
    public int getHumeuserId(){
        return this.humeuserId;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
}
