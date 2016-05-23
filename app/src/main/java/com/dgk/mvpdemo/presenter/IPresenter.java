package com.dgk.mvpdemo.presenter;

import com.dgk.mvpdemo.view.IView;

/**
 * @author dgk
 * @time 2016/5/22 0022 下午 10:38
 * @des ${TODO}
 */
public interface IPresenter {

    void onCreate(IView view);        // 初始化数据

    void performOnClick();  // 点击事件
}
