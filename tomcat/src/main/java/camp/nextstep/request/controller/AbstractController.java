package camp.nextstep.request.controller;

import org.apache.coyote.http11.model.HttpRequest;
import org.apache.coyote.http11.model.HttpResponse;
import org.apache.coyote.http11.model.constant.HttpMethod;
import org.apache.coyote.http11.model.constant.HttpStatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

public abstract class AbstractController implements Controller {

    private static final String STATIC_PATH = "static";
    private static final String NOT_FOUND_PATH = "/404.html";

    @Override
    public String handle(HttpRequest request) throws IOException {
        try {
            return service(request).buildResponse();
        } catch (Exception e) {
            return new HttpResponse(HttpStatusCode.INTERNAL_SERVER_ERROR, request.httpRequestHeader()).buildResponse();
        }
    }

    @Override
    public HttpResponse service(HttpRequest request) throws Exception {
        final HttpMethod httpMethod = request.httpRequestHeader()
                .requestLine()
                .httpMethod();

        if (httpMethod.isGetMethod()) {
            return doGet(request);
        }

        if (httpMethod.isPostMethod()) {
            return doPost(request);
        }

        return notFindMethod(request);
    }

    private HttpResponse notFindMethod(HttpRequest request) {
        final HttpResponse httpResponse = new HttpResponse(HttpStatusCode.FOUND, request.httpRequestHeader());
        httpResponse.addLocationHeader(NOT_FOUND_PATH);
        return httpResponse;
    }

    protected String buildBodyFromReadFile(final String pathString) throws IOException {
        final URL path = getClass().getClassLoader().getResource(STATIC_PATH + pathString);
        final File html = new File(path.getFile());

        return new String(Files.readAllBytes(html.toPath()));
    }

    protected HttpResponse buildOkHttpResponse(final HttpRequest request, final String body) {
        return new HttpResponse(HttpStatusCode.OK, request.httpRequestHeader(), body);
    }

    protected HttpResponse buildRedirectHttpResponse(final HttpRequest request, final String location) {
        final HttpResponse httpResponse = new HttpResponse(HttpStatusCode.FOUND, request.httpRequestHeader());
        httpResponse.addLocationHeader(location);

        return httpResponse;
    }

    protected HttpResponse buildRedirectSetCookieHttpResponse(final HttpRequest request, final String location, final String cookie) {
        final HttpResponse httpResponse = new HttpResponse(HttpStatusCode.FOUND, request.httpRequestHeader());
        httpResponse.addLocationHeader(location);
        httpResponse.addJSessionId(cookie);

        return httpResponse;
    }

    protected abstract HttpResponse doGet(HttpRequest request) throws Exception;

    protected abstract HttpResponse doPost(HttpRequest request) throws Exception;
}
