package camp.nextstep.http.domain;

import java.util.List;

public class RequestURI {
    private static final String EMPTY_STRING = "";
    private static final String DELIMITER = "?";
    private static final String DELIMITER_REGEX = "\\?";
    private static final int PATH_INDEX = 0;
    private static final int QUERY_STRING_INDEX = 1;

    private final HttpPath httpPath;
    private final QueryParameters queryParams;

    public RequestURI(final String requestURI) {
        final List<String> parsedRequestURI = parseRequestURI(requestURI);
        this.httpPath = new HttpPath(parsedRequestURI.get(PATH_INDEX));
        this.queryParams = new QueryParameters(parsedRequestURI.get(QUERY_STRING_INDEX));
    }

    private List<String> parseRequestURI(final String requestURI) {
        if (requestURI.contains(DELIMITER)) {
            return List.of(requestURI.split(DELIMITER_REGEX));
        }

        return List.of(requestURI, EMPTY_STRING);
    }

    public HttpPath getPath() {
        return httpPath;
    }

    public QueryParameters getQueryParameters() {
        return queryParams;
    }
}
