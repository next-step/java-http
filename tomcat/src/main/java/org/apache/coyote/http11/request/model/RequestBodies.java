package org.apache.coyote.http11.request.model;

import java.util.Map;
import java.util.Objects;

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

    public Map<String, String> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestBodies that = (RequestBodies) o;
        return Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(body);
    }
}
