package com.technology.yuyi.bean;

/**
 * Created by wanyu on 2017/3/22.
 */
//用户个人信息
public class bean_My_UserMsg {

    /**
     * createTimeString : 2017-03-20 10:34:19
     * updateTimeString :
     * gender : 0
     * idCard :
     * origin :
     * avatar : /static/image/avatar.jpeg
     * userName :
     * trueName :
     * marital : 0
     * id : 17743516301
     * age : 0
     */

    private String createTimeString;
    private String updateTimeString;
    private int gender;
    private String idCard;
    private String origin;
    private String avatar;
    private String userName;
    private String trueName;
    private int marital;
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
