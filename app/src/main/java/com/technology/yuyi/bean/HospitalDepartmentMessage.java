package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by liuhaidong on 2017/3/16.
 */

public class HospitalDepartmentMessage {
    private String departmentName;

    private String createTimeString;

    private String updateTimeString;

    private String updateTime;

    private int oid;

    private String localId;

    private List<HospitalOutPatient> clinicList;

    private String createTime;

    private int hospitalId;

    private int id;

    private String info;

    private int status;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    private boolean open;
    public void setDepartmentName(String departmentName){
        this.departmentName = departmentName;
    }
    public String getDepartmentName(){
        return this.departmentName;
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
    public void setUpdateTime(String updateTime){
        this.updateTime = updateTime;
    }
    public String getUpdateTime(){
        return this.updateTime;
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
    public void setClinicList(List<HospitalOutPatient> clinicList){
        this.clinicList = clinicList;
    }
    public List<HospitalOutPatient> getClinicList(){
        return this.clinicList;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
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
