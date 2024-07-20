package org.apache.coyote.http11.request;

import org.apache.coyote.http11.MapUtils;

import java.util.Map;

public class MessageBody {

    private final String entityMessage;

    public MessageBody(String entityMessage) {
        this.entityMessage = entityMessage;
    }

    public Map<String, Object> toMap() {
        return MapUtils.parseKeyValuePair("=", entityMessage.split("&"));
    }
}
