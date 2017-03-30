package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/3/29.
 */

public class bean_FamilyUserEle {

    /**
     * result : [{"createTimeString":"2017-03-30 15:16:10","updateTimeString":"","medicalrecord":"","mid":9,"trueName":"","marital":0,"id":0,"age":0},{"createTimeString":"2017-03-29 15:49:12","updateTimeString":"","medicalrecord":"","mid":10,"trueName":"","marital":0,"id":0,"age":0},{"createTimeString":"2017-03-29 15:49:31","updateTimeString":"","medicalrecord":"","mid":11,"trueName":"","marital":0,"id":0,"age":0}]
     * code : 0
     */

    private String code;
    private List<ResultBean> result;

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

    public static class ResultBean {
        /**
         * createTimeString : 2017-03-30 15:16:10
         * updateTimeString :
         * medicalrecord :
         * mid : 9
         * trueName :
         * marital : 0
         * id : 0
         * age : 0
         */

        private String createTimeString;
        private String updateTimeString;
        private String medicalrecord;
        private int mid;
        private String trueName;
        private int marital;
        private int id;
        private int age;

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

        public String getMedicalrecord() {
            return medicalrecord;
        }

        public void setMedicalrecord(String medicalrecord) {
            this.medicalrecord = medicalrecord;
        }

        public int getMid() {
            return mid;
        }

        public void setMid(int mid) {
            this.mid = mid;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getMarital() {
            return marital;
        }

        public void setMarital(int marital) {
            this.marital = marital;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
