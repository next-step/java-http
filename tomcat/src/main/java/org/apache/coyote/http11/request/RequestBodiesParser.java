package org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.model.RequestBodies;

import java.util.HashMap;
import java.util.Map;

public class RequestBodiesParser {
    public static RequestBodies parse(String bodyString) {
        Map<String,String> bodyMap = new HashMap<>();
        String[] pairs = bodyString.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];
                bodyMap.put(key, value);
            }
        }
        return new RequestBodies(bodyMap);
    }
}
