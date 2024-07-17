package camp.nextstep.controller;

import org.apache.coyote.request.HttpRequest;
import org.apache.coyote.response.FileFinder;
import org.apache.coyote.response.HttpResponse;
import org.apache.coyote.response.HttpStatus;
import org.apache.coyote.response.MimeType;

public class PathController extends AbstractController {
    @Override
    protected HttpResponse doGet(HttpRequest request) {
        String httpPath = request.getHttpPath();
        return new HttpResponse(
                HttpStatus.OK,
                MimeType.from(httpPath),
                FileFinder.find(httpPath));

    }
}
