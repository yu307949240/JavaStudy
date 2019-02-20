package com.chj.StopThread;

/**
 * 终止一个阻塞状态的线程。
 * 直接调用interrupt()方法即可，通过抛出InterruptedException退出。
 * <a href="http://www.cnblogs.com/skywang12345/p/3479949.html">终止线程</a>
 *
 * Created by chenhaojie on 2017/10/17.
 */
class MyThread2 extends Thread {

    public MyThread2(String name) {
        super(name);
    }

    @Override
    public void run() {
        int i = 0;
        try {
            while (!isInterrupted()) {
                Thread.sleep(100); // 休眠100ms
                i++;
                System.out.println(Thread.currentThread().getName() + " (" + this.getState() + ") loop " + i);
            }
        } catch (InterruptedException ie) {
            System.out.println(Thread.currentThread().getName() + " (" + this.getState() + ") catch InterruptedException.");
        }
    }
}

public class Demo2 {

    public static void main(String[] args) {
        try {
            Thread t1 = new MyThread2("t1");  // 新建“线程t1”
            System.out.println(t1.getName() + " (" + t1.getState() + ") is new.");

            t1.start();                      // 启动“线程t1”
            System.out.println(t1.getName() + " (" + t1.getState() + ") is started.");

            // 主线程休眠300ms，然后主线程给t1发“中断”指令。
            Thread.sleep(300);
            t1.interrupt();
            System.out.println(t1.getName() + " (" + t1.getState() + ") is interrupted.");

            // 主线程休眠300ms，然后查看t1的状态。
            Thread.sleep(300);
            System.out.println(t1.getName() + " (" + t1.getState() + ") is interrupted now.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
