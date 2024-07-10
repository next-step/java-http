package camp.nextstep.http.domain;

import java.io.IOException;

public abstract class AbstractController implements Controller {
    @Override
    public void service(final HttpRequest request, final HttpResponse response) throws IOException {
        if (request.isGetMethod()) {
            doGet(request, response);
            return;
        }

        doPost(request, response);
    }

    protected void doPost(final HttpRequest request, final HttpResponse response) throws IOException { /* NOOP */ }

    protected void doGet(final HttpRequest request, final HttpResponse response) throws IOException { /* NOOP */ }
}
