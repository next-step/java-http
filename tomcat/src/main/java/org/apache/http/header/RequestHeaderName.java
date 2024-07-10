package org.apache.http.header;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public enum RequestHeaderName {
    CONTENT_TYPE("Content-Type", ContentType::new),
    CONTENT_LENGTH("Content-Length", ContentLength::new);

    private final String name;
    private final Function<String, HttpRequestHeader> convertToHeader;

    RequestHeaderName(String name, Function<String, HttpRequestHeader> converter) {
        this.name = name;
        this.convertToHeader = converter;
    }

    public HttpRequestHeader toHeader(final String fullHeader) {
        var value = fullHeader.replace(name + HttpRequestHeader.DELIMITER, "");
        return convertToHeader.apply(value);
    }

    @Override
    public String toString() {
        return name;
    }

    public static Optional<HttpRequestHeader> match(final String fullHeader) {
        return Arrays.stream(values())
                .filter(type -> fullHeader.startsWith(type.name))
                .findFirst()
                .map(headerName -> headerName.toHeader(fullHeader));
    }
}
