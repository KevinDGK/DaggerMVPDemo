# DaggerMVPDemo
	内容主题：Dagger+MVP的基本使用
	内容介绍：本Demo主要讲解了Dagger注入框架的基本知识，以及在MVP框架中的使用。

## 简介
> 	内容主题：Dagger+MVP的基本使用
> 	当前版本：2.0
> 	内容介绍：本Demo主要讲解了Dagger注入框架的基本知识，以及在MVP框架中的使用
> 	官网：http://google.github.io/dagger/ 
> 	说明：下列Dagger2的主要内容翻译自官网最新的开发文档，  
		google的这个官方文档十分恶心，demo也很晦涩，所以部分内容来自博客，底部有链接，但是保证demo运行成功；
> 	版权声明：本文为原创文章，未经允许不得转载  
> 	博客地址：http://blog.csdn.net/kevindgk  
> 	GitHub地址：https://github.com/KevinDGK/DaggerMVPDemo

# 示例Demo
![](http://i.imgur.com/CZqboQG.gif)

# Dagger初步认识#
	Dagger是一个Java和Android都可以使用的完全静态的、编译时运行的依赖注入框架。早期由  Square开发，现在是由google维护的一个新的版本。
	Dagger2是一种依赖注入的框架，能够在编译时自动生成出一些代码，这些代码可以帮助对应的实例初始化，例如：一个容器类中装的是苹果，正常的写法是：

    	Public class Container{
    		Fruit f = new Apple(color,size);
    		...
    	}  

	但是该类面临一个问题，Container类中依赖了Apple的实现，耦合性较高，如果某一天需要修改Apple为Banana，那么你一定得改Container的代码。如果使用Dagger就会变成：  
  
    	Public class Container{
    		@Inject
    		Fruit f;	// 通过使用注解的方式，使用Dagger实现了Fruit类的自动注入
    	...
    	}  
	
	这样，Container的成员变量就自动初始化成Apple实例了，Container不用关心具体用哪个Fruit的实现，也不用关心到底用什么颜色多大的苹果。
	假如某一天要把苹果替换成香蕉，Container的代码是完全不需要改动的。从某种意义上说，Dagger2就是一个帮你写工厂代码的工具。当然Dagger2的功能比工厂模式更加强大。

#Dagger2 特点#
	依赖注入框架已经存在了好几年了，而且有了大量的配置和注入的API。  
	Dagger2是第一个implement the full stack with generated code.

#Dagger2 基础#
##基本结构视图##
Dagger2要实现一个完整的依赖注入，必不可少的元素有三种，Module，Component,Container。

**依赖(组件)		       注入器(构成)		     容器**  
![](http://i.imgur.com/gVqm9MH.png)  

- Module：依赖的提供者，所有需要被注入的元素的实例对象都是Module提供的，即所有注入的对象都是在这里new的；
- Container：可以注入依赖的容器，在容器中使用@Inject标注的元素(成员变量)都会被自动初始化。
- Component：有了依赖的提供者和可以注入依赖的容器，我们必须将依赖对象注入到容器中，这个过程由Component来执行。Component将Module中产生的依赖对象自动注入到Container中。

##配置##
- project的build.gradle添加  
	dependencies {  
	   ...  
	   classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'  
	}
- module的build.gradle添加  
	apply plugin: 'com.neenbedankt.android-apt' // 添加apt插件
	
	compile 'com.google.dagger:dagger:2.4'		// dagger
	apt 'com.google.dagger:dagger-compiler:2.4'	// dagger编译器
	//provided 'javax.annotation:jsr250-api:1.0' // Javax标注
	provided 'org.glassfish:javax.annotation:10.0-b28'  // Javax标注，添加android缺失的部分注解

##基本使用##
- @Inject：在需要注入依赖的地方使用，Dagger会自动创建这个类的实例注入到容器中。
- @Module ：Modules类里面的方法专门提供依赖，Dagger在构造类的实例的时候，就从modules里面去寻找它想要的依赖，modules的一个重要的特征就是他们可以自由组合在一起。
- @Component：注入器，可以说是@Inject和@Module的桥梁，作用就是链接这两个部分，它可以提供所有在Module中被定义了的类的实例，然后注入到使用@Inject修饰的元素。该过程是在编译时进行的，如果不满足条件会报错。 
- @Provides:在modules中，使用该注解提供依赖。
- @Named:Dagger2是根据Provide方法返回类型查找对应的依赖的，如果container需要注入相同类型的，但是内容不同(比如返回值同样是String，  
又或者同样是Fruit但是返回值不同，即多态)，这个时候就可以使用@Named进行区分。
- @Singleton有时候，我们不需要多次创建Component，可以使用一个Component，即单例模式，这个时候就可以使用@Singleton来修饰它，这样就会以单利的模式在生成的  Dagger********中保存；同样的，如果某个moudle也只需要使用同一个，那么也可以使用@Single来修饰provide的方法，同时也需要使用@Singleton修饰使用该moudle的Component。
- @Scope:自定义注解的作用域，通常可以定义表示范围为整个App或者Activity生命周期的@Scope注解。但是我们往往可以自己在Application层实现Component的单例类，  
  这个Demo中有，所有这部分暂时也可以不看。如果感兴趣，移步:[http://blog.csdn.net/duo2005duo/article/details/50696166](http://blog.csdn.net/duo2005duo/article/details/50696166)
- @Subcomponent:子Component，用于对原Component实现扩展。  
  如一个注入器不能满足你的需求，需要对他进行扩展，一种办法是使用Component(dependencies=××.classs)，  
  还有一种方法就是使用@Subcomponent扩展原有的注入器，只需要在父Component中添加返回子Component的方法即可。代码地址同上。
- Lazy和Provider都是在Container中用于修饰被注入的变量，Lazy表示延迟加载，Provider表示强制重新加载。
- Multibindings:多绑定，用于插件话的实现，应用场景较少，此处不做解释。


#Dagger+MVP#
##MVP回顾##
 ![](http://i.imgur.com/Wb19hIy.png)  
##本例中mvp的简单流程-点击按钮加载数据##  
 ![](http://i.imgur.com/Q36OFjs.png)  
##包结构##  
![](http://i.imgur.com/cUWvtHY.png)  
##流程##
1.	首先走一遍MVP流程：从第1~6步
2.	分析MVP的耦合性，然后代入Dagger的使用；
3.	走一遍Dagger的流程
4.	讲解Dagger的一些细节和规则
	- 为Provides方法添加参数  
	方式一：自动匹配使用module里的方法  
	方式二：如果module没有，自动使用参数的类来初始化实例
	- 为component添加多个Module  
	方式一：在Component的注解@Component(modules={A,B,C}) 添加多个modules；  
	方式二：module本身包含其他module，用于构建更高层次的module。  
	- DaggerPresenterComponent.builder().build()
	等价于DaggerPresenterComponent.creater();
	- 如果需要注入相同类型的实例，但是参数不同。  
	当有实例(对象)需要注入的时候，比如Fruit，Dagger2就会在Module中查找返回值类型为Fruit的方法，然后提供实例对象。所以说Dagger2是按照Provide方法返回类型查找对应的依赖。但是，当Container需要依赖两种不同的Fruit时，你就需要写两个@Provides方法，而且这两个@Provides方法都是返回Fruit类型，靠判别返回值的做法就行不通了，这就需要使用@Named来区分。

#Dagger+MVP+Retrofit+RxAndroid#
	（等待更新）

#Questions
1. 如果注入的变量爆空指针，点击进入生成的Dagger*******类中发现提供module的方法过时了，显示在项目中没有被使用。  
	可能的原因：在Component中的注入点，即inject()的参数错误。如果是在MainActivity中注入的，那么此处不要使用inject(Activity activity)，  
	必须使用inject(MainActivity acitvity)，否则的话，就会爆出空指针，因为如果写activity，Dagger会去Activity里面去找，然而并没有@Inject，所以会null，详细解释看代码。

2.	Dagger*******经常不出现。  
	如果写完Component和module，可以使用Build->Make Project；  
	如果已经生成了，那么就Rebuild Propject即可。

#本文引用的网址#
	官方网站：https://google.github.io/dagger/  
	博客：  
	http://blog.csdn.net/duo2005duo/article/details/50618171
	http://blog.csdn.net/duo2005duo/article/details/50696166
	http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0519/2892.html  
	Google维护的当前版本：Dagger2
#联系方式#
	如果文章或者Demo中有问题，请一定留言或者联系我：QQ-815852777