package camp.nextstep.controller;

import camp.nextstep.http.domain.HttpRequest;
import camp.nextstep.http.domain.HttpResponse;

import java.io.IOException;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws IOException;
}
