package org.apache.coyote.http11.response;

import java.nio.charset.Charset;

public record ResponseBody(byte[] contentBody, Charset charset) {

    public final static ResponseBody EMPTY = new ResponseBody(new byte[0], Charset.defaultCharset());

    public ResponseBody(byte[] contentBody) {
        this(contentBody, Charset.defaultCharset());
    }

    public int length() {
        return contentBody.length;
    }

    @Override
    public String toString() {
        return new String(contentBody, charset);
    }
}
