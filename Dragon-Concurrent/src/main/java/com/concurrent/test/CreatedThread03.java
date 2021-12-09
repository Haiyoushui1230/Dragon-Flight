package com.concurrent.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.CreatedThread03")
public class CreatedThread03 {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            log.debug("create thread");
        };

        new Thread(runnable,"thread01").start();
    }
}