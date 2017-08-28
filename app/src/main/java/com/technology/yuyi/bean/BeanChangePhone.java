package com.technology.yuyi.bean;

/**
 * Created by wanyu on 2017/8/23.
 */

public class BeanChangePhone {

    /**
     * message : shibai
     * result : 2EE6101DB3715CC6B4CDE1D1DB9ECF4B
     * code : 0
     * personal : {"createTimeString":"2017-04-27 20:08:05","updateTimeString":"","gender":0,"idCard":"410424646466464464","origin":"","avatar":"/static/image/2017822/5fc947bcc5a343fa9de9f1021783900b.jpg","userName":"","token":"","trueName":"刘文","marital":null,"id":17743516301,"age":25}
     */

    private String message;
    private String result;
    private String code;
    private PersonalBean personal;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public PersonalBean getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalBean personal) {
        this.personal = personal;
    }

    public static class PersonalBean {
        /**
         * createTimeString : 2017-04-27 20:08:05
         * updateTimeString :
         * gender : 0
         * idCard : 410424646466464464
         * origin :
         * avatar : /static/image/2017822/5fc947bcc5a343fa9de9f1021783900b.jpg
         * userName :
         * token :
         * trueName : 刘文
         * marital : null
         * id : 17743516301
         * age : 25
         */

        private String createTimeString;
        private String updateTimeString;
        private int gender;
        private String idCard;
        private String origin;
        private String avatar;
        private String userName;
        private String token;
        private String trueName;
        private Object marital;
        private long id;
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

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
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

        public long getId() {
            return id;
        }

        public void setId(long id) {
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
