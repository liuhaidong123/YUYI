package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/3/22.
 */
//电子病历列表
public class bean_MedicalRecordList {

    /**
     * result : [{"createTimeString":"2017-03-21 14:21:48","physicianId":13892873837,"updateTimeString":"","medicalrecord":"精神病很严重","id":3,"persinalId":18881882888},{"createTimeString":"2017-03-22 14:26:19","physicianId":13892873837,"updateTimeString":"","medicalrecord":"有病","id":4,"persinalId":18881882888},{"createTimeString":"2017-03-24 14:26:22","physicianId":13892873837,"updateTimeString":"","medicalrecord":"脑壳有问题","id":5,"persinalId":18881882888}]
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
         * createTimeString : 2017-03-21 14:21:48
         * physicianId : 13892873837
         * updateTimeString :
         * medicalrecord : 精神病很严重
         * id : 3
         * persinalId : 18881882888
         */

        private String createTimeString;
        private long physicianId;
        private String updateTimeString;
        private String medicalrecord;
        private int id;
        private long persinalId;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public long getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(long physicianId) {
            this.physicianId = physicianId;
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
