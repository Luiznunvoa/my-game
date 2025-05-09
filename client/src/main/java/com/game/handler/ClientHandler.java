package com.game.handler;

import com.game.util.ConsoleUtil;

import com.game.model.Message;
import com.game.util.ConsoleColor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Message> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Message message = new Message();
        message.content = "Hi";
        message.test = 2;

        ctx.writeAndFlush(message);
        ConsoleUtil.printf("[Client] Sent Message: %s\n", ConsoleColor.YELLOW, message.content);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) {
        ConsoleUtil.printf(
                "[Client] Received from %s\n",
                ConsoleColor.GREEN,
                ctx.channel().remoteAddress() + ": " + message.content);
        ConsoleUtil.printf("[Client] test * 3 = %d\n", message.test * 3);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ConsoleUtil.printf("[Client] Error in ClientHandler:\n %s\n", ConsoleColor.RED, cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
