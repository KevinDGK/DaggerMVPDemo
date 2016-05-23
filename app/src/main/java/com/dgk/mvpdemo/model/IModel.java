package com.dgk.mvpdemo.model;

/**
 * @author dgk
 * @time 2016/5/22 0022 下午 10:51
 * @des ${TODO}
 */
public interface IModel {

//    void getDataFromNet(ICallBack callBack);

    void getDataFromNet();      // 从服务器或者数据库加载数据，此处仅仅模拟，所以没有返回值

//    interface ICallBack{
//        void onResult (String data);
//
//        void showDialog();
//
//        void setDialogProgress(Integer value);
//
//        void cancelDialog();
//    }

}
