package com.technology.yuyi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/3/20.
 */

public class bean_ListFamilyUser {


    /**
     * result : [{"createTimeString":"","role":2,"updateTimeString":"","gender":0,"nickName":"","groupId":2,"telephone":17743516301,"avatar":"/static/image/avatar.jpeg","oid":0,"trueName":"我自己","id":94,"age":0}]
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

    public static class ResultBean implements Serializable{
        public String getBit64() {
            return bit64;
        }

        public void setBit64(String bit64) {
            this.bit64 = bit64;
        }

        /**
         * createTimeString :
         * role : 2
         * updateTimeString :
         * gender : 0
         * nickName :
         * groupId : 2
         * telephone : 17743516301
         * avatar : /static/image/avatar.jpeg
         * oid : 0
         * trueName : 我自己
         * id : 94
         * age : 0
         */
        private String bit64;

        private String createTimeString;
        private int role;
        private String updateTimeString;
        private int gender;
        private String nickName;
        private int groupId;
        private long telephone;
        private String avatar;
        private int oid;
        private String trueName;
        private int id;
        private int age;

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

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
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
