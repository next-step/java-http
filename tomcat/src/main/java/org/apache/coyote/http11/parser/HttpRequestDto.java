package org.apache.coyote.http11.parser;

import java.util.List;
import java.util.Objects;
import org.apache.coyote.http11.exception.MalformedRequestlLineException;

public class HttpRequestDto {

    public static final int REQUEST_LINE_LENGTH = 3;
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;
    public final String requestMethod;
    public final String requestUrl;
    public final String requestProtocol;


    private HttpRequestDto(final String requestMethod, final String requestUrl,
        final String requestProtocol) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.requestProtocol = requestProtocol;
    }

    public static HttpRequestDto of(List<String> requestLine) {
        validate(requestLine);
        String method = requestLine.get(METHOD_INDEX);
        String requestUrl = requestLine.get(URL_INDEX);
        String protocol = requestLine.get(PROTOCOL_INDEX);
        return new HttpRequestDto(method, requestUrl, protocol);
    }

    private static void validate(List<String> requestLine) {
        if (Objects.isNull(requestLine) || requestLine.isEmpty()
            || requestLine.size() != REQUEST_LINE_LENGTH) {
            throw new MalformedRequestlLineException("HTTP Request의 형태가 비정상적입니다.");
        }
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestProtocol() {
        return requestProtocol;
    }

}
