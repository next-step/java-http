package org.apache.coyote.http11.response;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;
import org.apache.coyote.http11.response.statusline.StatusLine;

public abstract class HttpResponse {

    final StatusLine statusLine;
    final Http11ResponseHeader http11ResponseHeader;
    final MessageBody messageBody;


    protected HttpResponse(final StatusLine statusLine, final MessageBody messageBody,
        Http11ResponseHeader http11ResponseHeader) {
        this.statusLine = statusLine;
        this.http11ResponseHeader = http11ResponseHeader;
        this.messageBody = messageBody;
    }

    public StatusLine getStatusLine() {
        return statusLine;
    }

    public byte[] getMessageBody() {
        return messageBody.getMessageBody();
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
        return Stream.of(statusLine, http11ResponseHeader,"", messageBody)
            .filter(Objects::nonNull)
            .map(Objects::toString)
            .collect(Collectors.joining("\r\n"));
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
            "statusLine=" + statusLine +
            ", http11ResponseHeader=" + http11ResponseHeader +
            '}';
    }
}
