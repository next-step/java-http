package org.apache.coyote.http11.request;

import org.apache.coyote.http11.request.model.RequestBodies;
import org.apache.coyote.http11.request.model.RequestHeader;
import org.apache.coyote.http11.request.model.RequestLine;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestHeaderParser {
    public static RequestHeader parse(BufferedReader bufferedReader, List<String> requestHeaders) {
        RequestLine requestLine = RequestLineParser.parse(requestHeaders.get(0));

        Map<String, Object> headers = new HashMap<>();
        for (int i = 1; i < requestHeaders.size(); i++) {
            String[] header = requestHeaders.get(i).split(": ", 2);
            headers.put(header[0], header[1]);
        }

        if(headers.containsKey("Content-Length")) {
            int contentLength = Integer.parseInt((String) headers.get("Content-Length"));
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < contentLength; i++) {
                try {
                    body.append((char) bufferedReader.read());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            RequestBodies requestBodies = RequestBodiesParser.parse(body.toString());
            return new RequestHeader(requestLine, headers, requestBodies);
        }
        return new RequestHeader(requestLine, headers, RequestBodies.emptyRequestBodies());
    }
}
