package camp.nextstep.controller;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;
import javassist.NotFoundException;

public class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if (request.isGET()) doGet(request, response);

        if (request.isPOST()) doPost(request, response);
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        throw new NotFoundException("GET 요청에는 응답할 수 없습니다");
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        throw new NotFoundException("POST 요청에는 응답할 수 없습니다");
    }
}
