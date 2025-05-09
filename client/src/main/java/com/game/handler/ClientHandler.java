package com.game.handler;

import com.game.util.ConsoleUtil;

import java.util.Objects;

import com.game.codec.JsonDecoder;
import com.game.codec.JsonEncoder;
import com.game.model.Message;
import com.game.util.ConsoleColor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    public ClientHandler() {
        super();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Message message = new Message();
        message.content = "Hi";
        message.test = 2;

        JsonEncoder encoder = new JsonEncoder(message);

        ctx.writeAndFlush(Objects.requireNonNull(encoder.getB64()) + System.lineSeparator());
        ConsoleUtil.printf("[Client] Sent Message: %s\n", ConsoleColor.YELLOW, Objects.requireNonNull(encoder.getJson()));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String b64) {
        JsonDecoder decoder = new JsonDecoder(b64);
        ConsoleUtil.printf("[Client] Received from %s\n", ConsoleColor.GREEN, ctx.channel().remoteAddress() + ": " + decoder.getJson());
        ConsoleUtil.printf("[Client] test * 3 = %d\n", decoder.getMessage().test * 3);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ConsoleUtil.printf("[Client] Error in ClientHandler:\n %s\n", ConsoleColor.RED, cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
