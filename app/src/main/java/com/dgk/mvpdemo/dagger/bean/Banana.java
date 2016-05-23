package com.dgk.mvpdemo.dagger.bean;


/**
 * Created by Kevin on 2016/5/23.
 *
 */
public class Banana extends Fruit{

    public int color;
    public String type;

    public Banana(){

    }

    public Banana(int color){
        this.color = color;
    }

    public Banana(AppleInfo appleInfo){
        this.type = appleInfo.type;
    }
}
