package com.dgk.mvpdemo.dagger.module;

import com.dgk.mvpdemo.presenter.IPresenter;
import com.dgk.mvpdemo.presenter.PresenterA;
import com.dgk.mvpdemo.presenter.PresenterB;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Kevin on 2016/5/23.
 * Moudle - 依赖提供者
 * 作用：Module其实就是一个依赖的制造工厂，内部包含提供各种依赖的方法。
 */
@Module
public class PresenterModule {

    public PresenterModule (){
    }

    /** Provides-对外提供依赖对象 */
    @Provides
    public IPresenter provideIPresenter(){
        return new PresenterA();
    }

    /**
     * @Singleton   单例
     * 创建某些对象有时候是耗时浪费资源或者没有完全必要的，这时候Component没有必要重复地
     * 使用Module来创建这些对象,我们可以使用@Singleton来进行修饰。
     * 只要创建过被Singleton修饰过的对象，那么就会被存储到component的实例中，下次注入的时候使用，即单例模式。
     * 使用方法：
     * 1.在Module对应的Provides方法标明@Singleton
     * 2.同时在Component类标明@Singleton
     */
    @Provides
    @Singleton
    public PresenterA providePresenterA(){
        return new PresenterA();
    }

}
