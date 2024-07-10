package org.apache.http.header;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

public enum HeaderName {
    CONTENT_TYPE("Content-Type", ContentType::new),
    CONTENT_LENGTH("Content-Length", ContentLength::new),
    LOCATION("Location", Location::new);

    private final String name;
    private final Function<String, HttpHeader> convertToHeader;

    HeaderName(String name, Function<String, HttpHeader> converter) {
        this.name = name;
        this.convertToHeader = converter;
    }

    public HttpHeader toHeader(final String fullHeader) {
        var value = fullHeader.replace(name + HttpHeader.DELIMITER, "");
        return convertToHeader.apply(value);
    }

    @Override
    public String toString() {
        return name;
    }

    public static Optional<HttpHeader> match(final String fullHeader) {
        return Arrays.stream(values())
                .filter(type -> fullHeader.startsWith(type.name))
                .findFirst()
                .map(headerName -> headerName.toHeader(fullHeader));
    }
}
