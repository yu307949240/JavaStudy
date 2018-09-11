package com.yyq.DesignPattern.creational.singleton;

/**
 * 静态内部类-基于类初始化的延迟加载解决方案及原理解析
 */
public class StaticInnerClassSingleton {
    /**
     * 静态内部类在于哪个线程拿到类的初始化锁，
     */
    private static class InnerClass{
        private static StaticInnerClassSingleton staticInnerClassSingleton = new StaticInnerClassSingleton();
    }
    public static StaticInnerClassSingleton getInstance(){
        return InnerClass.staticInnerClassSingleton;
    }

    private StaticInnerClassSingleton(){
        //单例模式-反射攻击解决方案
        if(InnerClass.staticInnerClassSingleton != null){
            throw  new RuntimeException("单例构造器禁止反射调用");
        }
    }

}
