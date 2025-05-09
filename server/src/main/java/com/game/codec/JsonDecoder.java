package com.game.codec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.game.model.Message;
import com.game.util.ConsoleUtil;

/**
 * JsonDecoder deserializes a Base64-encoded JSON string into a Message object.
 */
public class JsonDecoder {
    private final byte[] jsonBytes;
    private final String json;
    private final Message message;

    /**
     * @param b64 Base64-encoded JSON string
     */
    public JsonDecoder(String b64) {
        byte[] tmpBytes = null;
        String tmpJson  = null;
        Message tmpMsg  = null;

        try {
            // Decode Base64 to raw JSON bytes
            tmpBytes = Base64.getDecoder().decode(b64);
            // Convert bytes to JSON string using UTF-8
            tmpJson = new String(tmpBytes, StandardCharsets.UTF_8);
            // Deserialize JSON to Message
            tmpMsg = new ObjectMapper().readValue(tmpJson, Message.class);
        } catch (IllegalArgumentException e) {
            ConsoleUtil.printf("[JsonDecoder] Invalid Base64 input: %s", e.getMessage());
        } catch (JsonMappingException e) {
            ConsoleUtil.printf("[JsonDecoder] JSON mapping error: %s", e.getMessage());
        } catch (JsonProcessingException e) {
            ConsoleUtil.printf("[JsonDecoder] JSON processing error: %s", e.getMessage());
        }

        this.jsonBytes = tmpBytes;
        this.json      = tmpJson;
        this.message   = tmpMsg;
    }

    /**
     * @return raw JSON bytes (UTF-8)
     */
    public byte[] getJsonBytes() {
        return jsonBytes;
    }

    /**
     * @return JSON string representation
     */
    public String getJson() {
        return json;
    }

    /**
     * @return deserialized Message object, or null if decoding failed
     */
    public Message getMessage() {
        return message;
    }
}

