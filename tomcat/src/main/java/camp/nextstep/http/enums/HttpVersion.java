package camp.nextstep.http.enums;

public enum HttpVersion {
    VERSION_1_0("1.0"),
    VERSION_1_1("1.1"),
    VERSION_2_0("2.0"),
    NONE("none");

    private String version;

    HttpVersion(String version) {
        this.version = version;
    }

    public static HttpVersion getHttpVersionByVersion(String version) {
        for (HttpVersion httpVersion : HttpVersion.values()) {
            if (httpVersion.version.equals(version)) {
                return httpVersion;
            }
        }
        return NONE;
    }
}
