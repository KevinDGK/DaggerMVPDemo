package com.dgk.mvpdemo.view;

/**
 * @author dgk
 * @time 2016/5/22 0022 下午 10:36
 * @des view的根接口，所有的view都要实现这个接口，定义一些通用的数据操作
 */
public interface IView {

    void setData(String data);  // 设置数据，显示到界面

    void showDialog();          // 显示下载进度对话框

    void setDialogProgress(Integer value);

    void cancelDialog();
}
