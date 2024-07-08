package org.apache.catalina.connector;

import com.javax.servlet.Servlet;
import org.apache.coyote.http11.Request;
import org.apache.coyote.http11.Response;

public class CoyoteAdapter {

    private final ServletMapping servletMapping = new ServletMapping();

    public void addServlet(final String mapping, final Servlet servlet) {
        this.servletMapping.addServlet(mapping, servlet);
    }

    public void service(final Request request, final Response response) throws Exception {
        final Servlet servlet = servletMapping.getServlet(request);

        try {
            servlet.service(request, response);
        } catch (NullPointerException e) {
            response.notFound();
        }
    }

}
