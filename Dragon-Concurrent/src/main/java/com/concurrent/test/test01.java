package com.concurrent.test;

import static java.lang.Thread.sleep;

public class test01 {
    static volatile boolean run = true;
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(()->{
            while(run){
                // ....
            }
        });
        t.start();
        System.out.println("停止T");
        sleep(10);
        run = false; // 线程t不会如预想的停下来
    }
}
