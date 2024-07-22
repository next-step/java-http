package org.apache.coyote.http11.response;

import java.util.Arrays;
import java.util.Objects;

public abstract class HttpResponse {

    final StatusLine statusLine;
    final Http11ResponseHeader http11ResponseHeader;
    final byte[] messageBody;


    protected HttpResponse(final StatusLine statusLine, final byte[] messageBody,
        Http11ResponseHeader http11ResponseHeader) {
        this.statusLine = statusLine;
        this.http11ResponseHeader = http11ResponseHeader;
        this.messageBody = messageBody;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public byte[] getMessageBody() {
        return messageBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        HttpResponse that = (HttpResponse) o;
        return Objects.equals(statusLine, that.statusLine) && Objects.equals(
            messageBody, that.messageBody);
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusLine, messageBody);
    }

    public String write() {

        return String.join("\r\n",
            statusLine.toString(),
            http11ResponseHeader.toString(),
            "",
            new String(messageBody));
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
            "statusLine=" + statusLine +
            ", http11ResponseHeader=" + http11ResponseHeader +
            ", messageBody=" + Arrays.toString(messageBody) +
            '}';
    }
}
