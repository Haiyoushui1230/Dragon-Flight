package com.concurrent.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.CreatedThread01")
public class CreatedThread01 {
    public static void main(String[] args) {
        new Thread(()->{
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("sleep 3 seconds");
        },"thread01").start();

        log.debug("Async ....");

    }
}
