package com.yyq.DesignPattern.creational.singleton;




import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例模式-容器单例，这种方法适合方法初始化的时候，把多个单例对象放到singletonMap里面
 */
public class ContainerSingleton {
    private static Map<String,Object> singletonMap = new HashMap<>();

    private ContainerSingleton(){}

    public static void putInstance(String key,Object instance){
        if(StringUtils.isNotBlank(key) && instance != null){
            if(!singletonMap.containsKey(key)){
                singletonMap.put(key,instance);
            }
        }
    }

    public static Object getInstance(String key){
        return singletonMap.get(key);
    }
}
