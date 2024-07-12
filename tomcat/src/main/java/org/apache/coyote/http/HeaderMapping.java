package org.apache.coyote.http;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class HeaderMapping {
    public static final String HEADER_SPACE = " ";
    private static final String HEADER_ASSIGNMENT = ": ";
    private static final String CONTENT_TYPE_SEPARATOR = ";";
    private static final int DEFAULT_HEADER_POINT = 0;

    private final Map<HttpHeader, String[]> headerMapping = new LinkedHashMap<>();

    private static String toHeaderLine(final Map.Entry<HttpHeader, String[]> header) {
        final String valueLine = String.join(CONTENT_TYPE_SEPARATOR, header.getValue());

        return header.getKey().header() + HEADER_ASSIGNMENT + valueLine + HEADER_SPACE;
    }

    public void addHeader(final HttpHeader header, final String... value) {
        headerMapping.put(header, value);
    }

    public String convertHttpHeaders() {
        return headerMapping.entrySet()
                .stream()
                .map(HeaderMapping::toHeaderLine)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public String[] getHeader(final HttpHeader httpHeader) {
        return headerMapping.get(httpHeader);
    }

    public boolean isFormUrlEncoded() {
        return Objects.equals(this.headerMapping.get(HttpHeader.CONTENT_TYPE)[DEFAULT_HEADER_POINT], ContentType.APPLICATION_FORM_URLENCODED.type());
    }
}
