package com.technology.yuyi.bean;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MyEntity {
    private String title;
    private boolean isSelected;

    public MyEntity() {

    }

    public MyEntity(String title, boolean isSelected) {
        this.title = title;
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
