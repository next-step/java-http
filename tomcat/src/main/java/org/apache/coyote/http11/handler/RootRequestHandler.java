package org.apache.coyote.http11.handler;

import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;

public class RootRequestHandler implements RequestHandler {
    private static final String ROOT_PATH_BODY = "Hello world!";

    @Override
    public Response handle(Request request) {
        return Response.ok(ContentType.HTML, ROOT_PATH_BODY);
    }
}
