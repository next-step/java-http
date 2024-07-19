package org.apache.coyote.http11.handler;

import org.apache.coyote.http11.RequestHandler;
import org.apache.coyote.http11.constants.HttpStatus;
import org.apache.coyote.http11.model.ContentType;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringJoiner;

public class DefaultHandler implements RequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        response.setStatus(HttpStatus.SUCCESS);
        response.setContent("Hello World!");
        response.setContentType(ContentType.TEXT_HTML);
        response.send();
    }

    private String readHttpRequestMessage(final BufferedReader br) throws IOException {
        StringJoiner sj = new StringJoiner("\n");
        while (true) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            sj.add(line);
        }
        return sj.toString();
    }

}
