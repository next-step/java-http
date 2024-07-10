package org.apache.coyote.http11.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

public class RequestBody extends Parameters {

    private RequestBody(Map<String, Object> parameters) {
        super(parameters);
    }

    public static RequestBody from(BufferedReader br, int contentLength) throws IOException {
        char[] requestBodyChars = new char[contentLength];
        br.read(requestBodyChars, 0, contentLength);
        String requestString = new String(requestBodyChars);
        return new RequestBody(parseParameters(requestString));
    }
}
