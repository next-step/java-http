package camp.nextstep.http.domain.response;

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

    private HttpResponse(
            HttpResponseStartLine httpResponseStartLine,
            HttpResponseHeader httpResponseHeader,
            HttpResponseBody httpResponseBody
    ) {
        this.httpResponseStartLine = httpResponseStartLine;
        this.httpResponseHeader = httpResponseHeader;
        this.httpResponseBody = httpResponseBody;
    }

    private HttpResponse(
            HttpResponseStartLine httpResponseStartLine,
            HttpResponseHeader httpResponseHeader
    ) {
        this.httpResponseStartLine = httpResponseStartLine;
        this.httpResponseHeader = httpResponseHeader;
        this.httpResponseBody = new EmptyResponseBody();
    }

    private static HttpResponse createResponseByFile(HttpResponseStartLine httpResponseStartLine, File file) {
        HttpResponseHeader httpResponseHeader = null;
        try {
            httpResponseHeader = HttpResponseHeader.createResponseHeaderFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpResponseBody httpResponseBody = new FileResponseBody(file);

        return new HttpResponse(
                httpResponseStartLine,
                httpResponseHeader,
                httpResponseBody
        );
    }

    public static HttpResponse createSuccessResponseByFile(File file) {
        HttpResponseStartLine httpResponseStartLine = HttpResponseStartLine.createSuccessStartLine();
        return createResponseByFile(httpResponseStartLine, file);
    }

    public static HttpResponse createRedirectResponseByPath(String path) {
        HttpResponseStartLine httpResponseStartLine = HttpResponseStartLine.createRedirectStartLine();
        HttpResponseHeader httpResponseHeader = createRedirectHeaderFromPath(path);
        return new HttpResponse(httpResponseStartLine, httpResponseHeader);
    }

    public static HttpResponse createRedirectResponseByPathAndSetCookie(String path, String jSessionStr) {
        HttpResponseStartLine httpResponseStartLine = HttpResponseStartLine.createRedirectStartLine();
        HttpResponseHeader httpResponseHeader = HttpResponseHeader.createRedirectHeaderFromPathAndSetCookie(path, jSessionStr);
        return new HttpResponse(httpResponseStartLine, httpResponseHeader);
    }

    public static HttpResponse createBadRequestResponse(ClassLoader classLoader) {
        File notFoundFile = createResourceFromPath("/401.html", classLoader).getResourceFile();
        HttpResponseStartLine httpResponseStartLine = HttpResponseStartLine.createBadRequestStartLine();
        return createResponseByFile(httpResponseStartLine, notFoundFile);
    }

    public static HttpResponse createSuccessResponseByString(String responseBody) {
        HttpResponseStartLine httpResponseStartLine = HttpResponseStartLine.createSuccessStartLine();
        return createResponseByString(httpResponseStartLine, responseBody);
    }

    public static HttpResponse createNotFoundResponseByString() {
        HttpResponseStartLine httpResponseStartLine = HttpResponseStartLine.createNotFoundStartLine();
        String responseBody = "NOT FOUND";
        return createResponseByString(httpResponseStartLine, responseBody);
    }

    public static HttpResponse createBadRequestResponseByString() {
        HttpResponseStartLine httpResponseStartLine = HttpResponseStartLine.createBadRequestStartLine();
        String responseBody = "BAD REQUEST";
        return createResponseByString(httpResponseStartLine, responseBody);
    }

    public static HttpResponse createResponseByString(HttpResponseStartLine httpResponseStartLine, String responseBody) {
        HttpResponseHeader httpResponseHeader = createResponseHeaderFromString(responseBody);
        HttpResponseBody httpResponseBody = new StringResponseBody(responseBody);
        return new HttpResponse(
                httpResponseStartLine,
                httpResponseHeader,
                httpResponseBody
        );
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
}
