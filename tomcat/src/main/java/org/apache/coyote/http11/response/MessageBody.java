package org.apache.coyote.http11.response;

import org.apache.coyote.http11.constants.HttpFormat;

import java.nio.charset.Charset;

public record MessageBody(byte[] contentBody, Charset charset) {

    public final static MessageBody EMPTY = new MessageBody(new byte[0], Charset.defaultCharset());

    public MessageBody(byte[] contentBody) {
        this(contentBody, Charset.defaultCharset());
    }

    public String toContentLengthMessage() {
        return HttpFormat.headerFieldValue(HttpFormat.HEADERS.CONTENT_LENGTH, Integer.toString(length()));
    }

    public int length() {
        return contentBody.length;
    }

    @Override
    public String toString() {
        return new String(contentBody, charset);
    }
}
