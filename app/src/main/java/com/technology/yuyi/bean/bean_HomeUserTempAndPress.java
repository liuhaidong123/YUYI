package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/8/17.
 */
//用户血压与温度
public class bean_HomeUserTempAndPress {

    /**
     * result : {"createTimeString":"","role":0,"updateTimeString":"","gender":0,"nickName":"妹妹","groupId":2,"telephone":17746499494,"avatar":"/static/image/2017815/a7a3459648c145bf939e441ef7ed3a2d.jpg","oid":null,"bloodpressureList":[{"createTimeString":"2017-08-12 13:35:15","diastolic":89,"systolic":129,"updateTimeString":"","humeuserId":101,"id":128},{"createTimeString":"2017-08-13 13:34:45","diastolic":80,"systolic":127,"updateTimeString":"","humeuserId":101,"id":127},{"createTimeString":"2017-08-15 13:34:05","diastolic":90,"systolic":139,"updateTimeString":"","humeuserId":101,"id":126},{"createTimeString":"2017-08-16 12:59:49","diastolic":94,"systolic":128,"updateTimeString":"","humeuserId":101,"id":125}],"trueName":"刘文3","marital":null,"id":101,"temperatureList":[{"createTimeString":"2017-08-11 13:36:52","updateTimeString":"","humeuserId":101,"id":111,"temperaturet":38},{"createTimeString":"2017-08-12 13:36:16","updateTimeString":"","humeuserId":101,"id":110,"temperaturet":37},{"createTimeString":"2017-08-13 13:36:06","updateTimeString":"","humeuserId":101,"id":109,"temperaturet":38},{"createTimeString":"2017-08-15 13:35:51","updateTimeString":"","humeuserId":101,"id":108,"temperaturet":37},{"createTimeString":"2017-08-16 13:43:47","updateTimeString":"","humeuserId":101,"id":112,"temperaturet":41.5}],"age":25}
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

    public static class ResultBean {
        /**
         * createTimeString :
         * role : 0
         * updateTimeString :
         * gender : 0
         * nickName : 妹妹
         * groupId : 2
         * telephone : 17746499494
         * avatar : /static/image/2017815/a7a3459648c145bf939e441ef7ed3a2d.jpg
         * oid : null
         * bloodpressureList : [{"createTimeString":"2017-08-12 13:35:15","diastolic":89,"systolic":129,"updateTimeString":"","humeuserId":101,"id":128},{"createTimeString":"2017-08-13 13:34:45","diastolic":80,"systolic":127,"updateTimeString":"","humeuserId":101,"id":127},{"createTimeString":"2017-08-15 13:34:05","diastolic":90,"systolic":139,"updateTimeString":"","humeuserId":101,"id":126},{"createTimeString":"2017-08-16 12:59:49","diastolic":94,"systolic":128,"updateTimeString":"","humeuserId":101,"id":125}]
         * trueName : 刘文3
         * marital : null
         * id : 101
         * temperatureList : [{"createTimeString":"2017-08-11 13:36:52","updateTimeString":"","humeuserId":101,"id":111,"temperaturet":38},{"createTimeString":"2017-08-12 13:36:16","updateTimeString":"","humeuserId":101,"id":110,"temperaturet":37},{"createTimeString":"2017-08-13 13:36:06","updateTimeString":"","humeuserId":101,"id":109,"temperaturet":38},{"createTimeString":"2017-08-15 13:35:51","updateTimeString":"","humeuserId":101,"id":108,"temperaturet":37},{"createTimeString":"2017-08-16 13:43:47","updateTimeString":"","humeuserId":101,"id":112,"temperaturet":41.5}]
         * age : 25
         */

        private String createTimeString;
        private int role;
        private String updateTimeString;
        private int gender;
        private String nickName;
        private int groupId;
        private long telephone;
        private String avatar;
        private Object oid;
        private String trueName;
        private Object marital;
        private int id;
        private int age;
        private List<BloodpressureListBean> bloodpressureList;
        private List<TemperatureListBean> temperatureList;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public long getTelephone() {
            return telephone;
        }

        public void setTelephone(long telephone) {
            this.telephone = telephone;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public Object getOid() {
            return oid;
        }

        public void setOid(Object oid) {
            this.oid = oid;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public Object getMarital() {
            return marital;
        }

        public void setMarital(Object marital) {
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

        public List<BloodpressureListBean> getBloodpressureList() {
            return bloodpressureList;
        }

        public void setBloodpressureList(List<BloodpressureListBean> bloodpressureList) {
            this.bloodpressureList = bloodpressureList;
        }

        public List<TemperatureListBean> getTemperatureList() {
            return temperatureList;
        }

        public void setTemperatureList(List<TemperatureListBean> temperatureList) {
            this.temperatureList = temperatureList;
        }

        public static class BloodpressureListBean {
            /**
             * createTimeString : 2017-08-12 13:35:15
             * diastolic : 89
             * systolic : 129
             * updateTimeString :
             * humeuserId : 101
             * id : 128
             */

            private String createTimeString;
            private float diastolic;
            private float systolic;
            private String updateTimeString;
            private int humeuserId;
            private int id;

            public String getCreateTimeString() {
                return createTimeString;
            }

            public void setCreateTimeString(String createTimeString) {
                this.createTimeString = createTimeString;
            }

            public float getDiastolic() {
                return diastolic;
            }

            public void setDiastolic(float diastolic) {
                this.diastolic = diastolic;
            }

            public float getSystolic() {
                return systolic;
            }

            public void setSystolic(float systolic) {
                this.systolic = systolic;
            }

            public String getUpdateTimeString() {
                return updateTimeString;
            }

            public void setUpdateTimeString(String updateTimeString) {
                this.updateTimeString = updateTimeString;
            }

            public int getHumeuserId() {
                return humeuserId;
            }

            public void setHumeuserId(int humeuserId) {
                this.humeuserId = humeuserId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }

        public static class TemperatureListBean {
            /**
             * createTimeString : 2017-08-11 13:36:52
             * updateTimeString :
             * humeuserId : 101
             * id : 111
             * temperaturet : 38
             */

            private String createTimeString;
            private String updateTimeString;
            private int humeuserId;
            private int id;
            private float temperaturet;

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

            public int getHumeuserId() {
                return humeuserId;
            }

            public void setHumeuserId(int humeuserId) {
                this.humeuserId = humeuserId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public float getTemperaturet() {
                return temperaturet;
            }

            public void setTemperaturet(float temperaturet) {
                this.temperaturet = temperaturet;
            }
        }
    }
}
