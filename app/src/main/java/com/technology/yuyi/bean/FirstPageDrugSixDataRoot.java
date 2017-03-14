package com.technology.yuyi.bean;

import java.util.List;

/**
 * Created by liuhaidong on 2017/3/13.
 */

public class FirstPageDrugSixDataRoot {
    private int total;

    private List<FirstPageDrugSixData> rows;

    private List<String> colmodel;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<FirstPageDrugSixData> getRows() {
        return rows;
    }

    public void setRows(List<FirstPageDrugSixData> rows) {
        this.rows = rows;
    }

    public List<String> getColmodel() {
        return colmodel;
    }

    public void setColmodel(List<String> colmodel) {
        this.colmodel = colmodel;
    }
}
