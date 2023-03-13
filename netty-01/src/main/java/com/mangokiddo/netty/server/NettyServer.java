package com.mangokiddo.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @description:
 * @author: mango
 * @time: 2023/3/10 18:30
 */
public class NettyServer {

    public static void main(String[] args) {


        NioEventLoopGroup worker1 = new NioEventLoopGroup();
        NioEventLoopGroup worker2 = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(worker1, worker2)
                    .channel(NioServerSocketChannel.class)    //非阻塞模式
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childHandler(new MyServerHandler());


            ChannelFuture sync = serverBootstrap.bind(8001).sync();
            System.out.println("mangokiddo-demo-netty server start done.");
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();


        } finally {
            worker1.shutdownGracefully();
            worker2.shutdownGracefully();

        }
    }
}
