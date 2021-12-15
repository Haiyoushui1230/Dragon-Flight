package com.netty.simpleDemo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

    public static void main(String[] args) throws InterruptedException {

        // 客户端需要一个事件循环组
        NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();

        try {
            // 创建客户端启动对象
            // 注意服务端有server
            Bootstrap bootstrap = new Bootstrap();

            // 设置相关参数
            bootstrap.group(nioEventLoopGroup) // 设置线程组
                    .channel(NioSocketChannel.class) // 设置客户端通道的实现
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new NettyClientHandler()); // 加入自己的处理器
                        }
                    });

            System.out.println("..............Client is Ok!");

            // 启动客户端连接服务器
            // 关于 ChannelFuture 要分析，涉及到netty的异步模型
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();

            // 关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            nioEventLoopGroup.shutdownGracefully();
        }
    }
}
