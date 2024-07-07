package camp.nextstep.http.domain;

public class HttpVersion {
    private final String protocol;
    private final String version;

    public HttpVersion(final String httpVersion) {
        final String[] split = httpVersion.split("/");
        this.protocol = split[0];
        this.version = split[1];
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }
}
