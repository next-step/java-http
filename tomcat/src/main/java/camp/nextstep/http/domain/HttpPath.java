package camp.nextstep.http.domain;

import java.util.Objects;

public class HttpPath {

    private final String path;

    public HttpPath(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public boolean isHtml() {
        return path.endsWith(".html");
    }

    public boolean isCss() {
        return path.endsWith(".css");
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
