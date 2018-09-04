package com.kukan.server;

import com.kukan.server.handler.EchoServerHandler;
import com.utils.NetUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import com.registry.Registry;
import com.registry.ZookeeperRegistry;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 *
 * @author KuKan
 * @date 2018/2/24
 */
public class EchoServer {
    private final int port;
    private Registry registry;
    public EchoServer(int port, Registry registry) {
        this.port = port;
        this.registry = registry;
    }

    public static void main(String[] args) {

        new EchoServer(8080,new ZookeeperRegistry("47.93.40.16:2181")).start();
    }

    private void start() {
        final EchoServerHandler serverHandler = new EchoServerHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ch.pipeline().addLast(serverHandler);
                    }
                });

            ChannelFuture f = bootstrap.bind().sync();
            f.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("netty启动成功,开始注册逻辑");
                    System.out.println(EchoServer.this.port);
                    registry.register("/xiaomai/server",NetUtils.getLocalHost()+":"+port);
                }
            });
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
