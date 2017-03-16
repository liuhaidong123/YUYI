package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/3/15.
 */
//药品商城首页返回的数据
public class bean_MS_home {

    private List<CategoryBean> category;
    private List<DrugsBean> drugs;

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public List<DrugsBean> getDrugs() {
        return drugs;
    }

    public void setDrugs(List<DrugsBean> drugs) {
        this.drugs = drugs;
    }

    public static class CategoryBean {
        /**
         * id : 1
         * name : 常用
         * treeCode : 1
         * platMark : 0
         * level : 1
         * open : true
         * file :
         * children : {"list":[],"size":0}
         * pid : null
         */

        private int id;
        private String name;
        private String treeCode;
        private int platMark;
        private int level;
        private boolean open;
        private String file;
        private ChildrenBean children;
        private Object pid;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTreeCode() {
            return treeCode;
        }

        public void setTreeCode(String treeCode) {
            this.treeCode = treeCode;
        }

        public int getPlatMark() {
            return platMark;
        }

        public void setPlatMark(int platMark) {
            this.platMark = platMark;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public boolean isOpen() {
            return open;
        }

        public void setOpen(boolean open) {
            this.open = open;
        }

        public String getFile() {
            return file;
        }

        public void setFile(String file) {
            this.file = file;
        }

        public ChildrenBean getChildren() {
            return children;
        }

        public void setChildren(ChildrenBean children) {
            this.children = children;
        }

        public Object getPid() {
            return pid;
        }

        public void setPid(Object pid) {
            this.pid = pid;
        }

        public static class ChildrenBean {
            /**
             * list : []
             * size : 0
             */

            private int size;
            private List<?> list;

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public List<?> getList() {
                return list;
            }

            public void setList(List<?> list) {
                this.list = list;
            }
        }
    }

    public static class DrugsBean {
        /**
         * status : 1
         * createTime : null
         * updateTime : null
         * localId : null
         * info : null
         * id : 1
         * categoryId1 : 1
         * categoryId2 : 11
         * drugsName : 999感冒灵1
         * price : 20
         * number : 20
         * picture : /static/image/999.jpg
         * details : 专治感冒
         * specificationsd : 一盒3包
         * packing : 10g x3
         * commodityName : 999感冒灵1
         * drugsCurrencyName : 999感冒灵1
         * approvalNumber : 国药准字Z2017021423434
         * businesses : 华润三九医药公司
         * brand : 999
         * drugsType : 颗粒
         * dosageForm : 颗粒
         * productSpecification : 产品规格
         * drugsDosage : 用法用量
         * drugsFunction : 适用症/功能主治
         * oid : 1
         * specificationsdList : null
         * createTimeString : null
         * updateTimeString : null
         */

        private int status;
        private Object createTime;
        private Object updateTime;
        private Object localId;
        private Object info;
        private int id;
        private int categoryId1;
        private int categoryId2;
        private String drugsName;
        private int price;
        private int number;
        private String picture;
        private String details;
        private String specificationsd;
        private String packing;
        private String commodityName;
        private String drugsCurrencyName;
        private String approvalNumber;
        private String businesses;
        private String brand;
        private String drugsType;
        private String dosageForm;
        private String productSpecification;
        private String drugsDosage;
        private String drugsFunction;
        private int oid;
        private Object specificationsdList;
        private Object createTimeString;
        private Object updateTimeString;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(Object updateTime) {
            this.updateTime = updateTime;
        }

        public Object getLocalId() {
            return localId;
        }

        public void setLocalId(Object localId) {
            this.localId = localId;
        }

        public Object getInfo() {
            return info;
        }

        public void setInfo(Object info) {
            this.info = info;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getDrugsName() {
            return drugsName;
        }

        public void setDrugsName(String drugsName) {
            this.drugsName = drugsName;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public String getSpecificationsd() {
            return specificationsd;
        }

        public void setSpecificationsd(String specificationsd) {
            this.specificationsd = specificationsd;
        }

        public String getPacking() {
            return packing;
        }

        public void setPacking(String packing) {
            this.packing = packing;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getDrugsCurrencyName() {
            return drugsCurrencyName;
        }

        public void setDrugsCurrencyName(String drugsCurrencyName) {
            this.drugsCurrencyName = drugsCurrencyName;
        }

        public String getApprovalNumber() {
            return approvalNumber;
        }

        public void setApprovalNumber(String approvalNumber) {
            this.approvalNumber = approvalNumber;
        }

        public String getBusinesses() {
            return businesses;
        }

        public void setBusinesses(String businesses) {
            this.businesses = businesses;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getDrugsType() {
            return drugsType;
        }

        public void setDrugsType(String drugsType) {
            this.drugsType = drugsType;
        }

        public String getDosageForm() {
            return dosageForm;
        }

        public void setDosageForm(String dosageForm) {
            this.dosageForm = dosageForm;
        }

        public String getProductSpecification() {
            return productSpecification;
        }

        public void setProductSpecification(String productSpecification) {
            this.productSpecification = productSpecification;
        }

        public String getDrugsDosage() {
            return drugsDosage;
        }

        public void setDrugsDosage(String drugsDosage) {
            this.drugsDosage = drugsDosage;
        }

        public String getDrugsFunction() {
            return drugsFunction;
        }

        public void setDrugsFunction(String drugsFunction) {
            this.drugsFunction = drugsFunction;
        }

        public int getOid() {
            return oid;
        }

        public void setOid(int oid) {
            this.oid = oid;
        }

        public Object getSpecificationsdList() {
            return specificationsdList;
        }

        public void setSpecificationsdList(Object specificationsdList) {
            this.specificationsdList = specificationsdList;
        }

        public Object getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(Object createTimeString) {
            this.createTimeString = createTimeString;
        }

        public Object getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(Object updateTimeString) {
            this.updateTimeString = updateTimeString;
        }
    }
}
