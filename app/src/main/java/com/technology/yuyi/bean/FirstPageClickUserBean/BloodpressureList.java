package com.technology.yuyi.bean.FirstPageClickUserBean;

/**
 * Created by liuhaidong on 2017/3/24.
 */

public class BloodpressureList {
    private String createTimeString;

    private int diastolic;

    private int systolic;

    private String updateTimeString;

    private long humeuserId;

    private long id;

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
    public void setHumeuserId(long humeuserId){
        this.humeuserId = humeuserId;
    }
    public long getHumeuserId(){
        return this.humeuserId;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
}
