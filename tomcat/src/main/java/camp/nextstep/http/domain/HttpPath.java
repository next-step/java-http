package camp.nextstep.http.domain;

import java.util.Objects;

public class HttpPath {

    private static final String DOT = ".";
    private static final String ROOT_PATH = "/";
    private static final String LOGIN_PATH = "/login";

    private final String path;

    public HttpPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        final int lastDotIndex = path.lastIndexOf(DOT);
        if (lastDotIndex == -1) {
            return "";
        }
        return path.substring(lastDotIndex);
    }

    public boolean isRoot() {
        return ROOT_PATH.equals(path);
    }

    public boolean isLoginPath() {
        return LOGIN_PATH.equals(path);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final HttpPath httpPath = (HttpPath) o;
        return Objects.equals(path, httpPath.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path);
    }
}
