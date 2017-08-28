package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/3/30.
 */
//获取最新10条消息
public class bean_MyMessage {

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * result : [{"createTimeString":"2017-03-29 11:25:30","msgType":0,"updateTimeString":"","content":"","referId":0,"id":28,"operation":0,"persinalId":0},{"createTimeString":"2017-03-24 11:14:57","msgType":2,"updateTimeString":"","content":"操作类型--暂时未定义","referId":0,"id":4,"operation":0,"persinalId":0},{"createTimeString":"2017-03-22 11:14:33","msgType":1,"updateTimeString":"","content":"引用编号---根据消息类型引用不同的表的编号","referId":0,"id":3,"operation":0,"persinalId":0},{"createTimeString":"2017-03-23 11:12:40","msgType":1,"updateTimeString":"","content":"消息类型--1=宇医公告，2=挂号通知","referId":0,"id":1,"operation":0,"persinalId":0}]
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
         * createTimeString : 2017-03-29 11:25:30
         * msgType : 0
         * updateTimeString :
         * content :
         * referId : 0
         * id : 28
         * operation : 0
         * persinalId : 0
         */

        private String createTimeString;
        private int msgType;
        private String updateTimeString;
        private String content;
        private int referId;
        private int id;
        private int operation;
        private long persinalId;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public int getMsgType() {
            return msgType;
        }

        public void setMsgType(int msgType) {
            this.msgType = msgType;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getReferId() {
            return referId;
        }

        public void setReferId(int referId) {
            this.referId = referId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOperation() {
            return operation;
        }

        public void setOperation(int operation) {
            this.operation = operation;
        }

        public long getPersinalId() {
            return persinalId;
        }

        public void setPersinalId(long persinalId) {
            this.persinalId = persinalId;
        }
    }
}
