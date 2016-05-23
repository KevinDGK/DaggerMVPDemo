package com.dgk.mvpdemo;

import android.app.Application;

import com.dgk.mvpdemo.dagger.component.DaggerPresenterComponent;
import com.dgk.mvpdemo.dagger.component.PresenterComponent;
import com.dgk.mvpdemo.dagger.module.PresenterModule;


/**
 * Created by Kevin on 2016/5/23.
 *
 */
public class MyApplication extends Application {

    private static PresenterComponent presenterComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public PresenterComponent getPresenterComponent() {

        /*
            1. 在Application层对注入器进行初始化
              DaggerAppComponent是dagger2编译阶段生成的类，用于生成注入器Component的实例，如果没有的话，ReBuild一下
                -builder()  创建构造器
                -appModule()    传入本次生成依赖需要的模型
                -build()    创建Component的实例
         */
        if (presenterComponent == null) {
            presenterComponent = DaggerPresenterComponent.builder()
                    .presenterModule(new PresenterModule())
                    .build();
            //presenterComponent = DaggerPresenterComponent.create();   // 作用同上
        }
        return presenterComponent;
    }
}
