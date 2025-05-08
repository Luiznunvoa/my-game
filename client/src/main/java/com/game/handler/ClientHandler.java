package com.game.handler;

import com.game.util.ConsoleUtil;
import com.game.util.ConsoleColor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ClientHandler: sends an initial START_GAME message upon connection and
 * ignores further reads.
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    public ClientHandler() {
        super();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send START_GAME message to the server when the channel becomes active
        String startMsg = "I'm Here" + System.lineSeparator();
        ctx.writeAndFlush(startMsg);
        ConsoleUtil.printf("[Client] Sent %s", ConsoleColor.YELLOW, startMsg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        ConsoleUtil.printf("[Client] Received from %s", ConsoleColor.GREEN, ctx.channel().remoteAddress() + ": " + msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("Error in ClientHandler: " + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
