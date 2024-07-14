package camp.nextstep.controller;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class IndexController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(IndexController.class);

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
        log.debug("세션 조회: {}", request.getSession());

        response.render(request.getPath());
    }
}
