package org.apache.coyote.http11.request;

import java.util.Map;
import org.apache.coyote.http11.meta.HttpPath;
import org.apache.coyote.http11.meta.HttpMethod;

public class RequestLine {

    private static final String DELIMITER = " ";
    private static final String SLASH = "/";
    private static final int METHOD_INDEX = 0;
    private static final int PATH_INDEX = 1;
    private static final int PROTOCOL_AND_VERSION_INDEX = 2;
    private static final int PROTOCOL_INDEX = 0;
    private static final int VERSION_INDEX = 1;

    private final HttpMethod method;
    private final HttpPath path;
    private final String protocol;
    private final String version;

    private RequestLine(String method, HttpPath path, String protocol, String version) {
        this.method = HttpMethod.valueOf(method);
        this.path = path;
        this.protocol = protocol;
        this.version = version;
    }

    public static RequestLine from(String requestLine) {
        String[] tokens = requestLine.split(DELIMITER);
        String method = tokens[METHOD_INDEX];
        String path = tokens[PATH_INDEX];
        String[] protocolAndVersion = tokens[PROTOCOL_AND_VERSION_INDEX].split(SLASH);
        String protocol = protocolAndVersion[PROTOCOL_INDEX];
        String version = protocolAndVersion[VERSION_INDEX];
        return new RequestLine(method, HttpPath.from(path), protocol, version);
    }

    public String getMethod() {
        return method.name();
    }

    public HttpPath getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public String getExtension() {
        return path.getExtension();
    }

    public Map<String, Object> getParameters() {
        return path.getQuery().getParameters();
    }

    public boolean isGet() {
        return method.isGet();
    }

    public boolean isPost() {
        return method.isPost();
    }
}
