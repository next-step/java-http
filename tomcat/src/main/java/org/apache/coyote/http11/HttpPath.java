package org.apache.coyote.http11;

public record HttpPath(
        String path,
        HttpQueryParams queryParams
) {

    public static final String REQUEST_PATH_DELIMITER = "\\?";

    public static HttpPath from(String requestPath) {
        String[] parts = requestPath.split(REQUEST_PATH_DELIMITER, 2);
        String queryString = parts.length > 1 ? parts[1] : null;

        return new HttpPath(parts[0], HttpQueryParams.from(queryString));
    }
}
