package org.apache.coyote.request;

public class RequestLine {
    public static final String REQUEST_DELIMITER = " ";

    private final String httpMethod;
    private final String path;
    private final String httpProtocol;

    public RequestLine(String httpMethod, String path, String httpProtocol) {
        this.httpMethod = httpMethod;
        this.path = path;
        this.httpProtocol = httpProtocol;
    }

    public static RequestLine parse(String request) {
        String[] requestParts = request.split(REQUEST_DELIMITER);
        String method = requestParts[0];
        String path = requestParts[1];
        String httpProtocol = requestParts[2];
        return new RequestLine(method, path, httpProtocol);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getPath() {
        return path;
    }

    public String getHttpProtocol() {
        return httpProtocol;
    }
}
