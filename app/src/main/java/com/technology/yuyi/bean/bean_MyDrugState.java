package com.technology.yuyi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/3/30.
 */
//我的药品状态
public class bean_MyDrugState implements Serializable{

    /**
     * result : {"createTimeString":"2017-03-30 14:41:20","updateTimeString":"","boilMedicineList":[{"createTimeString":"2017-03-30 14:41:58","prescriptionId":1,"updateTimeString":"","stateText":"开具药方","id":1},{"createTimeString":"2017-03-30 14:45:31","prescriptionId":1,"updateTimeString":"","stateText":"药品清单已接收","id":2},{"createTimeString":"2017-03-30 14:50:11","prescriptionId":1,"updateTimeString":"","stateText":"配药已完成","id":3},{"createTimeString":"2017-03-30 15:43:33","prescriptionId":1,"updateTimeString":"","stateText":"正在煎药","id":4}],"title":"我的药方","physicianId":0,"hospitalId":0,"humeuserId":0,"takeNote":"","id":1,"persinalId":13717883005,"medincineCount":0}
     * code : 0
     */

    private ResultBean result;
    private String code;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ResultBean implements Serializable{
        /**
         * createTimeString : 2017-03-30 14:41:20
         * updateTimeString :
         * boilMedicineList : [{"createTimeString":"2017-03-30 14:41:58","prescriptionId":1,"updateTimeString":"","stateText":"开具药方","id":1},{"createTimeString":"2017-03-30 14:45:31","prescriptionId":1,"updateTimeString":"","stateText":"药品清单已接收","id":2},{"createTimeString":"2017-03-30 14:50:11","prescriptionId":1,"updateTimeString":"","stateText":"配药已完成","id":3},{"createTimeString":"2017-03-30 15:43:33","prescriptionId":1,"updateTimeString":"","stateText":"正在煎药","id":4}]
         * title : 我的药方
         * physicianId : 0
         * hospitalId : 0
         * humeuserId : 0
         * takeNote :
         * id : 1
         * persinalId : 13717883005
         * medincineCount : 0
         */

        private String createTimeString;
        private String updateTimeString;
        private String title;
        private int physicianId;
        private int hospitalId;
        private int humeuserId;
        private String takeNote;
        private int id;
        private long persinalId;
        private int medincineCount;
        private List<BoilMedicineListBean> boilMedicineList;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(int physicianId) {
            this.physicianId = physicianId;
        }

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
        }

        public int getHumeuserId() {
            return humeuserId;
        }

        public void setHumeuserId(int humeuserId) {
            this.humeuserId = humeuserId;
        }

        public String getTakeNote() {
            return takeNote;
        }

        public void setTakeNote(String takeNote) {
            this.takeNote = takeNote;
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

        public int getMedincineCount() {
            return medincineCount;
        }

        public void setMedincineCount(int medincineCount) {
            this.medincineCount = medincineCount;
        }

        public List<BoilMedicineListBean> getBoilMedicineList() {
            return boilMedicineList;
        }

        public void setBoilMedicineList(List<BoilMedicineListBean> boilMedicineList) {
            this.boilMedicineList = boilMedicineList;
        }

        public static class BoilMedicineListBean implements Serializable{
            /**
             * createTimeString : 2017-03-30 14:41:58
             * prescriptionId : 1
             * updateTimeString :
             * stateText : 开具药方
             * id : 1
             */
            private String createTimeString;
            private int prescriptionId;
            private String updateTimeString;
            private String stateText;
            private int id;

            public String getCreateTimeString() {
                return createTimeString;
            }

            public void setCreateTimeString(String createTimeString) {
                this.createTimeString = createTimeString;
            }

            public int getPrescriptionId() {
                return prescriptionId;
            }

            public void setPrescriptionId(int prescriptionId) {
                this.prescriptionId = prescriptionId;
            }

            public String getUpdateTimeString() {
                return updateTimeString;
            }

            public void setUpdateTimeString(String updateTimeString) {
                this.updateTimeString = updateTimeString;
            }

            public String getStateText() {
                return stateText;
            }

            public void setStateText(String stateText) {
                this.stateText = stateText;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
