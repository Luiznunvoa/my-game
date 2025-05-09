package com.game.codec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.game.model.Message;
import com.game.util.ConsoleUtil;

public class JsonDecoder {
    private final byte[] jsonBytes;
    private final String json;
    private final Message message;

    public JsonDecoder(String b64) {
        byte[] tmpBytes = null;
        String tmpJson = null;
        Message tmpMsg = null;

        try {
            tmpBytes = Base64.getDecoder().decode(b64);
            tmpJson = new String(tmpBytes, StandardCharsets.UTF_8);
            tmpMsg = new ObjectMapper().readValue(tmpJson, Message.class);
        } catch (IllegalArgumentException e) {
            ConsoleUtil.printf("[JsonDecoder] Invalid Base64 input: %s", e.getMessage());
        } catch (JsonMappingException e) {
            ConsoleUtil.printf("[JsonDecoder] JSON mapping error: %s", e.getMessage());
        } catch (JsonProcessingException e) {
            ConsoleUtil.printf("[JsonDecoder] JSON processing error: %s", e.getMessage());
        }

        this.jsonBytes = tmpBytes;
        this.json = tmpJson;
        this.message = tmpMsg;
    }

    public byte[] getJsonBytes() {
        return jsonBytes;
    }

    public String getJson() {
        return json;
    }

    public Message getMessage() {
        return message;
    }
}
