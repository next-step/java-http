package org.apache.coyote.http11;


public class RequestMapping {

    public RequestHandler getHandler(RequestLine requestLine) {

        if (FileLoader.isStaticResource(requestLine.getPath())) {
            return new StaticResourceRequestHandler();
        }
        return new ApplicationRequestHandler();
    }
}
