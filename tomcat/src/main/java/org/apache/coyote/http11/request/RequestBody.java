package org.apache.coyote.http11.request;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestBody {

    private String body;

    public RequestBody(String body) {
        this.body = body;
    }

    public String value() {
        return body;
    }


    public Map<String, Object> toMap() {
        return Arrays.stream(body.split("&"))
                 .map(pair -> pair.split("="))
                 .filter(keyValue -> keyValue.length == 2)
                 .collect(Collectors.toMap(keyValue -> keyValue[0], keyValue -> keyValue[1]));

    }
}
