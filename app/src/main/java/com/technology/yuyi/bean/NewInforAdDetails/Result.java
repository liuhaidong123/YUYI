package com.technology.yuyi.bean.NewInforAdDetails;

/**
 * Created by liuhaidong on 2017/11/21.
 */

public class Result {
    private String smallTitle;

    private String createTimeString;

    private String updateTimeString;

    private String title;

    private int auditState;

    private String content;

    private String picture;

    private int hospitalId;

    private int id;

    private int publishchannel;

    public void setSmallTitle(String smallTitle){
        this.smallTitle = smallTitle;
    }
    public String getSmallTitle(){
        return this.smallTitle;
    }
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
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setAuditState(int auditState){
        this.auditState = auditState;
    }
    public int getAuditState(){
        return this.auditState;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setPicture(String picture){
        this.picture = picture;
    }
    public String getPicture(){
        return this.picture;
    }
    public void setHospitalId(int hospitalId){
        this.hospitalId = hospitalId;
    }
    public int getHospitalId(){
        return this.hospitalId;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setPublishchannel(int publishchannel){
        this.publishchannel = publishchannel;
    }
    public int getPublishchannel(){
        return this.publishchannel;
    }
}
