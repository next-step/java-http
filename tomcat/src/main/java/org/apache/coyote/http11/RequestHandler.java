package org.apache.coyote.http11;

import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;

public interface RequestHandler {

    void handle(HttpRequest request, HttpResponse response) throws Exception;
}
