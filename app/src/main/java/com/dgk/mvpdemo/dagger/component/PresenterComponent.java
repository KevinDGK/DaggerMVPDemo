package com.dgk.mvpdemo.dagger.component;

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

    void inject(MainActivity activity);    // 注入点
}
