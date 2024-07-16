package org.apache.coyote.http11;


public interface RequestHandler {
    HttpResponse service(RequestLine requestLine);
}
