package org.apache.coyote.http11;

import org.apache.coyote.*;
import org.apache.coyote.handler.DefaultHandler;
import org.apache.coyote.handler.Handler;
import org.apache.coyote.handler.NotSupportHandlerException;
import org.apache.coyote.handler.ResourceHandler;

import java.util.List;

public class Http11ProcessorHandlers {

    private final List<Handler> handlers = List.of(new ResourceHandler(), new DefaultHandler());

    public HttpResponse handle(HttpRequest request) {
        HttpResponse response = null;
        for (Handler handler : handlers) {
            response = handle(handler, request);
            if (response != null) break;
        }
        return response;
    }

    private HttpResponse handle(Handler handler, HttpRequest request) {
        try {
            return handler.handle(request);
        } catch (NotSupportHandlerException ignored) {
            return null;
        }
    }
}
