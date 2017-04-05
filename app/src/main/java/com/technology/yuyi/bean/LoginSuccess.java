package com.technology.yuyi.bean;

/**
 * Created by liuhaidong on 2017/3/15.
 */

public class LoginSuccess {
    private String result;

    private String code;
    /**
     * personal : {"createTimeString":"2017-03-28 13:51:50","updateTimeString":"","gender":0,"idCard":"427894646644664646","origin":"","avatar":"/static/image/2017/3/31/1490924397362.jpg","userName":"","token":"Dn8kXt1C1KvU1wB4K3ViJSm+/4mVRkjkKe63vV33TZdIRtvhLu3OGbfoxCZ3wz2opU+yRkFaBOup69/S6T5CakGKL8P8leKr","trueName":"刘文","marital":null,"id":17734862622,"age":12}
     */

    private PersonalBean personal;

    public void setResult(String result){
        this.result = result;
    }
    public String getResult(){
        return this.result;
    }
    public void setCode(String code){
        this.code = code;
    }
    public String getCode(){
        return this.code;
    }

    public PersonalBean getPersonal() {
        return personal;
    }

    public void setPersonal(PersonalBean personal) {
        this.personal = personal;
    }

    public static class PersonalBean {
        /**
         * createTimeString : 2017-03-28 13:51:50
         * updateTimeString :
         * gender : 0
         * idCard : 427894646644664646
         * origin :
         * avatar : /static/image/2017/3/31/1490924397362.jpg
         * userName :
         * token : Dn8kXt1C1KvU1wB4K3ViJSm+/4mVRkjkKe63vV33TZdIRtvhLu3OGbfoxCZ3wz2opU+yRkFaBOup69/S6T5CakGKL8P8leKr
         * trueName : 刘文
         * marital : null
         * id : 17734862622
         * age : 12
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
