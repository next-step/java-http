package org.apache.coyote.http11.handler;

import java.io.IOException;
import org.apache.coyote.http11.meta.HttpPath;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;
import org.apache.utils.FileUtils;

public class StaticRequestHandler implements RequestHandler {

    @Override
    public Response handle(Request request) throws IOException {
        RequestLine requestLine = request.getRequestLine();
        HttpPath path = requestLine.getPath();
        String responseBody = FileUtils.getStaticFileContent(path);
        if (responseBody.isEmpty()) {
            return Response.notFound(ContentType.from(path.getExtension()), FileUtils.getStaticFileContent(HttpPath.from(NOT_FOUND_PATH)));
        }
        return Response.ok(ContentType.from(path.getExtension()), responseBody);
    }
}
