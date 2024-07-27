package org.apache.coyote.http11.parser;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.coyote.http11.exception.MalformedRequestlLineException;

public class HttpRequestDto {

    public static final int REQUEST_LINE_LENGTH = 3;
    private static final int METHOD_INDEX = 0;
    private static final int URL_INDEX = 1;
    private static final int PROTOCOL_INDEX = 2;

    public final String requestMethod;
    public final String requestUrl;
    public final String requestProtocol;
    public final HttpRequestHeaderDto requestHeader;
    public final String requestBody;

    private HttpRequestDto(final String requestMethod, final String requestUrl,
        final String requestProtocol, final HttpRequestHeaderDto requestHeader, String requestBody) {
        this.requestMethod = requestMethod;
        this.requestUrl = requestUrl;
        this.requestProtocol = requestProtocol;
        this.requestHeader = requestHeader;
        this.requestBody = requestBody;
    }

    public static HttpRequestDto of(List<String> requestLine, HttpRequestHeaderDto requestHeaders, String requestBody) {
        validate(requestLine);
        String method = requestLine.get(METHOD_INDEX);
        String requestUrl = requestLine.get(URL_INDEX);
        String protocol = requestLine.get(PROTOCOL_INDEX);

        return new HttpRequestDto(method, requestUrl, protocol, requestHeaders, requestBody);
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

    public String getRequestBody() {
        return requestBody;
    }

    public Optional<String> getCookie() {
        return Optional.ofNullable(requestHeader.getRequestHeader().get("Cookie"));
    }
}
