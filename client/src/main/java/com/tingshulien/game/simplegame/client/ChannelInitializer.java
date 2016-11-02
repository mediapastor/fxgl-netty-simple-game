package com.tingshulien.game.simplegame.client;

import com.almasb.fxgl.entity.GameEntity;

import io.netty.channel.Channel;
import io.netty.channel.ChannelPipeline;

public class ChannelInitializer extends io.netty.channel.ChannelInitializer<Channel> {

    private GameEntity player;

    public ChannelInitializer(GameEntity player) {
        this.player = player;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new MoveHandler(player));
    }
}
