package camp.nextstep.request.controller;

import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;
import org.apache.coyote.http11.request.RequestHandler;

import java.io.IOException;

public interface Controller extends RequestHandler {

    @Override
    String handle(HttpRequest request) throws IOException;

    HttpResponse service(HttpRequest request) throws Exception;

    String url();
}
