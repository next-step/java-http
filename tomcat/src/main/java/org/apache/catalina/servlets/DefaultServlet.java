package org.apache.catalina.servlets;

import com.javax.servlet.Servlet;
import org.apache.coyote.Request;
import org.apache.coyote.Response;

public class DefaultServlet implements Servlet {

    @Override
    public void service(final Request request, final Response response) throws Exception {
        serveResource(request, response);
    }

    private void serveResource(final Request request, final Response response) throws Exception {
        response.setStaticResource(request.getPath());
    }
}
