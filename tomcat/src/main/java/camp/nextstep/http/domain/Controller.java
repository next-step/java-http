package camp.nextstep.http.domain;

import java.io.IOException;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws IOException;
}
