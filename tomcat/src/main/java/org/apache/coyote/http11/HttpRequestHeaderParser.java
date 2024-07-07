package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpRequestHeader;
import org.apache.coyote.http11.model.RequestLine;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import static org.apache.coyote.http11.model.HttpRequestHeader.REQUEST_LINE_KEY;

public class HttpRequestHeaderParser {
    private static final String COLON_SPACE = ": ";
    private static final int REQUEST_LINE_INDEX = 0;
    private static final int HAS_NOT_OTHER_HEADER_SIZE = 1;
    private static final int KEY_INDEX = 0;
    private static final int VALUE_INDEX = 1;
    private static final RequestLineParser REQUEST_LINE_PARSER = new RequestLineParser();

    public HttpRequestHeader parse(final List<String> lines) throws IOException {
        final HashMap<String, Object> headerMap = new HashMap<>();

        if (lines.isEmpty()) {
            return new HttpRequestHeader(headerMap);
        }

        requestLineParsing(lines, headerMap);
        otherHeadersParsing(lines, headerMap);

        return new HttpRequestHeader(headerMap);
    }

    private void requestLineParsing(final List<String> lines, final HashMap<String, Object> headerMap) {
        final RequestLine requestLine = REQUEST_LINE_PARSER.parse(lines.get(REQUEST_LINE_INDEX));
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
        headerMap.put(splitHeaderLine[KEY_INDEX].toLowerCase(), splitHeaderLine[VALUE_INDEX].trim());
    }

    private boolean isNotHeaderLine(final String headerLine) {
        return !headerLine.contains(COLON_SPACE);
    }
}
