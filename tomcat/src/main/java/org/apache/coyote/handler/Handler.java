package org.apache.coyote.handler;

import org.apache.coyote.HttpRequest;
import org.apache.coyote.HttpResponse;

public interface Handler {
    HttpResponse handle(final HttpRequest request);
}
