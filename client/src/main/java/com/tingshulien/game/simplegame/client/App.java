package com.tingshulien.game.simplegame.client;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.GameEntity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.settings.GameSettings;

import java.net.InetSocketAddress;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class App extends GameApplication {

    private int port = 8080;
    private String host = "127.0.0.1";
    InetSocketAddress remoteAddress = new InetSocketAddress(host, port);

    private GameEntity player;
    private IntegerProperty pixelsMoved;
    private Client client;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(600);
        settings.setHeight(600);
        settings.setTitle("Basic Game App");
        settings.setVersion("0.1");
        settings.setIntroEnabled(false); // turn off intro
        settings.setMenuEnabled(false);  // turn off menus
    }

    @Override
    protected void initGame() {
        player = Entities.builder()
                .at(300, 300)
                .viewFromNode(new Rectangle(25, 25, Color.BLUE))
                .buildAndAttach(getGameWorld());

        client = new Client(port, host, player);
        client.start();

        pixelsMoved = new SimpleIntegerProperty();
    }

    @Override
    protected void initInput() {
        Input input = getInput(); // get input service

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                ByteBuf byteBuf = Unpooled.copiedBuffer("d", CharsetUtil.UTF_8);
                try {
                    client.write(new DatagramPacket(byteBuf, remoteAddress));
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                ByteBuf byteBuf = Unpooled.copiedBuffer("a", CharsetUtil.UTF_8);
                try {
                    client.write(new DatagramPacket(byteBuf, remoteAddress));
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                ByteBuf byteBuf = Unpooled.copiedBuffer("w", CharsetUtil.UTF_8);
                try {
                    client.write(new DatagramPacket(byteBuf, remoteAddress));
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                ByteBuf byteBuf = Unpooled.copiedBuffer("s", CharsetUtil.UTF_8);
                try {
                    client.write(new DatagramPacket(byteBuf, remoteAddress));
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }, KeyCode.S);
    }

    @Override
    protected void initUI() {
        Text textPixels = new Text();
        textPixels.setTranslateX(50); // x = 50
        textPixels.setTranslateY(100); // y = 100

        getGameScene().addUINode(textPixels); // add to the scene graph

        textPixels.textProperty().bind(pixelsMoved.asString());
    }

    @Override
    protected void initAssets() {

    }

    @Override
    protected void initPhysics() {

    }

    @Override
    protected void onUpdate(double tpf) {

    }
}
