package com.technology.yuyi.Presenter;

import com.technology.yuyi.Model.MS_drugStateModel;
import com.technology.yuyi.bean.BeanDrugStates;

/**
 * Created by wanyu on 2017/8/24.
 */

public class MS_drugStatePresenter implements MS_drugStateModel.IListener{
    MS_drugStateModel model;
    IMS_drugStateView iView;
    public MS_drugStatePresenter(IMS_drugStateView iView){
        model=new MS_drugStateModel();
        this.iView=iView;
    }
    //获取我的所有药品状态
    public void getMyDrugStates(){
        model.getDrugStates(this);
    }

    @Override
    public void onGetMyDrugStates(BeanDrugStates bean) {
        iView.onGetMyDrugStates(bean);
    }

    @Override
    public void onGetMyDrugStatesError(String msg) {
        iView.onGetMyDrugStatesError(msg);
    }

    public interface IMS_drugStateView{
        void onGetMyDrugStates(BeanDrugStates bean);//获取我的所有药品状态
        void onGetMyDrugStatesError(String msg);//获取我的药品状态失败
    }
}
