package org.apache.coyote.http11.request.handler;

import org.apache.coyote.http11.model.HttpRequestHeader;

import java.io.IOException;

@FunctionalInterface
public interface RequestHandler {
    String handle(HttpRequestHeader requestHeader) throws IOException;
}
