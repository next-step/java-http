package camp.nextstep.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
        List<String[]> queryParameters = parseQueryParameters(split[1]);
        String httpVersion = split[2];

        return new Request(method, path, queryParameters, httpVersion);
    }

    private RequestMethod parseRequestMethod(String requestMethod) {
        return RequestMethod.valueOf(requestMethod);
    }

    private String parsePath(String uri) {
        return uri.split(QUERY_STRING_SEPARATOR)[0];
    }

    private List<String[]> parseQueryParameters(String uri) {
        String[] pathAndQueryString = uri.split(QUERY_STRING_SEPARATOR, 2);
        String queryString = pathAndQueryString.length == 2 ? pathAndQueryString[1] : "";

        return Arrays.stream(queryString.split(QUERY_PARAMS_SEPARATOR))
                .map(keyAndValue -> keyAndValue.split(QUERY_PARAMS_KEY_VALUE_SEPARATOR))
                .toList();
    }
}
