package com.technology.yuyi.bean;

/**
 * Created by liuhaidong on 2017/3/16.
 */

public class HospitalOutPatient {
    private String clinicName;

    private String createTimeString;

    private String updateTimeString;

    private String createTime;

    private int departmentId;

    private String updateTime;

    private int id;

    private int oid;

    private String localId;

    private String info;

    private int status;

    public void setClinicName(String clinicName){
        this.clinicName = clinicName;
    }
    public String getClinicName(){
        return this.clinicName;
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
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setDepartmentId(int departmentId){
        this.departmentId = departmentId;
    }
    public int getDepartmentId(){
        return this.departmentId;
    }
    public void setUpdateTime(String updateTime){
        this.updateTime = updateTime;
    }
    public String getUpdateTime(){
        return this.updateTime;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setOid(int oid){
        this.oid = oid;
    }
    public int getOid(){
        return this.oid;
    }
    public void setLocalId(String localId){
        this.localId = localId;
    }
    public String getLocalId(){
        return this.localId;
    }
    public void setInfo(String info){
        this.info = info;
    }
    public String getInfo(){
        return this.info;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
}
