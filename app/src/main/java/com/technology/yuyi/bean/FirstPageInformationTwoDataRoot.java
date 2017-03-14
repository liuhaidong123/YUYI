package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by liuhaidong on 2017/3/13.
 */

public class FirstPageInformationTwoDataRoot {
    private int total;

    private List<FirstPageInformationTwoData> rows;

    private List<String> colmodel;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<FirstPageInformationTwoData> getRows() {
        return rows;
    }

    public void setRows(List<FirstPageInformationTwoData> rows) {
        this.rows = rows;
    }

    public List<String> getColmodel() {
        return colmodel;
    }

    public void setColmodel(List<String> colmodel) {
        this.colmodel = colmodel;
    }
}
