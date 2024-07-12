package nextstep.org.apache.coyote.http11.fixtures;

import java.util.StringJoiner;

public class TestHttpRequestMessageBuilder {

    private static final String REQUEST_LINE_FORMAT = "%s %s %s";
    private static final String HOST_HEADER_FORMAT = "Host: %s";
    private static final String CONNECTION_HEADER_FORMAT = "Connection: %s";
    private static final String ACCEPT_HEADER_FORMAT = "Accept: %s";
    private static final String EMPTY_LINE = "";

    private static final String method = "GET";
    private static final String path = "/index.html";
    private static final String httpProtocal = "HTTP/1.1";
    private static final String host = "localhost:8080";
    private static final String connection = "keep-alive";

    private final StringJoiner sj = new StringJoiner(System.lineSeparator());

    public TestHttpRequestMessageBuilder baseGetMessage() {
        sj.add(REQUEST_LINE_FORMAT.formatted(method, path, httpProtocal));
        sj.add(HOST_HEADER_FORMAT.formatted(host));
        sj.add(CONNECTION_HEADER_FORMAT.formatted(connection));
        return this;
    }

    public TestHttpRequestMessageBuilder requestLine(String method, String path, String httpProtocal) {
        sj.add(REQUEST_LINE_FORMAT.formatted(method, path, httpProtocal));
        return this;
    }

    public TestHttpRequestMessageBuilder acceptHeader(String accept) {
        sj.add(ACCEPT_HEADER_FORMAT.formatted(accept));
        return this;
    }

    public TestHttpRequestMessageBuilder emptyLine() {
        sj.add(EMPTY_LINE);
        return this;
    }

    public String build() {
        return sj.toString();
    }

}
