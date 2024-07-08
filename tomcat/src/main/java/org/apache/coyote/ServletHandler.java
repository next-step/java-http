package org.apache.coyote;

import com.javax.servlet.Servlet;

import java.util.regex.Pattern;

public class ServletHandler {

    private final String path;

    private final String regexPattern;

    private final Servlet servlet;

    public ServletHandler(final String path, final Servlet servlet) {
        this.path = path;
        this.regexPattern = path.replace("*", ".*");
        this.servlet = servlet;
    }

    public boolean match(final String requestUrl) {
        return Pattern.matches(regexPattern, requestUrl);
    }

    public Servlet getServlet() {
        return this.servlet;
    }
}
