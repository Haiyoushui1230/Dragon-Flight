package com.nio.bufferDemo;

import org.slf4j.Logger;

import java.nio.IntBuffer;

public class UseBuffer {

    private static final org.apache.log4j.Logger loger = org.apache.log4j.Logger.getLogger(UseBuffer.class);
    static IntBuffer intBuffer = null;

    public static void allocatTest(){
        intBuffer = IntBuffer.allocate(20);
        loger.info("------------after allocate------------------");

        loger.info("position = "+intBuffer.position());
        loger.info("capacity = "+intBuffer.capacity());
        loger.info("limit = "+intBuffer.limit());
    }



    public static void putTest(){
        for (int i = 0; i < 5; i++) {
            intBuffer.put(i);
        }
        loger.info("------------after put------------------");

        loger.info("position = "+intBuffer.position());
        loger.info("capacity = "+intBuffer.capacity());
        loger.info("limit = "+intBuffer.limit());
    }

    public static void flipTest(){
        intBuffer.flip();
        loger.info("------------after flip------------------");

        loger.info("position = "+intBuffer.position());
        loger.info("capacity = "+intBuffer.capacity());
        loger.info("limit = "+intBuffer.limit());
    }


    public static void getTest(){

        loger.info("------------after get 2 int------------------");
        for (int i = 0; i < 2; i++) {
            int j = intBuffer.get();
            loger.info("j = "+j);
        }

        loger.info("position = "+intBuffer.position());
        loger.info("capacity = "+intBuffer.capacity());
        loger.info("limit = "+intBuffer.limit());

        loger.info("------------after get 3 int------------------");
        //再度3个
        for (int i = 0; i < 3; i++) {
            int j = intBuffer.get();
            loger.info("j = "+j);
        }

        loger.info("position = "+intBuffer.position());
        loger.info("capacity = "+intBuffer.capacity());
        loger.info("limit = "+intBuffer.limit());
    }


    public static void rewindTest(){
        intBuffer.rewind();
        loger.info("------------after rewind------------------");

        loger.info("position = "+intBuffer.position());
        loger.info("capacity = "+intBuffer.capacity());
        loger.info("limit = "+intBuffer.limit());
    }



    public static void reRead(){


        loger.info("------------after reread------------------");
        for (int i = 0; i < 5; i++) {
            if(i==2){
                intBuffer.mark();
            }
            int j = intBuffer.get();
            loger.info("j = "+j);
        }
        loger.info("position = "+intBuffer.position());
        loger.info("capacity = "+intBuffer.capacity());
        loger.info("limit = "+intBuffer.limit());
    }



    public static void afterReset(){

        loger.info("------------after reset------------------");
        intBuffer.reset();
        loger.info("position = "+intBuffer.position());
        loger.info("capacity = "+intBuffer.capacity());
        loger.info("limit = "+intBuffer.limit());

        for (int i = 2; i < 5; i++) {
            int j = intBuffer.get();
            loger.info("j = "+j);
        }

    }

    public static void clearDemo(){
        loger.info("------------after clear------------------");
        intBuffer.clear();
        loger.info("position = "+intBuffer.position());
        loger.info("capacity = "+intBuffer.capacity());
        loger.info("limit = "+intBuffer.limit());

    }










    public static void main(String[] args) {

        UseBuffer.allocatTest();
        UseBuffer.putTest();
        UseBuffer.flipTest();
        UseBuffer.getTest();
        UseBuffer.rewindTest();
        UseBuffer.reRead();
        UseBuffer.afterReset();
        UseBuffer.clearDemo();


    }
}
