package org.apache.coyote.http11.model;

import java.util.HashMap;

public class HttpPath {
    private final UrlPath urlPath;
    private final QueryParams queryParams;


    public HttpPath(final String path, final QueryParams queryParams) {
        this.urlPath = new UrlPath(path);
        this.queryParams = queryParams;
    }

    public static HttpPath of(final String path, final HashMap<String, String> queryParams) {
        return new HttpPath(path, new QueryParams(queryParams));
    }

    public String path() {
        return urlPath.urlPath();
    }

    public QueryParams queryParams() {
        return queryParams;
    }

    public boolean isRootPath() {
        return urlPath.isRootPath();
    }

    public String contentTypeText() {
        return urlPath.findContentType()
                .contentType();
    }
}
