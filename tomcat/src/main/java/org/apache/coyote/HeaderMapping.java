package org.apache.coyote;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HeaderMapping {
    private final Map<String, String> headerMapping = new LinkedHashMap<>();

    public void addHeader(String header, String value) {
        headerMapping.put(header, value + " ");
    }

    public String convertHttpHeaders() {
        return headerMapping.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
