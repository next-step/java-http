package org.apache.coyote.request;

public class RequestLine {
    private static final String REQUEST_DELIMITER = " ";
    private static final int HTTP_METHOD_INDEX = 0;
    private static final int HTTP_PATH_INDEX = 1;
    private static final int HTTP_PROTOCOL_INDEX = 2;

    private final HttpMethod httpMethod;
    private final HttpPath httpPath;
    private final String httpProtocol;

    public RequestLine(HttpMethod httpMethod, HttpPath httpPath, String httpProtocol) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.httpProtocol = httpProtocol;
    }

    public static RequestLine parse(String request) {
        String[] requestParts = request.split(REQUEST_DELIMITER);
        HttpMethod method = HttpMethod.valueOf(requestParts[HTTP_METHOD_INDEX]);
        HttpPath path = HttpPath.parse(requestParts[HTTP_PATH_INDEX]);
        String httpProtocol = requestParts[HTTP_PROTOCOL_INDEX];
        return new RequestLine(method, path, httpProtocol);
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public String getHttpPath() {
        return httpPath.getPath();
    }

    public String findQueryParam(String key) {
        return httpPath.findQueryParam(key);
    }

    public String getHttpProtocol() {
        return httpProtocol;
    }
}
