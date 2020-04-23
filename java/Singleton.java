package com.mvc.utils;

/**
 * @Description: 单例模式设计
 * @author: LuShao
 * @create: 2018-04-14 18:02
 **/
public enum Singleton {
	INSTANCE;
	public void fun1(){
		//do something...
	}
}
/*
public class Singleton {
	private static class SingletonHolder{
		private static final Singleton INSTANCE=new Singleton();
	}
	private Singleton(){}
	public static final Singleton getInstance(){
		return SingletonHolder.INSTANCE;
	}
}
*/
/*
public class Singleton {
	private static volatile Singleton singleton=null;
	private Singleton(){}
	public static Singleton getInstance(){
		if (singleton==null){
			synchronized (Singleton.class){
				if (singleton==null){
					singleton=new Singleton();
				}
			}
		}
		return singleton;
	}
}
*/
/*
public class Singleton {
	private static Singleton singleton=null;
	private Singleton(){}
	public static Singleton getInstance(){
		if (singleton==null){
			synchronized (Singleton.class){
				if (singleton==null){
					singleton=new Singleton();
				}
			}
		}
		return singleton;
	}
}
*/
/*
public class Singleton {
	private static Singleton singleton=null;
	private Singleton(){}
	public static Singleton getInstance(){
		synchronized (Singleton.class){
			if (singleton==null){
				singleton=new Singleton();
			}
			return singleton;
		}
	}
}
*/
/*
public class Singleton {
	private static Singleton singleton=null;
	private Singleton(){}
	public static Singleton getInstance(){
		if (singleton==null){
			singleton=new Singleton();
		}
		return singleton;
	}
}
*/
/*
public class Singleton {

	//通过使用private封装该实例，则需要get()实现对外界的开放
	private static Singleton instance=new Singleton();

	//添加static 将该方法变成类所有 通过类名访问
	public static Singleton getInstance(){
		return instance;
	}
}
*/
/*
public class Singleton {
	//使用private将构造方法私有化，以防外界通过该构造方法创建多个实例
	private Singleton(){}

	//由于不能使用构造方法创建实例，所以需要在类的内部创建该类的唯一实例
	//使用static修饰Singleton，在外界可以通过类名调用该实例  类名.成员名
	static Singleton singleton=new Singleton();
}
*/
