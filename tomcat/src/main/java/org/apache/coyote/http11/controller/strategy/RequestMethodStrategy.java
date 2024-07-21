package org.apache.coyote.http11.controller.strategy;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public interface RequestMethodStrategy {

    boolean matched(String requestMethod);

    HttpResponse serve(HttpRequest httpRequest);
}
