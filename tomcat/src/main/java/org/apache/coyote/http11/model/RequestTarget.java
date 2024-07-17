package org.apache.coyote.http11.model;

public record RequestTarget(
        String path,
        QueryParamsMap queryParamsMap
) {

    public static final String REQUEST_PATH_DELIMITER = "\\?";


    public static RequestTarget from(String requestPath) {
        String[] parts = requestPath.split(REQUEST_PATH_DELIMITER, 2);
        String queryString = parts.length > 1 ? parts[1] : null;

        return new RequestTarget(
                parts[0],
                QueryParamsMap.from(queryString)
        );
    }
}
