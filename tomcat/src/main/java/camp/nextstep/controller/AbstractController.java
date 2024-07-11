package camp.nextstep.controller;

import camp.nextstep.request.HttpRequest;
import camp.nextstep.response.HttpResponse;
import javassist.NotFoundException;
import org.apache.coyote.http11.SessionManager;

public class AbstractController implements Controller {

    private final SessionManager sessionManager;

    public AbstractController() {
        this.sessionManager = SessionManager.INSTANCE;
    }

    @Override
    public void service(HttpRequest request, HttpResponse response) throws Exception {
        if (request.isGET()) doGet(request, response);

        if (request.isPOST()) doPost(request, response);
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
        throw new NotFoundException("POST 요청에는 응답할 수 없습니다");
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        throw new NotFoundException("GET 요청에는 응답할 수 없습니다");
    }

    protected SessionManager getSessionManager() {
        return sessionManager;
    }
}
