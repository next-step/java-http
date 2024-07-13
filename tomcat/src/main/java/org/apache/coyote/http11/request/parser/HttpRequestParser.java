package org.apache.coyote.http11.request.parser;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.model.*;

import java.io.BufferedReader;
import java.util.List;

public class HttpRequestParser {
    public static HttpRequest parse(BufferedReader bufferedReader) {
        List<String> requestLines = bufferedReader.lines().takeWhile(line -> !line.isEmpty()).toList();
        if(requestLines.isEmpty()) {
            throw new IllegalArgumentException("Request headers are empty");
        }
        RequestLine requestLine = RequestLineParser.parse(requestLines.get(0));
        RequestHeaders requestHeaders = RequestHeadersParser.parse(requestLines);
        Cookies cookies = CookiesParser.parse(requestHeaders.requestHeaders());
        if (requestHeaders.hasContentLength()) {
            int contentLength = requestHeaders.getContentLength();
            StringBuilder body = new StringBuilder();
            for (int i = 0; i < contentLength; i++) {
                try {
                    body.append((char) bufferedReader.read());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            RequestBodies requestBodies = RequestBodiesParser.parse(body.toString());
            return new HttpRequest(requestLine, requestHeaders, requestBodies, cookies);
        }
        return new HttpRequest(requestLine, requestHeaders, RequestBodies.emptyRequestBodies(), cookies);
    }
}
