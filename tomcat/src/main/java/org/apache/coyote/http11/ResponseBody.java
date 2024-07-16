package org.apache.coyote.http11;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public record ResponseBody(byte[] contentBody, Charset charset) {

    public final static ResponseBody EMPTY = new ResponseBody(new byte[0], StandardCharsets.UTF_8);
    private final static Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public ResponseBody(byte[] contentBody) {
        this(contentBody, DEFAULT_CHARSET);
    }

    public int length() {
        return contentBody.length;
    }

    @Override
    public String toString() {
        return new String(contentBody, charset);
    }
}
