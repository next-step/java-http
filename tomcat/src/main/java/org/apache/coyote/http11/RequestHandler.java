package org.apache.coyote.http11;


import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;


public interface RequestHandler {
    HttpResponse service(HttpRequest httpRequest) throws Exception;
}
