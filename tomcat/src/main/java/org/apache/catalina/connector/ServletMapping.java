package org.apache.catalina.connector;

import com.javax.servlet.Servlet;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.coyote.ServletHandler;
import org.apache.coyote.http.HttpRequest;

import java.util.*;

public class ServletMapping {

    public ServletMapping() {
        this.addServlet("/*", new DefaultServlet());
    }

    private final List<ServletHandler> servletHandlers = new ArrayList<>();

    public void addServlet(final String mapping, final Servlet servlet) {
        this.servletHandlers.add(0, new ServletHandler(mapping, servlet));
    }

    public Servlet getServlet(final HttpRequest httpRequest) {
        return this.servletHandlers.stream()
                .filter(servletHandler -> servletHandler.match(httpRequest.getPath()))
                .findFirst()
                .map(ServletHandler::getServlet)
                .orElse(null);
    }
}
