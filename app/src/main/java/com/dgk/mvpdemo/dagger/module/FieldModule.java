package com.dgk.mvpdemo.dagger.module;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kevin on 2016/5/23.
 * Moudle - 依赖提供者
 * 作用：Module其实就是一个依赖的制造工厂，内部包含提供各种依赖的方法。
 *
 * 本身添加其他module：@Module(includes={ModuleA.class,ModuleB.class,ModuleC.class})
 */
@Module
public class FieldModule {

    public FieldModule(){
    }

    @Named("name")
    @Provides
    public String provideName(){
        return "DaiGaoKai";
    }

    @Named("nickname")
    @Provides
    public String provideNickname(){
        return "Kevin";
    }

    @Provides
    public String providePassword(){
        return "201027209";
    }
}
