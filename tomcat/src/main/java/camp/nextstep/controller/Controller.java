package camp.nextstep.controller;

import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Controller {

    Logger log = LoggerFactory.getLogger(Controller.class);

    void service(Request request, Response response) throws Exception;
}
