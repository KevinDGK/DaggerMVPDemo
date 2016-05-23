package com.dgk.mvpdemo.dagger.bean;


/**
 * Created by Kevin on 2016/5/23.
 *
 */
public class Apple extends Fruit{

    public int color;
    public String type;

    public Apple(){

    }

    public Apple(int color){
        this.color = color;
    }

    public Apple(AppleInfo appleInfo){
        this.type = appleInfo.type;
    }
}
