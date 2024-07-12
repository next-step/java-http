package camp.nextstep.controller;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;
import org.apache.coyote.http11.Http11Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IndexController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        response.renderStaticResource(request.getPath());
        log.debug("세션 조회: {}", request.getSession());
    }
}
