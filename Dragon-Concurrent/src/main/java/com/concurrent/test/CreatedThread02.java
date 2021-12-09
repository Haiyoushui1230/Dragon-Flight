package com.concurrent.test;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.CreatedThread02")
public class CreatedThread02 {
    public static void main(String[] args) {
       Runnable runnable = new Runnable() {
           @Override
           public void run() {
               log.debug("creat thread");
           }
       };

       new Thread(runnable,"thread01").start();
    }
}
