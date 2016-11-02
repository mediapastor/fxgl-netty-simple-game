package com.tingshulien.game.simplegame.client;

import com.almasb.fxgl.entity.GameEntity;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class Client {

    private int port;
    private String host;
    private GameEntity player;

    private EventLoopGroup workerGroup;
    private Channel channel;

    public Client(int port, String host, GameEntity player) {
        this.port = port;
        this.host = host;
        this.player = player;
    }

    public ChannelFuture start() {
        workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioDatagramChannel.class)
                .handler(new ChannelInitializer(player));

        ChannelFuture channelFuture = bootstrap.bind(new InetSocketAddress(0));
        channelFuture.syncUninterruptibly();

        channel = channelFuture.channel();

        return channelFuture;
    }

    public ChannelFuture write(Object msg) throws InterruptedException {
        ChannelFuture channelFuture = channel.writeAndFlush(msg).sync();

        return channelFuture;
    }

    public void stop() {
        if (channel != null) {
            channel.close();
        }
        workerGroup.shutdownGracefully();
    }
}
