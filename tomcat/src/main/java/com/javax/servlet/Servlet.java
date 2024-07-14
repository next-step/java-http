package com.javax.servlet;

import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;

public interface Servlet {
    void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception;
}
