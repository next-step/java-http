package org.apache.coyote.http11.request;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class MessageBody {

    private final String entityMessage;

    public MessageBody(String entityMessage) {
        this.entityMessage = entityMessage;
    }

    public String getEntityMessage() {
        return entityMessage;
    }


    public Map<String, Object> toMap() {
        return Arrays.stream(entityMessage.split("&"))
                 .map(pair -> pair.split("="))
                 .filter(keyValue -> keyValue.length == 2)
                 .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1]));

    }
}
