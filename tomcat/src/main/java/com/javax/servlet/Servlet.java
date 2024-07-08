package com.javax.servlet;

import org.apache.coyote.http11.Request;
import org.apache.coyote.http11.Response;

public interface Servlet {
    void service(final Request request, final Response response) throws Exception;
}
