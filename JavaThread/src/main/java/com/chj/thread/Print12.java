package com.chj.thread;

/**
 * 两个线程交替打印1212
 * notify()    -- 唤醒在此对象监视器上等待的单个线程。
 * wait()      -- 让当前线程处于“等待(阻塞)状态”，“直到其他线程调用此对象的 notify() 方法或 notifyAll() 方法”，当前线程被唤醒(进入“就绪状态”)。
 */
public class Print12 implements Runnable{

    private Object o;
    private int flag;

    public Print12(Object o,int flag){
        this.o = o;
        this.flag = flag;
    }

    @Override
    public void run() {
        while (true){
            synchronized (o){
                try {
                    o.notify();
                    System.out.println(flag);
                    Thread.sleep(1000);
                    o.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String []args){
        final Object o = new Object();

        try {
            new Thread(new Print12(o,1)).start();
            Thread.sleep(1000);
            new Thread(new Print12(o,2)).start();
        }catch (Exception e){

        }
    }

}
