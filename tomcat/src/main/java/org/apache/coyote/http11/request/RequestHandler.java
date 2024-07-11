package org.apache.coyote.http11.request;

import org.apache.coyote.http11.model.HttpRequest;

import java.io.IOException;

@FunctionalInterface
public interface RequestHandler {
    String handle(HttpRequest request) throws IOException;
}
