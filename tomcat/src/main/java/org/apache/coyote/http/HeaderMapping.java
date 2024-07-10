package org.apache.coyote.http;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HeaderMapping {
    public static final String HEADER_SPACE = " ";
    private static final String HEADER_ASSIGNMENT = ": ";
    private final Map<String, String> headerMapping = new LinkedHashMap<>();

    private static String toHeaderLine(final Map.Entry<String, String> header) {
        return header.getKey() + HEADER_ASSIGNMENT + header.getValue();
    }

    public void addHeader(final HttpHeader header, final String value) {
        headerMapping.put(header.header(), value + HEADER_SPACE);
    }

    public String convertHttpHeaders() {
        return headerMapping.entrySet()
                .stream()
                .map(HeaderMapping::toHeaderLine)
                .collect(Collectors.joining(Constants.CR.getValue() + Constants.LF.getValue()));
    }
}
