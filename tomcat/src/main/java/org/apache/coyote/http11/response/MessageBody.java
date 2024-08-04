package org.apache.coyote.http11.response;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageBody {
    final String messageBody;

    public MessageBody(Stream<String> messageBody) {
        this.messageBody = messageBody.collect(Collectors.joining("\r\n"));
    }

    @Override
    public String toString() {
        return new String(messageBody);
    }

    public byte[] getMessageBody() {
        return messageBody.getBytes();
    }

    public int getContentLength(){
        return messageBody.getBytes().length;
    }
}
