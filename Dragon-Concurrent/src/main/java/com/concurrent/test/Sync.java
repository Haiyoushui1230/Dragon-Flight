package com.concurrent.test;

import lombok.Getter;
import lombok.Setter;


import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Sync")
@Getter
@Setter
public class Sync {

    private  String data;
    public static void main(String[] args) {

        new Sync();
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
