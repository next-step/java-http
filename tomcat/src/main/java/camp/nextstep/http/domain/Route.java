package camp.nextstep.http.domain;

public enum Route {
    INDEX("/index.html"),
    LOGIN("/login.html"),
    REGISTER("/register.html"),
    UNAUTHORIZED("/401.html"),
    NOT_FOUND("/404.html");

    private final String path;

    Route(final String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
