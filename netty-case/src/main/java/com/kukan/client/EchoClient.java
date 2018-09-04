package com.kukan.client;

import com.kukan.client.handler.EchoClientHandler;
import com.kukan.listener.Listener;
import com.registry.Registry;
import com.registry.ZookeeperRegistry;
import com.utils.NetUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.I0Itec.zkclient.ZkClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Random;

/**
 * @author KuKan
 * @date 2018/2/24
 */
public class EchoClient {

    private Registry registry;

    public EchoClient(Registry registry) {
        this.registry = registry;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        new EchoClient(new ZookeeperRegistry("47.93.40.16:2181")).start();
    }

    public void  start() throws InterruptedException, IOException {

        ZkClient zkClient = new ZkClient("47.93.40.16:2181");
        zkClient.subscribeChildChanges("/xiaomai",new Listener());
        List<String> children = zkClient.getChildren("/xiaomai/server");
        if (children == null || children.isEmpty()){
            throw new RuntimeException("无对应服务");
        }
        int randomServer =  new Random().nextInt(children.size());
        String[] serverHostPort = children.get(randomServer).split(":");

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(serverHostPort[0],Integer.parseInt(serverHostPort[1])))
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect().sync();
            future.addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    System.out.println("客户端启动成功");
                    registry.register("/xiaomai/client", NetUtils.getLocalHost()+Math.random());
                }
            });
            InputStream in = System.in;
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while (true){
                String command = reader.readLine();
                if ("q".equals(command)){
                    break;
                }
            }
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }


    }
}
