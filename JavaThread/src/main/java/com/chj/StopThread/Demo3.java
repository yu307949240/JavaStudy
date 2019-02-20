package com.chj.StopThread;

/**
 * 终止线程通用方法，设置标志位。
 * Created by chenhaojie on 2017/10/17.
 */
class MyThread3 extends Thread {

    private volatile boolean flag= true;
    public void stopTask() {
        flag = false;
    }

    public MyThread3(String name) {
        super(name);
    }

    @Override
    public void run() {
        synchronized(this) {
            try {
                int i=0;
                while (flag) {
                    Thread.sleep(100); // 休眠100ms
                    i++;
                    System.out.println(Thread.currentThread().getName()+" ("+this.getState()+") loop " + i);
                }
            } catch (InterruptedException ie) {
                System.out.println(Thread.currentThread().getName() +" ("+this.getState()+") catch InterruptedException.");
            }
        }
    }
}

public class Demo3 {

    public static void main(String[] args) {
        try {
            MyThread3 t1 = new MyThread3("t1");  // 新建“线程t1”
            System.out.println(t1.getName() +" ("+t1.getState()+") is new.");

            t1.start();                      // 启动“线程t1”
            System.out.println(t1.getName() +" ("+t1.getState()+") is started.");

            // 主线程休眠300ms，然后主线程给t1发“中断”指令。
            Thread.sleep(300);
            t1.stopTask();
            System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted.");

            // 主线程休眠300ms，然后查看t1的状态。
            Thread.sleep(300);
            System.out.println(t1.getName() +" ("+t1.getState()+") is interrupted now.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
