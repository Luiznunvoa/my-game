package com.game.handler;

import com.game.util.ConsoleUtil;
import com.game.util.ConsoleColor;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ClientHandler: sends an initial START_GAME message upon connection and ignores further reads.
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    public ClientHandler() {
        super();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // Send START_GAME message to the server when the channel becomes active
        String startMsg = "TEST MESSAGE" + System.lineSeparator();
        ctx.writeAndFlush(startMsg);
        ConsoleUtil.printf("[Client] Sent %s", ConsoleColor.YELLOW, startMsg);
        // No need to call super.channelActive(ctx) as we don't chain to another handler
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        // Intentionally left empty: this handler only sends a message on activation
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        System.err.println("Error in ClientHandler: " + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}

