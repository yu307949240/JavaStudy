package com.chj.thread;

/**
 * 三个线程交替打印123，本例采用标志位的方法实现。还可以使用两个object对象锁方式。
 * Created by chenhaojie on 2018/03/21.
 */
public class PrintABC implements Runnable{

    public Object o;
    public static volatile int flag;
    public int originFlag;

    public PrintABC(Object o,int originFlag){
        this.o = o;
        this.originFlag = originFlag;
    }

    @Override
    public void run() {
        while (true){
            synchronized (o){
                try {
                    o.notifyAll();
                    if(originFlag == flag){
                        System.out.println(flag);
                        flag = getNextflag(flag);
                        Thread.sleep(1000);
                    }
                    o.wait();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public int getNextflag(int flag){
        flag = flag + 1 > 3 ? 1 : flag + 1;
        return flag;
    }

    public static void main(String[]args){
        Object o = new Object();
        PrintABC.flag = 1;
        try {
            new Thread(new PrintABC(o,1)).start();
            Thread.sleep(1000);
            new Thread(new PrintABC(o,2)).start();
            Thread.sleep(1000);
            new Thread(new PrintABC(o,3)).start();
        }catch (Exception e){

        }

    }
}
