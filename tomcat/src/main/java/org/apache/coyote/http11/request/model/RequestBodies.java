package org.apache.coyote.http11.request.model;

import java.util.Map;

public class RequestBodies {
    private final Map<String, String> body;

    public RequestBodies(final Map<String, String> body) {
        this.body = body;
    }

    public static RequestBodies emptyRequestBodies() {
        return new RequestBodies(Map.of());
    }

    public String getRequestBodyValueByKey(String key) {
        return body.get(key);
    }
}
