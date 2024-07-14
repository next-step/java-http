package org.apache.catalina.servlets;

import com.javax.servlet.Servlet;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;
import org.apache.coyote.view.StaticResource;
import org.apache.coyote.view.StaticResourceResolver;

public class DefaultServlet implements Servlet {

    @Override
    public void service(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        serveResource(httpRequest, httpResponse);
    }

    private void serveResource(final HttpRequest httpRequest, final HttpResponse httpResponse) throws Exception {
        final StaticResource staticResource = StaticResourceResolver.findStaticResource(httpRequest.getPath());
        httpResponse.setBody(staticResource.getContent(), staticResource.getMimeType());
    }
}
