package com.game.codec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.model.Message;
import com.game.util.ConsoleColor;
import com.game.util.ConsoleUtil;

public class JsonEncoder {
    private final String json;
    private final String b64;

    public JsonEncoder(Message message) {
        String tmpJson = null;
        String tmpB64  = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            tmpJson = mapper.writeValueAsString(message);
            tmpB64 = Base64.getEncoder()
                           .encodeToString(tmpJson.getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
            ConsoleUtil.printf("[JsonEncoder] Error serializing message: %s", ConsoleColor.RED, e.getMessage());
        }

        this.json = tmpJson;
        this.b64  = tmpB64;
    }

    public String getJson() {
        return json;
    }

    public String getB64() {
        return b64;
    }
}

