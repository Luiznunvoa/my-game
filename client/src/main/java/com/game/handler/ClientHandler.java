package com.game.handler;

import com.game.model.Message;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * ClientHandler: processes incoming Message instances.
 */
public class ClientHandler extends SimpleChannelInboundHandler<Message> {
    public ClientHandler() {
        super();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
        // Basic handling: print the message type
        System.out.println("Received message: " + msg.getType());
    }
}
