package com.game.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.Message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Encodes a Message into JSON bytes.
 */
public class MessageEncoder extends MessageToMessageEncoder<Message> {
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) throws Exception {
        byte[] bytes = mapper.writeValueAsBytes(msg);
        ByteBuf buffer = ctx.alloc().buffer(bytes.length);
        buffer.writeBytes(bytes);
        out.add(buffer);
    }
}
