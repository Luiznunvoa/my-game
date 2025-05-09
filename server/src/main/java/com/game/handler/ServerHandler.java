
package com.game.handler;

import com.game.model.Message;
import com.game.util.ConsoleColor;
import com.game.util.ConsoleUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * ServerHandler mantém um ChannelGroup de todos os canais.
 * Ao receber uma Message, loga e a retransmite a todos,
 * prefixando o content com o endereço de quem enviou.
 */
public class ServerHandler extends SimpleChannelInboundHandler<Message> {

    // Grupo estático que reúne todos os canais ativos
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // Adiciona canal novo ao grupo
        channels.add(ctx.channel());
        ConsoleUtil.printf(
                "[Server] Client connected: %s\n",
                ConsoleColor.CYAN,
                ctx.channel().remoteAddress());

        Message broadcast = new Message();
        broadcast.content = String.format("Client connected: %s", ctx.channel().remoteAddress());

        for (Channel ch : channels) {
            if (ch != ctx.channel()) {
                ch.writeAndFlush(broadcast);
            }
        }

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // Remove canal que saiu
        channels.remove(ctx.channel());
        ConsoleUtil.printf(
                "[Server] Client disconnected: %s\n",
                ConsoleColor.CYAN,
                ctx.channel().remoteAddress());
        Message broadcast = new Message();
        broadcast.content = String.format("Client disconnected: %s", ctx.channel().remoteAddress());

        for (Channel ch : channels) {
            if (ch != ctx.channel()) {
                ch.writeAndFlush(broadcast);
            }
        }

        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message message) {
        String sender = ctx.channel().remoteAddress().toString();
        ConsoleUtil.printf(
                "[Server] Received from %s: %s\n",
                ConsoleColor.GREEN,
                sender, message.content);

        Message broadcast = new Message();
        broadcast.content = String.format("[%s] %s", sender, message.content);

        for (Channel ch : channels) {
            if (ch != ctx.channel()) {
                ch.writeAndFlush(broadcast);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ConsoleUtil.printf(
                "[Server] Error in ServerHandler:\n %s\n",
                ConsoleColor.RED,
                cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }
}
