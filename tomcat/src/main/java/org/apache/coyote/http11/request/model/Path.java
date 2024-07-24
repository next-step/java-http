package org.apache.coyote.http11.request.model;

public record Path(String urlPath, QueryStrings queryStrings) {

    public static Path createPathWithRedirectPath(Path path, String redirectPath) {
        return new Path(redirectPath, path.queryStrings());
    }

    public String getExtension() {
        return urlPath.substring(urlPath.lastIndexOf(".") + 1);
    }
}
