package com.concurrent.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


@Slf4j(topic = "c.CreatedThread043")
public class CreatedThread04 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask futureTask = new FutureTask(() ->{
           log.debug("futureTask exec");
           return 100;
        });


        //
        FutureTask futureTask1 = new FutureTask<>(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                log.debug("futureTask1 exec");
                return 99;
            }
        });


        new Thread(futureTask,"thread01").start();
        new Thread(futureTask1,"thread02").start();


        Integer result = (Integer) futureTask.get();
        Integer result1 = (Integer) futureTask1.get();

        log.debug("result is :"+result);
        log.debug("result1 is :"+result1);
    }
}