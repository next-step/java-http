package org.apache.catalina.servlets;

import com.javax.servlet.Servlet;
import org.apache.coyote.http11.Request;
import org.apache.coyote.http11.Response;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class DefaultServlet implements Servlet {

    @Override
    public void service(final Request request, final Response response) throws Exception {
        serveResource(request, response);
    }

    private void serveResource(final Request request, final Response response) throws Exception {
        response.setStaticResource(request.getPath());
    }
}
