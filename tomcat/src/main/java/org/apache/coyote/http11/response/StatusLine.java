package org.apache.coyote.http11.response;

import org.apache.coyote.http11.HttpProtocol;
import org.apache.coyote.http11.constants.HttpFormat;

/**
 * HTTP-Version SP Status-Code SP Reason-Phrase CRLF
 */
public record StatusLine(HttpProtocol protocol, HttpStatusCode statusCode) {

    public StatusLine(HttpProtocol protocol) {
        this(protocol, null);
    }

    public String toMessage() {
        return protocol.description() + HttpFormat.SP + statusCode.getCode() + HttpFormat.SP + statusCode.getDescription() + HttpFormat.SP + HttpFormat.CRLF;
    }
}
