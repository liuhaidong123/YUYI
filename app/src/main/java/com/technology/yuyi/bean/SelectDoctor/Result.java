package com.technology.yuyi.bean.SelectDoctor;

import java.util.List;

/**
 * Created by liuhaidong on 2017/3/24.
 */

public class Result {
    private String datastr;

    private List<DatenumberList> datenumberList;

    public void setDatastr(String datastr){
        this.datastr = datastr;
    }
    public String getDatastr(){
        return this.datastr;
    }
    public void setDatenumberList(List<DatenumberList> datenumberList){
        this.datenumberList = datenumberList;
    }
    public List<DatenumberList> getDatenumberList(){
        return this.datenumberList;
    }
}
