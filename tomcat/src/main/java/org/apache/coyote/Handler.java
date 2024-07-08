package org.apache.coyote;

public interface Handler {
    HttpResponse handle(final HttpRequest request);
}
