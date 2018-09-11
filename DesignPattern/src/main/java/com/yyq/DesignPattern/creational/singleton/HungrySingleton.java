package com.yyq.DesignPattern.creational.singleton;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 单例模式-恶汉式
 */
public class HungrySingleton implements Serializable {
    private final static HungrySingleton hungrySingleton;//声明为final的静态变量，必须在类加载完成时完成赋值
    static{
        hungrySingleton = new HungrySingleton();
    }
    private HungrySingleton(){
        if(hungrySingleton != null){
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }
    public static HungrySingleton getInstance(){
        return hungrySingleton;
    }

    private Object readResolve(){
        return hungrySingleton;
    }
}
