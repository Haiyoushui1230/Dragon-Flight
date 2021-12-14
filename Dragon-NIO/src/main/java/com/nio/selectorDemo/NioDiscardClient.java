package com.nio.selectorDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


/**
 * 客户端的DiscardClient
 * 客户端首先建立到服务器的连接，发送一些简单的数据，然后直接关闭连接
 */
public class NioDiscardClient {
    private static final org.apache.log4j.Logger loger = org.apache.log4j.Logger.getLogger(NioDiscardClient.class);
    public static void startClient() throws IOException {

        InetSocketAddress address =new InetSocketAddress("127.0.0.1", 8080);

        // 1.获取通道
        SocketChannel socketChannel = SocketChannel.open(address);

        // 2.切换成非阻塞模式
        socketChannel.configureBlocking(false);

        //不断地自旋、等待连接完成，或者做一些其他的事情
        while (!socketChannel.finishConnect()) {
        }
        loger.info("客户端连接成功");

        // 3.分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hello world".getBytes());
        byteBuffer.flip();

        //发送到服务器
        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        socketChannel.close();
    }
    public static void main(String[] args) throws IOException {
        startClient();
    }
}
