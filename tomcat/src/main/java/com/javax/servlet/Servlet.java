package com.javax.servlet;

import org.apache.coyote.http.Request;
import org.apache.coyote.http.Response;

public interface Servlet {
    void service(final Request request, final Response response) throws Exception;
}
