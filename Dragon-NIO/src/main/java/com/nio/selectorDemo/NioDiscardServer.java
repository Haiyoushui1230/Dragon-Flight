package com.nio.selectorDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Discard服务端
 * Discard协议是这个世界上最简单的协议。它是一个会丢弃，从客户端接收到的所有数据，并没有任何响应的协议。
 * 想要实现DISCARD协议，我们只需要做一件事情，那就是忽略所有接收到的数据。
 *
 */
public class NioDiscardServer {
    private static final org.apache.log4j.Logger loger = org.apache.log4j.Logger.getLogger(NioDiscardServer.class);

    public static void startServer() throws IOException {
        //1、获取选择器
        Selector selector = Selector.open();

        //2、获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //3、设置通道为非阻塞
        serverSocketChannel.configureBlocking(false);

        /* 4、绑定连接 */
        serverSocketChannel.bind(new InetSocketAddress(8080));

        loger.info("服务器启动成功");

        //5、将通道注册的“接收新连接”io事件，注册到选择器上
        SelectionKey register = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

//        register.attach()


        // 6.轮询感兴趣的IO就绪事件（选择键集合）
        //todo selector.select() 没有事件发生会阻塞
        while (selector.select()>0){

            //7、获取选择键集合
            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();

            while (selectedKeys.hasNext()){

                //8、获取单个的选择键，并处理
                SelectionKey selectionKey = selectedKeys.next();

                //9、判断key具体是什么事件
                if(selectionKey.isAcceptable()){

                    //10、若选择键的io事件是“就绪连接事件”，就读取客户端连接
                    //todo socketChannel 用来与客户端通信
                    SocketChannel socketChannel = serverSocketChannel.accept();

                    //11、切换为非阻塞模式
                    socketChannel.configureBlocking(false);

                    //12、将该新连接的通道的可读事件，注册到选择器上
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if(selectionKey.isReadable()){

                    //13、若选择的io事件是“可读”事件，读取数据
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();

                    // 14.读取数据，然后丢弃
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int length = 0;
                    while ((length = socketChannel.read(byteBuffer)) >0) {
                        byteBuffer.flip();
                        loger.info(new String(byteBuffer.array(), 0, length));
                        byteBuffer.clear();
                    }
                    socketChannel.close();
                }

                //15、移除选择键
                selectedKeys.remove();
            }
        }

        // 16.关闭连接
        serverSocketChannel.close();
    }


    public static void main(String[] args) throws IOException {
        startServer();
    }























}
