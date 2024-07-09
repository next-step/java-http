package org.apache.coyote.http11;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public final class RequestParser {

    public static RequestLine parse(InputStream is, Charset charset) {
        final var br = new BufferedReader(new InputStreamReader(is, charset));
        final var lines = br.lines().toList();
        if (!lines.isEmpty()) {
            return new RequestLine(lines.get(0));
        }
        throw new RuntimeException("Request is empty");
    }

}
