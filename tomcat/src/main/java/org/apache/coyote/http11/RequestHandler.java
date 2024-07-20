package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;

import java.io.IOException;

public interface RequestHandler {

    void handle(HttpRequest request, HttpResponse response) throws IOException;
}
