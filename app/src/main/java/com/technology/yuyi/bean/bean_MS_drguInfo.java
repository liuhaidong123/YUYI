package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/3/15.
 */
//药品详细信息
public class bean_MS_drguInfo {

    /**
     * createTimeString :
     * productSpecification : 产品规格
     * drugsName : 999感冒灵1
     * specificationsd : 一盒3包
     * oid : 1
     * packing : 10g x3
     * drugsCurrencyName : 999感冒灵1
     * localId :
     * drugsFunction : 适用症/功能主治
     * number : 20
     * price : 20
     * drugsType : 颗粒
     * drugsDosage : 用法用量
     * specificationsdList : [{"createTimeString":"","updateTimeString":"","specificationsdNumber":3,"updateTime":null,"oid":1,"localId":"","unit":"包","specificationsdName":"1盒3包","createTime":null,"drugsId":1,"price":45,"id":1,"info":"","status":1},{"createTimeString":"","updateTimeString":"","specificationsdNumber":6,"updateTime":null,"oid":2,"localId":"","unit":"","specificationsdName":"1盒6包","createTime":null,"drugsId":1,"price":65,"id":2,"info":"","status":1},{"createTimeString":"","updateTimeString":"","specificationsdNumber":8,"updateTime":null,"oid":3,"localId":"","unit":"","specificationsdName":"1盒9包","createTime":null,"drugsId":1,"price":75,"id":3,"info":"","status":1}]
     * details : 专治感冒
     * id : 1
     * brand : 999
     * businesses : 华润三九医药公司
     * info :
     * updateTimeString :
     * dosageForm : 颗粒
     * updateTime : null
     * approvalNumber : 国药准字Z2017021423434
     * picture : /static/image/999.jpg
     * categoryId1 : 1
     * categoryId2 : 11
     * createTime : null
     * commodityName : 999感冒灵1
     * status : 1
     */

    private String createTimeString;
    private String productSpecification;
    private String drugsName;
    private String specificationsd;
    private int oid;
    private String packing;
    private String drugsCurrencyName;
    private String localId;
    private String drugsFunction;
    private int number;
    private int price;
    private String drugsType;
    private String drugsDosage;
    private String details;
    private int id;
    private String brand;
    private String businesses;
    private String info;
    private String updateTimeString;
    private String dosageForm;
    private Object updateTime;
    private String approvalNumber;
    private String picture;
    private int categoryId1;
    private int categoryId2;
    private Object createTime;
    private String commodityName;
    private int status;
    private List<SpecificationsdListBean> specificationsdList;

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    public String getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(String productSpecification) {
        this.productSpecification = productSpecification;
    }

    public String getDrugsName() {
        return drugsName;
    }

    public void setDrugsName(String drugsName) {
        this.drugsName = drugsName;
    }

    public String getSpecificationsd() {
        return specificationsd;
    }

    public void setSpecificationsd(String specificationsd) {
        this.specificationsd = specificationsd;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getPacking() {
        return packing;
    }

    public void setPacking(String packing) {
        this.packing = packing;
    }

    public String getDrugsCurrencyName() {
        return drugsCurrencyName;
    }

    public void setDrugsCurrencyName(String drugsCurrencyName) {
        this.drugsCurrencyName = drugsCurrencyName;
    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getDrugsFunction() {
        return drugsFunction;
    }

    public void setDrugsFunction(String drugsFunction) {
        this.drugsFunction = drugsFunction;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDrugsType() {
        return drugsType;
    }

    public void setDrugsType(String drugsType) {
        this.drugsType = drugsType;
    }

    public String getDrugsDosage() {
        return drugsDosage;
    }

    public void setDrugsDosage(String drugsDosage) {
        this.drugsDosage = drugsDosage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBusinesses() {
        return businesses;
    }

    public void setBusinesses(String businesses) {
        this.businesses = businesses;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUpdateTimeString() {
        return updateTimeString;
    }

    public void setUpdateTimeString(String updateTimeString) {
        this.updateTimeString = updateTimeString;
    }

    public String getDosageForm() {
        return dosageForm;
    }

    public void setDosageForm(String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public Object getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Object updateTime) {
        this.updateTime = updateTime;
    }

    public String getApprovalNumber() {
        return approvalNumber;
    }

    public void setApprovalNumber(String approvalNumber) {
        this.approvalNumber = approvalNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getCategoryId1() {
        return categoryId1;
    }

    public void setCategoryId1(int categoryId1) {
        this.categoryId1 = categoryId1;
    }

    public int getCategoryId2() {
        return categoryId2;
    }

    public void setCategoryId2(int categoryId2) {
        this.categoryId2 = categoryId2;
    }

    public Object getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Object createTime) {
        this.createTime = createTime;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<SpecificationsdListBean> getSpecificationsdList() {
        return specificationsdList;
    }

    public void setSpecificationsdList(List<SpecificationsdListBean> specificationsdList) {
        this.specificationsdList = specificationsdList;
    }

    public static class SpecificationsdListBean {
        /**
         * createTimeString :
         * updateTimeString :
         * specificationsdNumber : 3
         * updateTime : null
         * oid : 1
         * localId :
         * unit : 包
         * specificationsdName : 1盒3包
         * createTime : null
         * drugsId : 1
         * price : 45
         * id : 1
         * info :
         * status : 1
         */

        private String createTimeString;
        private String updateTimeString;
        private int specificationsdNumber;
        private Object updateTime;
        private int oid;
        private String localId;
        private String unit;
        private String specificationsdName;
        private Object createTime;
        private int drugsId;
        private int price;
        private int id;
        private String info;
        private int status;

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

        public int getSpecificationsdNumber() {
            return specificationsdNumber;
        }

        public void setSpecificationsdNumber(int specificationsdNumber) {
            this.specificationsdNumber = specificationsdNumber;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
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

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getSpecificationsdName() {
            return specificationsdName;
        }

        public void setSpecificationsdName(String specificationsdName) {
            this.specificationsdName = specificationsdName;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public int getDrugsId() {
            return drugsId;
        }

        public void setDrugsId(int drugsId) {
            this.drugsId = drugsId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
