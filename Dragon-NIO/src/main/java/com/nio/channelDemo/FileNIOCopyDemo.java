package com.nio.channelDemo;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNIOCopyDemo {
    private static final org.apache.log4j.Logger loger = org.apache.log4j.Logger.getLogger(FileNIOCopyDemo.class);
    public static void main(String[] args) {

        nioCopyFile("D:\\log_step01_test.txt","D:\\out.txt");
    }

    public static void nioCopyFile(String srcPath, String destPath){
        File srcFile = new File(srcPath);
        File destFile = new File(destPath);

        try {
            if(!destFile.exists()){
                destFile.createNewFile();
            }

            long startTime = System.currentTimeMillis();
            FileInputStream fis = null;
            FileOutputStream fos = null;
            FileChannel inChannel = null;
            FileChannel outchannel = null;

            try {
                fis = new FileInputStream(srcFile);
                fos = new FileOutputStream(destFile);
                inChannel = fis.getChannel();
                outchannel = fos.getChannel();

                int length = -1;
                ByteBuffer buf = ByteBuffer.allocate(1024);
                //从输入通道读取到buf
                while ((length = inChannel.read(buf)) != -1) {
                    loger.info("读取的字节数为 length = "+length);
                    //第一次切换：翻转buf，变成读取模式
                    buf.flip();
                    int outlength = 0;
                    //将buf写入到输出的通道
                    while ((outlength = outchannel.write(buf)) != 0) {
                        System.out.println("写入的字节数：" + outlength);
                    }
                    //第二次切换：清除buf，变成写入模式
                    buf.clear();
                }
                //强制刷新到磁盘
                outchannel.force(true);
            }finally {
                outchannel.close();
                fos.close();
                inChannel.close();
                fis.close();
            }


            long endTime = System.currentTimeMillis();
            loger.info("base复制毫秒数：" + (endTime - startTime));

        }catch (Exception e){

        }finally {

        }

    }
}
