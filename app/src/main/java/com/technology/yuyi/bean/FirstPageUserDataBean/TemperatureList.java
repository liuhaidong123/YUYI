package com.technology.yuyi.bean.FirstPageUserDataBean;

/**
 * Created by liuhaidong on 2017/3/23.
 */

public class TemperatureList {
    private String createTimeString;

    private String updateTimeString;

    private int humeuserId;

    private int id;

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
    public void setTemperaturet(float temperaturet){
        this.temperaturet = temperaturet;
    }
    public float getTemperaturet(){
        return this.temperaturet;
    }
}
