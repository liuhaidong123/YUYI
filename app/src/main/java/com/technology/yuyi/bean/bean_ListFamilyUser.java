package com.technology.yuyi.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wanyu on 2017/3/20.
 */

public class bean_ListFamilyUser {


    /**
     * result : [{"createTimeString":"","role":0,"updateTimeString":"","nickName":"aaa","groupId":3,"telephone":0,"avatar":"/static/image/avatar.jpeg","oid":1,"localId":"","trueName":"bbb","id":7,"age":12,"info":"","status":1},{"createTimeString":"","role":0,"updateTimeString":"","nickName":"aaa","groupId":3,"telephone":0,"avatar":"/static/image/avatar.jpeg","oid":1,"localId":"","trueName":"bbb","id":8,"age":12,"info":"","status":1},{"createTimeString":"","role":0,"updateTimeString":"","nickName":"aaa","groupId":3,"telephone":0,"avatar":"/static/image/avatar.jpeg","oid":1,"localId":"","trueName":"bbb","id":9,"age":12,"info":"","status":1},{"createTimeString":"","role":0,"updateTimeString":"","nickName":"aaa","groupId":3,"telephone":0,"avatar":"/static/image/avatar.jpeg","oid":1,"localId":"","trueName":"bbb","id":10,"age":12,"info":"","status":1},{"createTimeString":"","role":0,"updateTimeString":"","nickName":"aaab","groupId":3,"telephone":13712345678,"avatar":"/static/image/avatar.jpeg","oid":1,"localId":"","trueName":"bbba","id":11,"age":12,"info":"","status":1},{"createTimeString":"","role":2,"updateTimeString":"","nickName":"","groupId":3,"telephone":13717883005,"avatar":"/static/image/avatar.jpeg","oid":0,"localId":"","trueName":"我自己","id":2,"age":0,"info":"","status":1},{"createTimeString":"","role":0,"updateTimeString":"","nickName":"","groupId":3,"telephone":0,"avatar":"/static/image/avatar.jpeg","oid":1,"localId":"","trueName":"bbb","id":6,"age":12,"info":"","status":1}]
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

    public static class ResultBean implements Serializable {
        /**
         * createTimeString :
         * role : 0
         * updateTimeString :
         * nickName : aaa
         * groupId : 3
         * telephone : 0
         * avatar : /static/image/avatar.jpeg
         * oid : 1
         * localId :
         * trueName : bbb
         * id : 7
         * age : 12
         * info :
         * status : 1
         */

        private String createTimeString;
        private int role;
        private String updateTimeString;
        private String nickName;
        private int groupId;
        private Long telephone;
        private String avatar;
        private int oid;
        private String localId;
        private String trueName;
        private int id;
        private int age;
        private String info;
        private int status;

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

        public Long getTelephone() {
            return telephone;
        }

        public void setTelephone(Long telephone) {
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

        public String getLocalId() {
            return localId;
        }

        public void setLocalId(String localId) {
            this.localId = localId;
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

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
