package com.dgk.mvpdemo.dagger.component;

import android.app.Activity;

import com.dgk.mvpdemo.dagger.module.PresenterModule;
import com.dgk.mvpdemo.view.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Kevin on 2016/5/23.
 * 注入器：连接实例消费者(inject)和实例的提供者(module),
 * 通过创建该接口的实例生成注入器，然后通过注入器完成依赖的注入：
 * 过程：
 *      1.创建注入器的构造器builder
 *      2.提供依赖的模型modules
 *      3.使用构造器创建注入器的实例；
 *    然后就可以通过使用注入器的实例，在依赖注入(@inject)的地方完成注入。
 * 注意：此处可以添加多个module，Component会依次在module中查找要注入的实例，
 *      module不允许有重复的方法。
 *      @Component(modules={ModuleA.class,ModuleB.class,ModuleC.class})
 */
@Singleton
@Component(modules = {PresenterModule.class})
public interface PresenterComponent {

    /**
     * 注入点
     * @param activity  表示需要使用DaggerPresenterComponent.create().inject(this);注入的地方，
     *                  注意，此处一定不要使用Activity，需要使用MainActivity，否则的话会报空指针异常。
     *                  因为这里的注入点是什么，就会到该类里面去找。如果写Activity，就会到Activity里面去找，
     *                  而Activity中并没有@inject，即没有需要注入的地方，所以在生成的DaggerPresenterComponent
     *                  中，方法就不会被调用。
     */
    void inject(MainActivity activity);    // 注入点
}
