package com.chj.Future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTest {

    public static void main(String[] args) {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
        List<FutureTask<Integer>> futureTaskList = new ArrayList<>();

        //扔进20个任务
        for (int i = 0; i < 20; i++) {
            FutureTask futureTask = new FutureTask(new Task(i));
            futureTaskList.add(futureTask);
            fixedThreadPool.submit(futureTask);
        }

        //获得任务结果
        for (FutureTask f : futureTaskList) {
            try {
                int i = (int) f.get();
                System.out.println("task " + i + " 完成工作");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        fixedThreadPool.shutdown();
    }

}
