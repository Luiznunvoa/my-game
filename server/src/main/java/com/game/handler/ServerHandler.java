package com.game.handler;

import com.game.util.ConsoleColor;
import com.game.util.ConsoleUtil;
import com.game.model.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Message> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) {

        ConsoleUtil.printf("[Server] Received from %s: %s\n", ConsoleColor.GREEN,
                ctx.channel().remoteAddress(), message.content);

        ConsoleUtil.printf("[Server] test + 2 = %d\n", message.test+ 2);

        Message response = new Message();
        response.content = "How are you?";
        response.test= 21;

        ctx.writeAndFlush(response);
        ConsoleUtil.printf("[Server] Sent %s\n", ConsoleColor.YELLOW, response.content);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ConsoleUtil.printf("[Server] Error in ServerHandler:\n %s\n", ConsoleColor.RED, cause.getMessage());
        ctx.close();
    }
}
