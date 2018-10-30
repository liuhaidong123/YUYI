package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by liuhaidong on 2018/8/23.
 */

public class DoctorRoot {
    private int code;

    private List<PhysicianList> physicianList;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<PhysicianList> getPhysicianList() {
        return physicianList;
    }

    public void setPhysicianList(List<PhysicianList> physicianList) {
        this.physicianList = physicianList;
    }

}


