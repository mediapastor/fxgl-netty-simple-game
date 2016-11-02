package com.tingshulien.game.simplegame.client;

import com.almasb.fxgl.entity.GameEntity;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class MoveHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private GameEntity player;

    public MoveHandler(GameEntity player) {
        this.player = player;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
        ByteBuf buf = msg.content();

        String command = buf.toString(CharsetUtil.UTF_8);

        switch (command) {
            case "w" :
                player.getPositionComponent().translateY(-5);
                break;
            case "a" :
                player.getPositionComponent().translateX(-5);
                break;
            case "s" :
                player.getPositionComponent().translateY(5);
                break;
            case "d" :
                player.getPositionComponent().translateX(5);
                break;
            default:
                break;
        }

        buf.clear();

        System.out.println("Player location is " + player.getPosition());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.err.println(cause.getMessage());
        ctx.close();
    }
}
