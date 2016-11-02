package com.tingshulien.game.simplegame.server;

import io.netty.channel.ChannelFuture;

public class App {

    private static final int PORT = 8080;

    public static void main(String[] args) {

        Server server;

        try {
            server = new Server(PORT);
            ChannelFuture future = server.start();

            // Wait until the connection is closed.
            future.channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
