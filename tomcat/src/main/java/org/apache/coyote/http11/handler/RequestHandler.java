package org.apache.coyote.http11.handler;

import java.io.IOException;
import org.apache.coyote.http11.request.HttpPath;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.response.Response;
import org.apache.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface RequestHandler {

    String UNAUTHORIZED_PATH = "/401.html";
    String NOT_FOUND_PATH = "/404.html";
    String INDEX_PATH = "/index.html";

     Logger log = LoggerFactory.getLogger(RequestHandler.class);


    Response handle(Request request) throws IOException;

    default Response notFound() throws IOException {
        return Response.notFound(FileUtils.getStaticFileContent(HttpPath.from(NOT_FOUND_PATH)));
    }

    default Response unauthorized() throws IOException {
        return Response.unauthorized(FileUtils.getStaticFileContent(HttpPath.from(UNAUTHORIZED_PATH)));
    }
}
