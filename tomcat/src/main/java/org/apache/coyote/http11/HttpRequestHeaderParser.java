package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpCookie;
import org.apache.coyote.http11.model.HttpHeaders;
import org.apache.coyote.http11.model.RequestLine;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.apache.coyote.http11.model.HttpHeaders.REQUEST_LINE_KEY;

public class HttpRequestHeaderParser {
    private static final String COOKIE_KEY = "Cookie";
    private static final String COLON_SPACE = ": ";
    private static final int REQUEST_LINE_INDEX = 0;
    private static final int HAS_NOT_OTHER_HEADER_SIZE = 1;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;

    private HttpRequestHeaderParser() {
    }

    public static HttpRequestHeaderParser getInstance() {
        return SingletonHelper.SINGLETON;
    }

    public HttpHeaders parse(final List<String> lines) throws IOException {
        final HashMap<String, Object> headerMap = new HashMap<>();

        if (lines.isEmpty()) {
            return new HttpHeaders(headerMap);
        }

        requestLineParsing(lines, headerMap);
        otherHeadersParsing(lines, headerMap);

        return new HttpHeaders(headerMap);
    }

    private void requestLineParsing(final List<String> lines, final HashMap<String, Object> headerMap) {
        final RequestLine requestLine = RequestLineParser.getInstance()
                .parse(lines.get(REQUEST_LINE_INDEX));
        headerMap.put(REQUEST_LINE_KEY, requestLine);
    }

    private void otherHeadersParsing(final List<String> lines, final HashMap<String, Object> headerMap) {
        if (lines.size() <= HAS_NOT_OTHER_HEADER_SIZE) {
            return;
        }

        for (final String headerLine : lines) {
            addOtherHeaders(headerLine, headerMap);
        }
    }

    private void addOtherHeaders(final String headerLine, final HashMap<String, Object> headerMap) {
        if (headerLine.isEmpty() || headerLine.isBlank()) {
            return;
        }

        if (isNotHeaderLine(headerLine)) {
            return;
        }

        final String[] splitHeaderLine = StringTokenizer.token(headerLine, COLON_SPACE);

        if (splitHeaderLine[KEY_INDEX].equals(COOKIE_KEY)) {
            headerMap.put(splitHeaderLine[KEY_INDEX], HttpCookie.fromStringLine(splitHeaderLine[VALUE_INDEX].trim()));
            return;
        }

        headerMap.put(splitHeaderLine[KEY_INDEX], splitHeaderLine[VALUE_INDEX].trim());
    }

    private boolean isNotHeaderLine(final String headerLine) {
        return !headerLine.contains(COLON_SPACE);
    }

    private static class SingletonHelper {
        private static final HttpRequestHeaderParser SINGLETON = new HttpRequestHeaderParser();
    }
}
