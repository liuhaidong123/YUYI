package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by wanyu on 2017/3/14.
 */

public class bean_MS_allkinds {

    private List<CategoryBean> category;

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public static class CategoryBean {
        /**
         * id : 1
         * name : 常用
         * platMark : 0
         * level : 1
         * pId : null
         * treeCode : 1
         * open : true
         * file :
         * isParent : true
         * children : [{"id":"6","name":"中药调理","platMark":"0","level":"2","pId":"1","treeCode":"6","open":true,"file":"","isParent":false},{"id":"7","name":"肠胃用药","platMark":"0","level":"2","pId":"1","treeCode":"7","open":true,"file":"","isParent":false},{"id":"8","name":"保健滋补","platMark":"0","level":"2","pId":"1","treeCode":"8","open":true,"file":"","isParent":false},{"id":"9","name":"眼鼻喉耳","platMark":"0","level":"2","pId":"1","treeCode":"9","open":true,"file":"","isParent":false},{"id":"10","name":"皮肤用药","platMark":"0","level":"2","pId":"1","treeCode":"10","open":true,"file":"","isParent":false},{"id":"11","name":"感冒发烧","platMark":"0","level":"2","pId":"1","treeCode":"11","open":true,"file":"","isParent":false},{"id":"12","name":"调节免疫","platMark":"0","level":"2","pId":"1","treeCode":"12","open":true,"file":"","isParent":false},{"id":"13","name":"维生素钙片","platMark":"0","level":"2","pId":"1","treeCode":"13","open":true,"file":"","isParent":false}]
         */

        private String id;
        private String name;
        private String platMark;
        private String level;
        private String pId;
        private String treeCode;
        private boolean open;
        private String file;
        private boolean isParent;
        private List<ChildrenBean> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPlatMark() {
            return platMark;
        }

        public void setPlatMark(String platMark) {
            this.platMark = platMark;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getPId() {
            return pId;
        }

        public void setPId(String pId) {
            this.pId = pId;
        }

        public String getTreeCode() {
            return treeCode;
        }

        public void setTreeCode(String treeCode) {
            this.treeCode = treeCode;
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

        public boolean isIsParent() {
            return isParent;
        }

        public void setIsParent(boolean isParent) {
            this.isParent = isParent;
        }

        public List<ChildrenBean> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenBean> children) {
            this.children = children;
        }

        public static class ChildrenBean {
            /**
             * id : 6
             * name : 中药调理
             * platMark : 0
             * level : 2
             * pId : 1
             * treeCode : 6
             * open : true
             * file :
             * isParent : false
             */

            private String id;
            private String name;
            private String platMark;
            private String level;
            private String pId;
            private String treeCode;
            private boolean open;
            private String file;
            private boolean isParent;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPlatMark() {
                return platMark;
            }

            public void setPlatMark(String platMark) {
                this.platMark = platMark;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getPId() {
                return pId;
            }

            public void setPId(String pId) {
                this.pId = pId;
            }

            public String getTreeCode() {
                return treeCode;
            }

            public void setTreeCode(String treeCode) {
                this.treeCode = treeCode;
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

            public boolean isIsParent() {
                return isParent;
            }

            public void setIsParent(boolean isParent) {
                this.isParent = isParent;
            }
        }
    }
}
