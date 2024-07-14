package org.apache.catalina.connector;

import com.javax.servlet.Servlet;
import org.apache.coyote.http.Cookie;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;

public class CoyoteAdapter {

    private final ServletMapping servletMapping = new ServletMapping();

    public void addServlet(final String mapping, final Servlet servlet) {
        this.servletMapping.addServlet(mapping, servlet);
    }

    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        final Servlet servlet = servletMapping.getServlet(httpRequest);

        servlet.service(httpRequest, httpResponse);

        if (httpRequest.hasNotSessionId()) {
            httpResponse.addCookie(Cookie.createSessionCookie());
        }
    }

}
