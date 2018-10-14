package com.yyq.DesignPattern.creational.singleton;


/**
 * 单例模式-懒汉式，注重延迟加载，只有使用该类的时候才进行初始化。
 */
public class LazySingleton {
    private static LazySingleton lazySingleton = null;
    private LazySingleton(){
        if(lazySingleton != null){
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }
    public synchronized static LazySingleton getInstance(){
        if(lazySingleton == null){
            lazySingleton = new LazySingleton();
         }
        return lazySingleton;
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new T());
        Thread t2 = new Thread(new T());
        t1.start();
        t2.start();
        System.out.println("program is end");
    }
    static class T implements Runnable{

        @Override
        public void run() {
            LazySingleton lazySingleton = LazySingleton.getInstance();
            System.out.println(Thread.currentThread().getName()+" "+lazySingleton);
        }
    }
    /*private static LazySingleton lazySingleton = null;
    private static boolean flag = true;
    private LazySingleton(){
        if(flag){
            flag = false;
        }else{
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }
    public synchronized static LazySingleton getInstance(){
        if(lazySingleton == null){
            lazySingleton = new LazySingleton();
        }
        return lazySingleton;
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Class objectClass = LazySingleton.class;
        Constructor c = objectClass.getDeclaredConstructor();
        c.setAccessible(true);

        LazySingleton o1 = LazySingleton.getInstance();

        Field flag = o1.getClass().getDeclaredField("flag");
        flag.setAccessible(true);
        flag.set(o1,true);

        LazySingleton o2 = (LazySingleton) c.newInstance();
        System.out.println(o1);
        System.out.println(o2);
        System.out.println(o1 == o2);
    }*/

}
