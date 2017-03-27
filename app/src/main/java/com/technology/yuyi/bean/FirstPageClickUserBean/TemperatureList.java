package com.technology.yuyi.bean.FirstPageClickUserBean;

/**
 * Created by liuhaidong on 2017/3/24.
 */

public class TemperatureList {
    private String createTimeString;

    private String updateTimeString;

    private long humeuserId;

    private long id;

    private float temperaturet;

    public void setCreateTimeString(String createTimeString){
        this.createTimeString = createTimeString;
    }
    public String getCreateTimeString(){
        return this.createTimeString;
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
    public void setTemperaturet(float temperaturet){
        this.temperaturet = temperaturet;
    }
    public float getTemperaturet(){
        return this.temperaturet;
    }
}
