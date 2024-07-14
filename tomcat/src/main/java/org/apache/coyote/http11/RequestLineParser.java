package org.apache.coyote.http11;

public final class RequestLineParser {

    public static final String SPACE = " ";
    public static final String NEWLINE = "\n";

    private RequestLineParser() {
    }

    public static HttpRequest parse(final String requestLine, final HttpHeaders headers, final RequestBody bodies) {
        String[] parts = requestLine.split(NEWLINE);
        final String[] firstLine = parts[0].split(SPACE); // httpMethod + httpPath + protocol
        final HttpMethod httpMethod = HttpMethod.from(firstLine[0]);
        final RequestTarget requestTarget = RequestTarget.from(firstLine[1], firstLine[2]);

        return new HttpRequest(httpMethod, requestTarget, headers, bodies);
    }

}
