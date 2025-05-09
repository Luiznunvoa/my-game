package com.game.codec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.Message;
import com.game.util.ConsoleColor;
import com.game.util.ConsoleUtil;

/**
 * JsonEncoder serializes a Message to JSON and Base64-encoded string.
 */
public class JsonEncoder {
    private final String json;
    private final String b64;

    public JsonEncoder(Message message) {
        String tmpJson = null;
        String tmpB64  = null;

        try {
            // Serialize message to JSON
            ObjectMapper mapper = new ObjectMapper();
            tmpJson = mapper.writeValueAsString(message);
            // Encode JSON bytes as Base64 (UTF-8 charset)
            tmpB64 = Base64.getEncoder()
                           .encodeToString(tmpJson.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            ConsoleUtil.printf("[JsonEncoder] Error serializing message: %s", ConsoleColor.RED, e.getMessage());
        }

        this.json = tmpJson;
        this.b64  = tmpB64;
    }

    /**
     * @return JSON string representation of the message (UTF-8)
     */
    public String getJson() {
        return json;
    }

    /**
     * @return Base64-encoded JSON string
     */
    public String getB64() {
        return b64;
    }
}

