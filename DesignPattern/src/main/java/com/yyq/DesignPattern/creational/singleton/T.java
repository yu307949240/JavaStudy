package com.yyq.DesignPattern.creational.singleton;

public class T implements Runnable {
    @Override
    public void run() {
       /* LazySingleton lazySingleton = LazySingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+"  "+lazySingleton);*/
       //双重检查锁机制
//       LazyDoubleCheckSingleton lazyDoubleCheckSingleton = LazyDoubleCheckSingleton.getInstance();
//        System.out.println(Thread.currentThread().getName()+"  "+lazyDoubleCheckSingleton);
       //静态内部类
       /* StaticInnerClassSingleton staticInnerClassSingleton = StaticInnerClassSingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+"  "+staticInnerClassSingleton);*/
       //容器单例
//        ContainerSingleton.putInstance("object",new Object());
////
////        Object instance = ContainerSingleton.getInstance("object");
////        System.out.println(Thread.currentThread().getName()+instance);
        ThreadLocalInstance instance = ThreadLocalInstance.getInstance();
        System.out.println(Thread.currentThread().getName()+" "+instance);
    }
}
