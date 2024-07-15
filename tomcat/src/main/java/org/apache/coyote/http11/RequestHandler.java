package org.apache.coyote.http11;


public interface RequestHandler {
    Response service(RequestLine requestLine);
}
