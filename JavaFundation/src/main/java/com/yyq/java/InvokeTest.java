package com.yyq.java;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Method.invoke()用法
 * @author yyq
 * @since 18/09/20
 */
public class InvokeTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class mCls = methodClass.class;
        Object obj = mCls.newInstance();
        Method method = mCls.getMethod("add",int.class,int.class);
        Object res = method.invoke(obj,1,4);
        System.out.println(res);
    }
}

class methodClass{
    public int add(int a,int b){
        return a+b;
    }
}
