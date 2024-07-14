package camp.nextstep.controller;

import org.apache.coyote.http11.meta.HttpPath;
import org.apache.coyote.http11.request.Request;
import org.apache.coyote.http11.request.RequestLine;
import org.apache.coyote.http11.response.ContentType;
import org.apache.coyote.http11.response.Response;
import org.apache.utils.FileUtils;

public abstract class AbstractController implements Controller {

    protected static final String NOT_FOUND_PATH = "/404.html";

    @Override
    public void service(Request request, Response response) throws Exception {
        RequestLine requestLine = request.getRequestLine();
        if (requestLine.isGet()) {
            doGet(request, response);
        }

        if (requestLine.isPost()) {
            doPost(request, response);
        }
    }

    protected abstract void doPost(Request request, Response response) throws Exception;

    protected void doGet(Request request, Response response) throws Exception {
        RequestLine requestLine = request.getRequestLine();
        HttpPath path = requestLine.getPath();
        String responseBody = FileUtils.getStaticFileContent(path);
        if (responseBody.isEmpty()) {
            response.notFound(ContentType.from(path.getExtension()), FileUtils.getStaticFileContent(HttpPath.from(NOT_FOUND_PATH)));
        }
        response.ok(ContentType.from(path.getExtension()), responseBody);
    }
}
