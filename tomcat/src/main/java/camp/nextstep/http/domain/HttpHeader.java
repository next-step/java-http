package camp.nextstep.http.domain;

import camp.nextstep.http.enums.ContentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static camp.nextstep.http.enums.ContentType.findContentTypeByContentTypeHeader;

public class HttpHeader {
    private Map<String, String> httpHeaders;
    private HttpCookie httpCookie;

    public static final String SET_COOKIE_HEADER = "Set-Cookie";
    private static final String CONTENT_LENGTH_HEADER = "Content-Length";
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final Pattern HEADER_SEPARATOR = Pattern.compile(":");
    private static final int HEADER_KEY_INDEX = 0;
    private static final int HEADER_VALUE_INDEX = 1;

    private HttpHeader(
            Map<String, String> httpHeaders,
            HttpCookie httpCookie
    ) {
        this.httpHeaders = httpHeaders;
        this.httpCookie = httpCookie;
    }

    public static HttpHeader createHeadersFromReader(
            BufferedReader bufferedReader
    ) throws IOException {
        String s;
        Map<String, String> httpHeaders = new HashMap<>();
        HttpCookie httpCookie = null;
        while ((s = bufferedReader.readLine()) != null) {
            if (s.isEmpty()) {
                break;
            }
            String[] header = HEADER_SEPARATOR.split(s);
            httpHeaders.put(
                    header[HEADER_KEY_INDEX].trim(),
                    header[HEADER_VALUE_INDEX].trim()
            );

            if (header[HEADER_KEY_INDEX].equals(HttpCookie.COOKIE_KEY)) {
                httpCookie = HttpCookie.createHttpCookie(header[HEADER_VALUE_INDEX]);
            }
        }
        return new HttpHeader(httpHeaders, httpCookie);
    }

    public static HttpHeader createNewCookieHeader(String cookie) {
        HttpCookie httpCookie = HttpCookie.createHttpCookie(cookie);
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put(SET_COOKIE_HEADER, httpCookie.getCookie());
        return new HttpHeader(httpHeaders, httpCookie);
    }

    public boolean hasContentLength() {
        return httpHeaders.containsKey(CONTENT_LENGTH_HEADER);
    }

    public int getContentLength() {
        return Integer.parseInt(httpHeaders.get(CONTENT_LENGTH_HEADER));
    }

    public ContentType getContentType() {
        return findContentTypeByContentTypeHeader(httpHeaders.get(CONTENT_TYPE_HEADER));
    }

    public Map<String, String> getHttpHeaders() {
        return httpHeaders;
    }
}
