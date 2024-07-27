package org.apache.coyote.http11.response;

public class MessageBody {
    final byte[] messageBody;

    public MessageBody(byte[] messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return new String(messageBody);
    }

    public byte[] getMessageBody() {
        return messageBody;
    }
}
