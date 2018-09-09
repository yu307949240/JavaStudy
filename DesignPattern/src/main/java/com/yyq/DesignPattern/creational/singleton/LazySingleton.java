package com.yyq.DesignPattern.creational.singleton;

/**
 * 单例模式-懒汉式，注重延迟加载，只有使用该类的时候才进行初始化。
 */
public class LazySingleton {
    private static LazySingleton lazySingleton = null;
    private LazySingleton(){

    }

    public static LazySingleton getInstance(){
        if(lazySingleton == null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }
}
