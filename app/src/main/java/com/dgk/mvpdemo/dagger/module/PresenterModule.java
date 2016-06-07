package com.dgk.mvpdemo.dagger.module;

import android.graphics.Color;

import com.dgk.mvpdemo.dagger.bean.Apple;
import com.dgk.mvpdemo.dagger.bean.AppleInfo;
import com.dgk.mvpdemo.dagger.bean.Banana;
import com.dgk.mvpdemo.dagger.bean.Fruit;
import com.dgk.mvpdemo.dagger.bean.Orange;
import com.dgk.mvpdemo.presenter.IPresenter;
import com.dgk.mvpdemo.presenter.PresenterA;
import com.dgk.mvpdemo.presenter.PresenterB;

import javax.inject.Named;
import javax.inject.Singleton;

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
     * 2.同时在Component类标明@Singleton，它也是单例的，被保存在DaggerPresenterComponent中
     */
    @Singleton
    @Provides
    public PresenterA providePresenterA(){
        return new PresenterA();
    }



    //  Provides添加参数方法一：输入参数自动使用到provideColor()的返回值Color.RED
    @Named("Apple")
    @Provides
    public Fruit provideFruit(Integer color){
        return new Apple(color);
    }

    @Provides
    public Integer provideColor(){      // 使用int也可以
        return Color.RED;
    }

    //  Provides添加参数方法二：如果在module中找不到@Provide方法提供的参数对象，就会找到参数对象的类，
    //  然后调用@inject修饰的构造方法生成相应的实例对象。
    @Provides
    public Apple provideApple(AppleInfo appleInfo){
        return new Apple(appleInfo);
    }

    @Named("Orange")
    @Provides
    public Fruit provideOrange(Integer color){
        return new Orange(color);
    }

    @Named("Banana")
    @Provides
    public Fruit provideBanana(Integer color){
        return new Banana(color);
    }
}
