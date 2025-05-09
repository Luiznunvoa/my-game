package com.game.handler;

import com.game.codec.JsonDecoder;
import com.game.codec.JsonEncoder;
import com.game.util.ConsoleColor;
import com.game.util.ConsoleUtil;
import com.game.model.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String b64) {

        JsonDecoder decoder = new JsonDecoder(b64);
        ConsoleUtil.printf("[Server] Received from %s: %s\n", ConsoleColor.GREEN,
                ctx.channel().remoteAddress(), decoder.getJson());

        ConsoleUtil.printf("[Server] test + 2 = %d\n", decoder.getMessage().test+ 2);

        Message response = new Message();
        response.content = "How are you?";
        response.test= 21;

        JsonEncoder encoder = new JsonEncoder(response);
        ctx.writeAndFlush(encoder.getB64() + System.lineSeparator());
        ConsoleUtil.printf("[Server] Sent %s\n", ConsoleColor.YELLOW, encoder.getJson());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ConsoleUtil.printf("[Server] Error in ServerHandler:\n %s\n", ConsoleColor.RED, cause.getMessage());
        ctx.close();
    }
}
