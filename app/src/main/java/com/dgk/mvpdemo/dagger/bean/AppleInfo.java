package com.dgk.mvpdemo.dagger.bean;

import javax.inject.Inject;

/**
 * Created by Kevin on 2016/5/23.
 *
 */
public class AppleInfo {

    String type;

    /**
     * 如果该构造方法被@Inject标注，那么就可以被component在使用module初始化实例的时候使用
     */
    @Inject
    public AppleInfo() {

        type = "HFS";
    }
}
