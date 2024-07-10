package org.apache.catalina.servlets;

import com.javax.servlet.Servlet;
import org.apache.coyote.http.Request;
import org.apache.coyote.http.Response;
import org.apache.coyote.view.StaticResource;
import org.apache.coyote.view.StaticResourceResolver;

public class DefaultServlet implements Servlet {

    @Override
    public void service(final Request request, final Response response) throws Exception {
        serveResource(request, response);
    }

    private void serveResource(final Request request, final Response response) throws Exception {
        final StaticResource staticResource = StaticResourceResolver.findStaticResource(request.getPath());
        response.setBody(staticResource.getContent(), staticResource.getMimeType());
    }
}
