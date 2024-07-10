package camp.nextstep.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class RequestParser {
    private static final String REQUEST_LINE_SEPARATOR = " ";
    private static final String QUERY_STRING_SEPARATOR = "\\?";
    private static final String QUERY_PARAMS_SEPARATOR = "&";
    private static final String QUERY_PARAMS_KEY_VALUE_SEPARATOR = "=";

    public Request parse(BufferedReader bufferedReader) throws IOException {
        return parse(bufferedReader.readLine());
    }

    public Request parse(String requestLine) {
        String[] split = requestLine.split(REQUEST_LINE_SEPARATOR, 3);

        RequestMethod method = parseRequestMethod(split[0]);
        String path = parsePath(split[1]);
        QueryParameters queryParameters = parseQueryParameters(split[1]);
        String httpVersion = split[2];

        return new Request(method, path, queryParameters, httpVersion);
    }

    private RequestMethod parseRequestMethod(String requestMethod) {
        return RequestMethod.valueOf(requestMethod);
    }

    private String parsePath(String uri) {
        return uri.split(QUERY_STRING_SEPARATOR)[0];
    }

    // XXX: 여기 정리?
    private QueryParameters parseQueryParameters(String uri) {
        Map<String, List<Object>> map = new HashMap<>();

        String[] pathAndQueryString = uri.split(QUERY_STRING_SEPARATOR, 2);
        String queryString = pathAndQueryString.length == 2 ? pathAndQueryString[1] : "";

        Arrays.stream(queryString.split(QUERY_PARAMS_SEPARATOR))
                .forEach(keyAndValue -> {
                    if (keyAndValue.contains("=")) {
                        String[] split = keyAndValue.split(QUERY_PARAMS_KEY_VALUE_SEPARATOR, 2);

                        String key = split[0];
                        String value = split[1];
                        map.computeIfAbsent(key, s -> new ArrayList<>());
                        map.get(key).add(value);
                    }
                });
        return new QueryParameters(map);
    }
}
