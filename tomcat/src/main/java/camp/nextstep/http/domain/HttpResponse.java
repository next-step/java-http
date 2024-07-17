package camp.nextstep.http.domain;

import camp.nextstep.http.enums.ContentType;
import camp.nextstep.http.exception.ResourceNotFoundException;

import java.io.*;
import java.nio.file.Files;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HttpResponse {
    private static final String SUCCESS_HEADER = "HTTP/1.1 200 OK ";
    private static final String NOTFOUND_HEADER = "HTTP/1.1 404 OK ";
    private static final String DEFAULT_CHARSET = "charset=utf-8 ";
    private static final String CONTENT_TYPE_FORMAT = "Content-Type: %s;%s";
    private static final String CONTENT_LENGTH_FORMAT = "Content-Length: %s ";

    private String responseStr;

    private HttpResponse(String responseStr) {
        this.responseStr = responseStr;
    }

    public String getResponseStr() {
        return responseStr;
    }

    public static HttpResponse createResponseByFile(File file) throws IOException {
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

        final var response = String.join(System.lineSeparator(),
                SUCCESS_HEADER,
                contentTypeStr,
                getContentLengthHeader(fileStr),
                "",
                fileStr);

        return new HttpResponse(response);
    }

    public static HttpResponse createResponseByString(String responseBody) {
        String contentTypeStr = getContentTypeHeader(
                ContentType.TEXT_HTML,
                DEFAULT_CHARSET
        );

        return createResponse(
            contentTypeStr,
            responseBody,
            SUCCESS_HEADER
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
            NOTFOUND_HEADER
        );
    }

    private static HttpResponse createResponse(
        String contentTypeStr,
        String responseBody,
        String header
    ) {
        final var response = String.join(System.lineSeparator(),
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
}
