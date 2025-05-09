package com.game.codec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.Message;
import com.game.util.ConsoleUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * Netty handler that decodes Base64-encoded JSON strings into Message objects.
 */
public class JsonDecoder extends MessageToMessageDecoder<String> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void decode(ChannelHandlerContext ctx, String b64msg, List<Object> out) {
        try {
            byte[] jsonBytes = Base64.getDecoder().decode(b64msg);
            String json = new String(jsonBytes, StandardCharsets.UTF_8);
            Message msg = mapper.readValue(json, Message.class);
            out.add(msg);
        } catch (IllegalArgumentException e) {
            ConsoleUtil.printf("[JsonDecoder] Invalid Base64 input: %s", e.getMessage());
        } catch (Exception e) {
            ConsoleUtil.printf("[JsonDecoder] JSON decoding error: %s", e.getMessage());
        }
    }
}
