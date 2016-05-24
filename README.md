# DaggerMVPDemo
	内容主题：Dagger+MVP的基本使用
	内容介绍：本Demo主要讲解了Dagger注入框架的基本知识，以及在MVP框架中的使用。
#Dagger初步认识#
	Dagger是一个Java和Android都可以使用的完全静态的、编译时运行的依赖注入框架。早期由Square开发，现在是由google维护的一个新的版本。
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
	
	这样，Container的成员变量就自动初始化成Apple实例了，Container不用关心具体用哪个Fruit的实现，也不用关心到底用什么颜色多大的苹果。假如某一天要把苹果替换成香蕉，Container的代码是完全不需要改动的。从某种意义上说，Dagger2就是一个帮你写工厂代码的工具。当然Dagger2的功能比工厂模式更加强大。
#Dagger基础#
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
- @Scope:自定义注解的作用域。
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

#本文引用的网址#
	官方网站：https://google.github.io/dagger/  
	博客：  
	http://blog.csdn.net/duo2005duo/article/details/50618171
	http://blog.csdn.net/duo2005duo/article/details/50696166
	http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0519/2892.html  
	Google维护的当前版本：Dagger2
#联系方式#
	如果文章或者Demo中有问题，请一定留言或者联系我：QQ-815852777