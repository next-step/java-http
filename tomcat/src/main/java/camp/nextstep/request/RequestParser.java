package camp.nextstep.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestParser {
    private static final String REQUEST_LINE_REGEX_SEPARATOR = " ";
    private static final String QUERY_STRING_REGEX_SEPARATOR = "\\?";
    private static final String QUERY_PARAMS_REGEX_SEPARATOR = "&";
    private static final String QUERY_PARAMS_KEY_VALUE_REGEX_SEPARATOR = "=";
    private static final String COOKIE_KEY_VALUE_REGEX_SEPARATOR = "=";
    private static final String COOKIES_REGEX_SEPARATOR = "; ";

    public Request parse(BufferedReader bufferedReader) throws IOException {
        String requestLineString = extractRequestLine(bufferedReader);
        RequestLine requestLine = parseRequestLine(requestLineString);

        List<String> headers = extractHeaders(bufferedReader);
        RequestHeaders requestHeaders = parseHeaders(headers);
        RequestCookies requestCookies = parseCookies(requestHeaders.getCookieHeader());

        String bodyString = extractBody(bufferedReader, requestHeaders.getContentLength());
        QueryParameters requestBody = parseRequestBody(bodyString);

        return new Request(requestLine, requestHeaders, requestCookies, requestBody);
    }

    private String extractRequestLine(BufferedReader bufferedReader) throws IOException {
        return bufferedReader.readLine();
    }

    public RequestLine parseRequestLine(String requestLineString) {
        final String[] split = requestLineString.split(REQUEST_LINE_REGEX_SEPARATOR, 3);

        final RequestMethod method = extractRequestMethod(split[0]);
        final String path = extractPath(split[1]);
        final String queryString = extractQueryStringFromUri(split[1]);
        final QueryParameters queryParameters = parseQueryString(queryString);
        final String httpVersion = split[2];

        return new RequestLine(method, path, queryParameters, httpVersion);
    }

    private String extractQueryStringFromUri(String path) {
        final String[] split = path.split(QUERY_STRING_REGEX_SEPARATOR, 2);
        if (split.length != 2) return null;

        return split[1];
    }

    private List<String> extractHeaders(BufferedReader bufferedReader) throws IOException {
        final List<String> headers = new ArrayList<>();
        while (bufferedReader.ready()) {
            var line = bufferedReader.readLine();
            if (line.isEmpty()) break;

            headers.add(line);
        }
        return headers;
    }

    private RequestHeaders parseHeaders(List<String> list) {
        final Map<String, String> map = new HashMap<>();
        list.forEach(line -> {
            int index = line.indexOf(": ");
            String key = line.substring(0, index);
            String value = line.substring(index + 2);
            map.put(key, value);
        });

        return new RequestHeaders(map);
    }

    private RequestCookies parseCookies(String cookieValue) {
        if (cookieValue == null) return RequestCookies.empty();

        Map<String, Cookie> map = new HashMap<>();

        for (String eachCookieString : cookieValue.split(COOKIES_REGEX_SEPARATOR)) {
            String[] keyAndValue = eachCookieString.split(COOKIE_KEY_VALUE_REGEX_SEPARATOR, 2);
            String key = keyAndValue[0];
            String value = keyAndValue[1];
            map.put(key, new Cookie(key, value));
        }

        return new RequestCookies(map);
    }

    private String extractBody(BufferedReader bufferedReader, Integer contentLength) throws IOException {
        String bodyString = null;
        if (contentLength != null && contentLength > 0 && bufferedReader.ready()) {
            char[] buffer = new char[contentLength];
            //noinspection ResultOfMethodCallIgnored
            bufferedReader.read(buffer, 0, contentLength);
            bodyString = new String(buffer);
        }
        return bodyString;
    }

    public QueryParameters parseRequestBody(String requestBody) {
        return parseQueryString(requestBody);
    }

    private RequestMethod extractRequestMethod(String requestMethod) {
        return RequestMethod.valueOf(requestMethod);
    }

    private String extractPath(String uri) {
        return uri.split(QUERY_STRING_REGEX_SEPARATOR)[0];
    }

    private QueryParameters parseQueryString(String queryString) {
        if (queryString == null) return QueryParameters.empty();

        Map<String, List<Object>> map = new HashMap<>();
        for (String each : queryString.split(QUERY_PARAMS_REGEX_SEPARATOR)) {
            String[] keyAndValue = each.split(QUERY_PARAMS_KEY_VALUE_REGEX_SEPARATOR, 2);

            String key = keyAndValue[0];
            String value = keyAndValue.length == 2 ? keyAndValue[1] : null;

            map.computeIfAbsent(key, s -> new ArrayList<>()).add(value);
        }
        return new QueryParameters(map);
    }
}
