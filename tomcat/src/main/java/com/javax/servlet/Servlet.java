package com.javax.servlet;

import org.apache.coyote.Request;
import org.apache.coyote.Response;

public interface Servlet {
    void service(final Request request, final Response response) throws Exception;
}
