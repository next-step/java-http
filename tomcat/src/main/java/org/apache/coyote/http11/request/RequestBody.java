package org.apache.coyote.http11.request;

import java.util.Objects;

public class RequestBody {
    private final String requestBody;

    public RequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestBody() {
        return requestBody;
    }

    @Override
    public String toString() {
        return "RequestBody{" +
                "requestBody='" + requestBody + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestBody that = (RequestBody) o;
        return Objects.equals(requestBody, that.requestBody);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(requestBody);
    }

}
