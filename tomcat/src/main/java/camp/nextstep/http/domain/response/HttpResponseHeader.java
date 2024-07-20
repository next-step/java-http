package camp.nextstep.http.domain.response;

import camp.nextstep.http.domain.HttpHeader;
import camp.nextstep.http.enums.ContentType;
import camp.nextstep.http.exception.ResourceNotFoundException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static camp.nextstep.http.domain.JSessionId.createJSessionIdStr;

public class HttpResponseHeader {
    public static final HttpResponseHeader EMPTY = new HttpResponseHeader();

    private static final String CONTENT_LENGTH_KEY = "Content-Length";
    private static final String CONTENT_TYPE_KEY = "Content-Type";
    private static final String CONTENT_TYPE_FORMAT = " %s;%s";
    private static final String DEFAULT_CHARSET = "charset=utf-8 ";
    private static final String HEADER_JOINER = ": ";
    private static final String LOCATION_KEY = "Location";

    private Map<String, String> headerMap;

    private HttpResponseHeader() {
    }

    private HttpResponseHeader(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public static HttpResponseHeader createResponseHeaderFromFile(File file) throws IOException {
        Map<String, String> headerMap = new HashMap<>();
        putContentTypeHeader(headerMap, file);
        putContentLengthHeader(headerMap, file);
        return new HttpResponseHeader(headerMap);
    }

    public static HttpResponseHeader createRedirectHeaderFromPath(String path) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(LOCATION_KEY, path);
        return new HttpResponseHeader(headerMap);
    }

    public static HttpResponseHeader createRedirectHeaderFromPathAndSetCookie(String path, String jSessionStr) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(LOCATION_KEY, path);

        Map<String, String> cookieHeader = HttpHeader.createNewCookieHeader(jSessionStr)
                .getHttpHeaders();

        cookieHeader.forEach((k, v) -> headerMap.put(k, v));
        return new HttpResponseHeader(headerMap);
    }

    public static HttpResponseHeader createResponseHeaderFromString(String string) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put(CONTENT_TYPE_KEY, String.format(CONTENT_TYPE_FORMAT, ContentType.TEXT_HTML.getContentTypeHeader(), DEFAULT_CHARSET));
        headerMap.put(CONTENT_LENGTH_KEY, String.valueOf(string.length()));
        return new HttpResponseHeader(headerMap);
    }

    private static void putContentTypeHeader(Map<String, String> headerMap, File file) {
        String fileExt = getExtensionByStringHandling(file.getName())
                .orElseThrow(() -> new ResourceNotFoundException("파일확장자 불명확 : " + file.getName()));
        ContentType contentType = ContentType.findContentTypeByFileExt(fileExt);

        headerMap.put(CONTENT_TYPE_KEY, String.format(CONTENT_TYPE_FORMAT, contentType.getContentTypeHeader(), DEFAULT_CHARSET));
    }

    private static void putContentLengthHeader(Map<String, String> headerMap, File file) throws IOException {
        String fileStr;
        try (final BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             final Stream<String> lines = bufferedReader.lines()) {
            fileStr = lines.collect(Collectors.joining("\r\n"));
        }
        headerMap.put(CONTENT_LENGTH_KEY, String.valueOf(fileStr.length()));
    }

    private static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public String headersToHeaderStr() {
        if (headerMap == null) {
            return null;
        }

        return headerMap.entrySet().stream()
                .map(v -> v.getKey().concat(HEADER_JOINER).concat(v.getValue()))
                .collect(Collectors.joining("\r\n"));
    }
}
