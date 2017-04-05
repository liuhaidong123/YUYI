package com.technology.yuyi.bean.FirstPageUserDataBean;

import java.util.List;

/**
 * Created by liuhaidong on 2017/3/23.
 */

public class Result {
    private String createTimeString;

    private int role;

    private String updateTimeString;

    private String nickName;

    private int groupId;

    private long telephone;

    private String avatar;

    private int oid;

    private Integer gender;
    private List<BloodpressureList> bloodpressureList;

    private String trueName;

    private long id;

    private List<TemperatureList> temperatureList;

    private int age;

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setCreateTimeString(String createTimeString){
        this.createTimeString = createTimeString;
    }
    public String getCreateTimeString(){
        return this.createTimeString;
    }
    public void setRole(int role){
        this.role = role;
    }
    public int getRole(){
        return this.role;
    }
    public void setUpdateTimeString(String updateTimeString){
        this.updateTimeString = updateTimeString;
    }
    public String getUpdateTimeString(){
        return this.updateTimeString;
    }
    public void setNickName(String nickName){
        this.nickName = nickName;
    }
    public String getNickName(){
        return this.nickName;
    }
    public void setGroupId(int groupId){
        this.groupId = groupId;
    }
    public int getGroupId(){
        return this.groupId;
    }
    public void setTelephone(long telephone){
        this.telephone = telephone;
    }
    public long getTelephone(){
        return this.telephone;
    }
    public void setAvatar(String avatar){
        this.avatar = avatar;
    }
    public String getAvatar(){
        return this.avatar;
    }
    public void setOid(int oid){
        this.oid = oid;
    }
    public int getOid(){
        return this.oid;
    }
    public void setBloodpressureList(List<BloodpressureList> bloodpressureList){
        this.bloodpressureList = bloodpressureList;
    }
    public List<BloodpressureList> getBloodpressureList(){
        return this.bloodpressureList;
    }
    public void setTrueName(String trueName){
        this.trueName = trueName;
    }
    public String getTrueName(){
        return this.trueName;
    }
    public void setId(long id){
        this.id = id;
    }
    public long getId(){
        return this.id;
    }
    public void setTemperatureList(List<TemperatureList> temperatureList){
        this.temperatureList = temperatureList;
    }
    public List<TemperatureList> getTemperatureList(){
        return this.temperatureList;
    }
    public void setAge(int age){
        this.age = age;
    }
    public int getAge(){
        return this.age;
    }
}
