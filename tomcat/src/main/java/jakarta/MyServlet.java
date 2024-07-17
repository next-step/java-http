package jakarta;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

import java.io.IOException;

public interface MyServlet {
    void delegate(HttpRequest request, HttpResponse response) throws IOException;
}
