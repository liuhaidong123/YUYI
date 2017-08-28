package com.technology.yuyi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/3/22.
 */
//电子病历列表(用户自己的)
public class bean_MedicalRecordList {

    /**
     * result : [{"departmentName":"内科","createTimeString":"2017-08-21 14:33:03","updateTimeString":"","departmentId":1,"homeuserid":94,"medicalrecord":"电子病历","hospitalName":"涿州市中医医院","picture":"/static/image/201773/30a41c1a8cfe4798a5bde731fec8f312.jpg;/static/image/201773/30a41c1a8cfe4798a5bde731fec8f312.jpg;/static/image/201773/30a41c1a8cfe4798a5bde731fec8f312.jpg","physicianId":1,"physicianName":"李时珍","hospitalId":1,"id":17,"persinalId":17743516301}]
     * code : 0
     */

    private String code;
    private List<ResultBean> result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * departmentName : 内科
         * createTimeString : 2017-08-21 14:33:03
         * updateTimeString :
         * departmentId : 1
         * homeuserid : 94
         * medicalrecord : 电子病历
         * hospitalName : 涿州市中医医院
         * picture : /static/image/201773/30a41c1a8cfe4798a5bde731fec8f312.jpg;/static/image/201773/30a41c1a8cfe4798a5bde731fec8f312.jpg;/static/image/201773/30a41c1a8cfe4798a5bde731fec8f312.jpg
         * physicianId : 1
         * physicianName : 李时珍
         * hospitalId : 1
         * id : 17
         * persinalId : 17743516301
         */

        private String departmentName;
        private String createTimeString;
        private String updateTimeString;
        private int departmentId;
        private int homeuserid;
        private String medicalrecord;
        private String hospitalName;
        private String picture;
        private int physicianId;
        private String physicianName;
        private int hospitalId;
        private int id;
        private long persinalId;

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public int getDepartmentId() {
            return departmentId;
        }

        public void setDepartmentId(int departmentId) {
            this.departmentId = departmentId;
        }

        public int getHomeuserid() {
            return homeuserid;
        }

        public void setHomeuserid(int homeuserid) {
            this.homeuserid = homeuserid;
        }

        public String getMedicalrecord() {
            return medicalrecord;
        }

        public void setMedicalrecord(String medicalrecord) {
            this.medicalrecord = medicalrecord;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(int physicianId) {
            this.physicianId = physicianId;
        }

        public String getPhysicianName() {
            return physicianName;
        }

        public void setPhysicianName(String physicianName) {
            this.physicianName = physicianName;
        }

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getPersinalId() {
            return persinalId;
        }

        public void setPersinalId(long persinalId) {
            this.persinalId = persinalId;
        }
    }
}
