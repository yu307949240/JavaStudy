package com.chj.Future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task implements Callable<Integer> {

    private int taskNum;

    public Task(int taskNum){
        this.taskNum = taskNum;
    }


    @Override
    public Integer call() throws Exception {
//        TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        TimeUnit.SECONDS.sleep(5);
        return taskNum;
    }
}
