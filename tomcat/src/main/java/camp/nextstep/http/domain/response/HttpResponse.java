package camp.nextstep.http.domain.response;

import camp.nextstep.http.enums.HttpStatus;

import java.io.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static camp.nextstep.http.domain.StaticResource.createResourceFromPath;
import static camp.nextstep.http.domain.response.HttpResponseHeader.createRedirectHeaderFromPath;
import static camp.nextstep.http.domain.response.HttpResponseHeader.createResponseHeaderFromString;

public class HttpResponse {
    private HttpResponseStartLine httpResponseStartLine;
    private HttpResponseHeader httpResponseHeader;
    private HttpResponseBody httpResponseBody;

    public HttpResponse() {
        this.httpResponseHeader = HttpResponseHeader.EMPTY;
        this.httpResponseBody = new EmptyResponseBody();
    }

    public String getResponseStr() {
        return Stream.of(
                httpResponseStartLine.getResponseStartLine(),
                httpResponseHeader.headersToHeaderStr(),
                "",
                httpResponseBody.getBodyString()
        )
                .filter(v -> v != null)
                .collect(Collectors.joining("\r\n"));
    }

    public void redirectResponseByPath(String path) {
        this.httpResponseStartLine = HttpResponseStartLine.createRedirectStartLine();
        this.httpResponseHeader = createRedirectHeaderFromPath(path);
    }

    public void redirectResponseByPathAndSetCookie(String path, String jSessionStr) {
        this.httpResponseStartLine = HttpResponseStartLine.createRedirectStartLine();
        this.httpResponseHeader = HttpResponseHeader.createRedirectHeaderFromPathAndSetCookie(path, jSessionStr);
    }

    public void successResponseByFile(File file) {
        httpResponseStartLine = HttpResponseStartLine.createSuccessStartLine();
        setResponseByFile(file);
    }

    public void notFoundResponseByFile(File file) {
        httpResponseStartLine = HttpResponseStartLine.createNotFoundStartLine();
        setResponseByFile(file);
    }

    private void setResponseByFile(File file) {
        try {
            this.httpResponseHeader = HttpResponseHeader.createResponseHeaderFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.httpResponseBody = new FileResponseBody(file);
    }

    public void badRequestResponse(ClassLoader classLoader) {
        File notFoundFile = createResourceFromPath("/401.html", classLoader).getResourceFile();
        this.httpResponseStartLine = HttpResponseStartLine.createBadRequestStartLine();
        setResponseByFile(notFoundFile);
    }

    public void badRequestResponseByString() {
        this.httpResponseStartLine = HttpResponseStartLine.createBadRequestStartLine();
        responseByString(HttpStatus.BAD_REQUEST.getMessage());
    }

    public void responseByString(String responseBody) {
        this.httpResponseHeader = createResponseHeaderFromString(responseBody);
        this.httpResponseBody = new StringResponseBody(responseBody);
    }

    public void successResponseByString(String responseBody) {
        this.httpResponseStartLine = HttpResponseStartLine.createSuccessStartLine();
        responseByString(responseBody);
    }

    public void internalServerErrorResponseByString() {
        this.httpResponseStartLine = HttpResponseStartLine.createBadRequestStartLine();
        responseByString(HttpStatus.BAD_REQUEST.getMessage());
    }
}
