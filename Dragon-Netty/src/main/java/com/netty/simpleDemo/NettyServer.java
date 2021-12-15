package com.netty.simpleDemo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        //创建bossGroup workGroup
        //说明
        //1、创建两个线程组 bossGroup workGroup
        //2、bossGroup只能处理连接请求，真正的和客户端业务处理，，会交给workGroup完成
        //3、两个都是无限循环
        //4、bossGroup和workGroup 含有子线程（NioEventLoop）的个数，默认实际CPU核数*2
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {

            //创建服务器的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            //使用链式编程设置
            bootstrap.group(bossGroup,workGroup) //设置两个线程组
                    .channel(NioServerSocketChannel.class) //使用NioServerSocketChannel 作为通道服务器的链接实现
                    .option(ChannelOption.SO_BACKLOG,128) //设置线程队列得到的链接个数
                    .childOption(ChannelOption.SO_KEEPALIVE,true) //设置保持活动链接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道测试对象，

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //给pipeline设置处理器
                            socketChannel.pipeline().addLast(new NettyServerHandler());

                        }
                    });

            System.out.println("...............Server is ready！");

            // 6、绑定端口，并同步，生成一个 ChannelFuture 对象
            // 7、启动服务器并绑定端口
            ChannelFuture cf = bootstrap.bind(6668).sync();

            // 8、对关闭通道进行监听
            cf.channel().closeFuture().sync();

        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();

        }

    }
}
