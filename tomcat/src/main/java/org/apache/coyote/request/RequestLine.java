package org.apache.coyote.request;

public class RequestLine {
    public static final String REQUEST_DELIMITER = " ";

    private final String httpMethod;
    private final HttpPath httpPath;
    private final String httpProtocol;

    public RequestLine(String httpMethod, HttpPath httpPath, String httpProtocol) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.httpProtocol = httpProtocol;
    }

    public static RequestLine parse(String request) {
        String[] requestParts = request.split(REQUEST_DELIMITER);
        String method = requestParts[0];
        HttpPath path = HttpPath.parse(requestParts[1]);
        String httpProtocol = requestParts[2];
        return new RequestLine(method, path, httpProtocol);
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public HttpPath getHttpPath() {
        return httpPath;
    }

    public String getHttpProtocol() {
        return httpProtocol;
    }
}
