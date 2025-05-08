package com.game.handler;

import com.game.util.ConsoleColor;
import com.game.util.ConsoleUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ServerHandler: receives messages from clients and prints them to console.
 */
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        // Print the received message along with client address
        ConsoleUtil.printf("[Server] Received from %s", ConsoleColor.GREEN, ctx.channel().remoteAddress() + ": " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ConsoleUtil.printf("[Server] Error in ServerHandler:\n %s\n", ConsoleColor.RED, cause.getMessage());
        // cause.printStackTrace();
        ctx.close();
    }
}
