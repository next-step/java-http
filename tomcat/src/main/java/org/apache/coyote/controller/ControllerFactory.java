package org.apache.coyote.controller;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public interface ControllerFactory {

    HttpResponse serve(HttpRequest httpRequest);

}