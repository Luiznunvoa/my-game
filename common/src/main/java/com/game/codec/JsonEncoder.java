package com.game.codec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.Message;
import com.game.util.ConsoleUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

/**
 * Encodes Message objects into Base64-encoded JSON strings for the Netty
 * pipeline.
 */
public class JsonEncoder extends MessageToMessageEncoder<Message> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, List<Object> out) {
        try {
            String json = mapper.writeValueAsString(msg);
            String b64 = Base64.getEncoder().encodeToString(json.getBytes(StandardCharsets.UTF_8));
            out.add(b64 + System.lineSeparator());
        } catch (Exception e) {
            ConsoleUtil.printf("[JsonEncoder] JSON encoding error: %s", e.getMessage());
        }
    }
}
