package com.javax.servlet.http;

import com.javax.servlet.Servlet;
import org.apache.coyote.http.HttpMethod;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;
import org.apache.coyote.http.StatusCode;

public abstract class HttpServlet implements Servlet {
    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        if (httpRequest.getMethod() == HttpMethod.GET) {
            doGet(httpRequest, httpResponse);
            return;
        }

        if (httpRequest.getMethod() == HttpMethod.POST) {
            doPost(httpRequest, httpResponse);
            return;
        }
    }

    public void doGet(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        httpResponse.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        httpResponse.setBody("HTTP method GET not supported");
    }

    public void doPost(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        httpResponse.setStatusCode(StatusCode.METHOD_NOT_ALLOWED);
        httpResponse.setBody("HTTP method POST not supported");
    }
}
