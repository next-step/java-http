package org.apache.coyote.request;

import java.util.HashMap;
import java.util.Map;

public class RequestBody {

    private final Map<String, String> requestBodies;

    public RequestBody() {
        this.requestBodies = new HashMap<>();
    }

    public RequestBody(Map<String, String> requestBodies) {
        this.requestBodies = requestBodies;
    }

    public static RequestBody parse(String requestBody) {
        Map<String, String> params = new HashMap<>();

        String[] pairs = requestBody.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            } else {
                params.put(keyValue[0], "");
            }
        }
        return new RequestBody(params);
    }

    public String get(String key) {
        return requestBodies.get(key);
    }
}
