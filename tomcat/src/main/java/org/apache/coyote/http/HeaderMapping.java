package org.apache.coyote.http;

import java.util.*;
import java.util.stream.Collectors;

public class HeaderMapping {
    public static final String HEADER_SPACE = " ";
    private static final String HEADER_ASSIGNMENT = ": ";
    private static final String CONTENT_TYPE_SEPARATOR = ";";
    private static final int DEFAULT_HEADER_POINT = 0;

    private final Map<HttpHeader, List<String>> headerMapping = new LinkedHashMap<>();

    private static String toHeaderLine(final Map.Entry<HttpHeader, List<String>> header) {
        final String valueLine = toHeaderValueLine(header);
        return String.format("%s%s%s%s", header.getKey().header(), HEADER_ASSIGNMENT, valueLine, HEADER_SPACE);
    }

    private static String toHeaderValueLine(final Map.Entry<HttpHeader, List<String>> header) {
        return String.join(CONTENT_TYPE_SEPARATOR, header.getValue());
    }

    public void addHeader(final HttpHeader header, final String value) {
        final List<String> headerValues = headerMapping.getOrDefault(header, new ArrayList<>());

        if (!isAddableValue(value, headerValues)) {
            return;
        }

        headerValues.add(value.trim());

        headerMapping.put(header, headerValues);
    }

    public void addHeader(final HttpHeader header, final String value1, final String value2) {
        final List<String> headerValues = headerMapping.getOrDefault(header, new ArrayList<>());

        if (isAddableValue(value1, headerValues)) {
            headerValues.add(value1.trim());
        }

        if (isAddableValue(value2, headerValues)) {
            headerValues.add(value2.trim());
        }

        headerMapping.put(header, headerValues);
    }

    public void addHeader(final HttpHeader header, final String... values) {
        final List<String> headerValues = headerMapping.getOrDefault(header, new ArrayList<>());
        headerValues.addAll(Arrays.stream(values)
                .filter(value -> isAddableValue(value, headerValues))
                .map(String::trim).toList());

        headerMapping.put(header, headerValues);
    }

    private boolean isAddableValue(final String value, final List<String> headerValues) {
        return !value.isBlank() && !headerValues.contains(value);
    }

    public String convertHttpHeaders() {
        return headerMapping.entrySet()
                .stream()
                .filter(entry -> Objects.nonNull(entry.getKey()))
                .map(HeaderMapping::toHeaderLine)
                .collect(Collectors.joining(System.lineSeparator()));
    }

    public List<String> getHeader(final HttpHeader httpHeader) {
        return headerMapping.get(httpHeader);
    }

    public boolean isFormUrlEncoded() {
        return Objects.equals(this.headerMapping.get(HttpHeader.CONTENT_TYPE).get(DEFAULT_HEADER_POINT), ContentType.APPLICATION_FORM_URLENCODED.type());
    }

    public Map<String, String> getHeaders() {
        return headerMapping.entrySet().stream()
                .map(entry -> new String[]{entry.getKey().header(), toHeaderValueLine(entry)})
                .collect(Collectors.toMap(pair -> pair[0], pair -> pair[1]));
    }
}
