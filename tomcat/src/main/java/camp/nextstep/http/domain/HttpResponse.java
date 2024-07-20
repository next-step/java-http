package camp.nextstep.http.domain;

import camp.nextstep.http.enums.ContentType;
import camp.nextstep.http.exception.ResourceNotFoundException;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static camp.nextstep.http.domain.StaticResource.createResourceFromPath;

public class HttpResponse {
    private static final String SUCCESS_RESPONSE_START_LINE = "HTTP/1.1 200 OK ";
    private static final String NOTFOUND_RESPONSE_START_LINE = "HTTP/1.1 404 NOT FOUND ";
    private static final String REDIRECT_RESPONSE_START_LINE = "HTTP/1.1 302 FOUND ";
    private static final String BAD_REQUEST_RESPONSE_START_LINE = "HTTP/1.1 401 BAD REQUEST ";
    private static final String DEFAULT_CHARSET = "charset=utf-8 ";
    private static final String CONTENT_TYPE_FORMAT = "Content-Type: %s;%s";
    private static final String CONTENT_LENGTH_FORMAT = "Content-Length: %s ";
    private static final String LOCATION_FORMAT = "Location: %s ";
    private static final String HEADER_JOINER = ": ";

    private String responseStr;

    private HttpResponse(String responseStr) {
        this.responseStr = responseStr;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public static HttpResponse createSuccessResponseByFile(File file) {
        try {
            return createResponseByFileAndHeader(
                    file,
                    SUCCESS_RESPONSE_START_LINE,
                    null
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            return createNotFoundResponseByString();
        }
    }

    public static HttpResponse createRedirectResponseByPath(String path) {
        String responseBody = String.format(LOCATION_FORMAT, path);
        final var response = String.join("\r\n",
                REDIRECT_RESPONSE_START_LINE,
                responseBody);

        return new HttpResponse(response);
    }

    public static HttpResponse createRedirectResponseByPathWithHeader(
            String path,
            Map<String, String> headers
    ) {
        String responseBody = String.format(LOCATION_FORMAT, path);
        final var response = String.join("\r\n",
                REDIRECT_RESPONSE_START_LINE,
                headersToHeaderStr(headers),
                responseBody);

        return new HttpResponse(response);
    }

    public static HttpResponse createBadRequestResponse(ClassLoader classLoader) {
        try {
            File notFoundFile = createResourceFromPath("/401.html", classLoader).getResourceFile();
            return createResponseByFileAndHeader(
                    notFoundFile,
                    BAD_REQUEST_RESPONSE_START_LINE,
                    null
            );
        } catch (IOException ex) {
            ex.printStackTrace();
            return createNotFoundResponseByString();
        }
    }

    public static HttpResponse createResponseByString(String responseBody) {
        String contentTypeStr = getContentTypeHeader(
                ContentType.TEXT_HTML,
                DEFAULT_CHARSET
        );

        return createResponse(
            contentTypeStr,
            responseBody,
                SUCCESS_RESPONSE_START_LINE
        );
    }

    public static HttpResponse createNotFoundResponseByString() {
        String responseBody = "NOT FOUND";
        String contentTypeStr = getContentTypeHeader(
            ContentType.TEXT_HTML,
            DEFAULT_CHARSET
        );

        return createResponse(
            contentTypeStr,
            responseBody,
                NOTFOUND_RESPONSE_START_LINE
        );
    }

    public static HttpResponse createBadRequestResponseByString() {
        String responseBody = "BAD REQUEST";
        String contentTypeStr = getContentTypeHeader(
                ContentType.TEXT_HTML,
                DEFAULT_CHARSET
        );

        return createResponse(
                contentTypeStr,
                responseBody,
                BAD_REQUEST_RESPONSE_START_LINE
        );
    }

    private static HttpResponse createResponseByFileAndHeader(
            File file,
            String responseStartLine,
            Map<String, String> headers
    ) throws IOException {
        String fileExt = getExtensionByStringHandling(file.getName())
                .orElseThrow(() -> new ResourceNotFoundException("파일확장자 불명확 : " + file.getName()));

        ContentType contentType = ContentType.findContentTypeByFileExt(fileExt);

        String fileStr;
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             final Stream<String> lines = bufferedReader.lines()) {
            fileStr = lines.collect(Collectors.joining("\r\n"));
        }

        String contentTypeStr = getContentTypeHeader(
                contentType,
                DEFAULT_CHARSET
        );

        if (headers == null) {
            final var response = String.join("\r\n",
                    responseStartLine,
                    contentTypeStr,
                    getContentLengthHeader(fileStr),
                    "",
                    fileStr);
            return new HttpResponse(response);
        }

        final var response = String.join("\r\n",
                responseStartLine,
                headersToHeaderStr(headers),
                contentTypeStr,
                getContentLengthHeader(fileStr),
                "",
                fileStr);

        return new HttpResponse(response);
    }

    private static HttpResponse createResponse(
        String contentTypeStr,
        String responseBody,
        String header
    ) {
        final var response = String.join("\r\n",
            header,
            contentTypeStr,
            getContentLengthHeader(responseBody),
            "",
            responseBody);

        return new HttpResponse(response);
    }

    private static String getContentTypeHeader(ContentType contentType, String charSet) {
        return String.format(CONTENT_TYPE_FORMAT, contentType.getContentTypeHeader(), charSet);
    }

    private static String getContentLengthHeader(String responseBody) {
        return String.format(CONTENT_LENGTH_FORMAT, responseBody.length());
    }

    private static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private static String headersToHeaderStr(Map<String, String> headers) {
        return headers.entrySet().stream()
                .map(v -> v.getKey().concat(HEADER_JOINER).concat(v.getValue()))
                .collect(Collectors.joining("\r\n"));

    }
}
