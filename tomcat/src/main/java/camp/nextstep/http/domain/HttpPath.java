package camp.nextstep.http.domain;

import java.util.Objects;

public class HttpPath {

    private static final String DOT = ".";
    private static final String ROOT_PATH = "/";
    private static final String LOGIN_PATH = "/login";
    public static final String EMPTY_EXTENSION = "";
    public static final String REGISTER_PATH = "/register";

    private final String path;

    public HttpPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String getExtension() {
        if (path.contains(DOT)) {
            return path.substring(path.lastIndexOf(DOT));
        }
        return EMPTY_EXTENSION;
    }

    public boolean isRoot() {
        return ROOT_PATH.equals(path);
    }

    public boolean isLoginPath() {
        return LOGIN_PATH.equals(path);
    }

    public boolean isRegisterPath() {
        return REGISTER_PATH.equals(path);
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