package camp.nextstep.handler;

import org.apache.coyote.http11.RequestHandler;
import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;

public abstract class AbstractController implements RequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws Exception {
        switch(request.getHttpMethod()) {
            case GET -> doGet(request, response);
            case POST -> doPost(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws Exception { /* NOOP */ }
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception { /* NOOP */ }
}
