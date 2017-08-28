package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/8/24.
 */

public class BeanDrugStates
{
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * result : [{"createTimeString":"2017-03-30 14:41:20","updateTimeString":"","boilMedicineList":[{"createTimeString":"2017-03-30 14:41:58","prescriptionId":1,"updateTimeString":"","stateText":"开具药方","id":1},{"createTimeString":"2017-03-30 14:45:31","prescriptionId":1,"updateTimeString":"","stateText":"药品清单已接收","id":2},{"createTimeString":"2017-03-30 14:50:11","prescriptionId":1,"updateTimeString":"","stateText":"配药已完成","id":3},{"createTimeString":"2017-03-30 15:43:33","prescriptionId":1,"updateTimeString":"","stateText":"正在煎药","id":4}],"title":"我的药方","physicianId":null,"hospitalId":null,"humeuserId":null,"takeNote":"","id":1,"state":1,"persinalId":13717883005,"medincineCount":null},{"createTimeString":"2017-03-29 14:44:55","updateTimeString":"","boilMedicineList":[],"title":"我的药方","physicianId":null,"hospitalId":null,"humeuserId":null,"takeNote":"","id":2,"state":1,"persinalId":13717883005,"medincineCount":null}]
     * code : 0
     */
    String message;
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
         * createTimeString : 2017-03-30 14:41:20
         * updateTimeString :
         * boilMedicineList : [{"createTimeString":"2017-03-30 14:41:58","prescriptionId":1,"updateTimeString":"","stateText":"开具药方","id":1},{"createTimeString":"2017-03-30 14:45:31","prescriptionId":1,"updateTimeString":"","stateText":"药品清单已接收","id":2},{"createTimeString":"2017-03-30 14:50:11","prescriptionId":1,"updateTimeString":"","stateText":"配药已完成","id":3},{"createTimeString":"2017-03-30 15:43:33","prescriptionId":1,"updateTimeString":"","stateText":"正在煎药","id":4}]
         * title : 我的药方
         * physicianId : null
         * hospitalId : null
         * humeuserId : null
         * takeNote :
         * id : 1
         * state : 1
         * persinalId : 13717883005
         * medincineCount : null
         */

        private String createTimeString;
        private String updateTimeString;
        private String title;
        private Object physicianId;
        private Object hospitalId;
        private Object humeuserId;
        private String takeNote;
        private int id;
        private int state;
        private long persinalId;
        private Object medincineCount;
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

        public Object getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(Object physicianId) {
            this.physicianId = physicianId;
        }

        public Object getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(Object hospitalId) {
            this.hospitalId = hospitalId;
        }

        public Object getHumeuserId() {
            return humeuserId;
        }

        public void setHumeuserId(Object humeuserId) {
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getPersinalId() {
            return persinalId;
        }

        public void setPersinalId(long persinalId) {
            this.persinalId = persinalId;
        }

        public Object getMedincineCount() {
            return medincineCount;
        }

        public void setMedincineCount(Object medincineCount) {
            this.medincineCount = medincineCount;
        }

        public List<BoilMedicineListBean> getBoilMedicineList() {
            return boilMedicineList;
        }

        public void setBoilMedicineList(List<BoilMedicineListBean> boilMedicineList) {
            this.boilMedicineList = boilMedicineList;
        }

        public static class BoilMedicineListBean {
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
