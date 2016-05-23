package com.dgk.mvpdemo.dagger.bean;


/**
 * Created by Kevin on 2016/5/23.
 *
 */
public class Orange extends Fruit{

    public int color;
    public String type;

    public Orange(){

    }

    public Orange(int color){
        this.color = color;
    }

    public Orange(AppleInfo appleInfo){
        this.type = appleInfo.type;
    }
}
