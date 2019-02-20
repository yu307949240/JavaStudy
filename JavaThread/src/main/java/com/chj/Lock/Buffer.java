package com.chj.Lock;

/**
 * 当用 synchronized 中断对互斥锁的等待时，并不起作用，该线程依然会一直等待
 * 本例子将会一直等待
 */
public class Buffer {

    private Object lock;

    public Buffer() {
        lock = this;
    }

    public void write() {
        synchronized (lock) {
            long startTime = System.currentTimeMillis();
            System.out.println("开始往这个buff写入数据…");
            for (; ; )// 模拟要处理很长时间
            {
                if (System.currentTimeMillis()
                        - startTime > Integer.MAX_VALUE) {
                    break;
                }
            }
            System.out.println("终于写完了");
        }
    }

    public void read() {
        synchronized (lock) {
            System.out.println("从这个buff读数据");
        }
    }

    public static void main(String[] args) {
        Buffer buff = new Buffer();

        final Writer writer = new Writer(buff);
        final Reader reader = new Reader(buff);

        writer.start();
        reader.start();

        new Thread(new Runnable() {

            @Override
            public void run() {
                long start = System.currentTimeMillis();
                for (; ; ) {
                    //等5秒钟去中断读
                    if (System.currentTimeMillis()
                            - start > 5000) {
                        System.out.println("不等了，尝试中断");
                        reader.interrupt();  //尝试中断读线程
                        break;
                    }

                }

            }
        }).start();
        // 我们期待“读”这个线程能退出等待锁，可是事与愿违，一旦读这个线程发现自己得不到锁，
        // 就一直开始等待了，就算它等死，也得不到锁，因为写线程要21亿秒才能完成 T_T ，即使我们中断它，
        // 它都不来响应下，看来真的要等死了。这个时候，ReentrantLock给了一种机制让我们来响应中断，
        // 让“读”能伸能屈，勇敢放弃对这个锁的等待。我们来改写Buffer这个类，就叫BufferInterruptibly吧，可中断缓存。
    }
}

class Writer extends Thread {

    private Buffer buff;

    public Writer(Buffer buff) {
        this.buff = buff;
    }

    @Override
    public void run() {
        buff.write();
    }
}

class Reader extends Thread {

    private Buffer buff;

    public Reader(Buffer buff) {
        this.buff = buff;
    }

    @Override
    public void run() {

        buff.read();//这里估计会一直阻塞

        System.out.println("读结束");

    }
}
