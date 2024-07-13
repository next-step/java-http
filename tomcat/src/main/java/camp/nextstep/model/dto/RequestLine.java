package camp.nextstep.model.dto;

import camp.nextstep.model.enums.HttpMethod;
import camp.nextstep.model.request.Path;
import camp.nextstep.model.request.Protocol;
import camp.nextstep.model.request.Version;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    HttpMethod method;
    Path path;
    Protocol protocol;
    Version version;
    Map<String, String> queryStringMap = new HashMap<>();

    public HttpMethod getMethod() {
        return method;
    }

    public Path getPath() {
        return path;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public Version getVersion() {
        return version;
    }

    public Map<String, String> getQueryStringMap() {
        return queryStringMap;
    }
    public String getFileExtension() {
        final String dot = ".";
        if (this.path.isEmpty()) {
            return "";
        }
        if (!this.path.contains(dot)) {
            return "";
        }
        String[] split = this.path.split("\\" + dot);
        return split[split.length-1];
    }

    public static RequestLine of(String method, String path, String protocol, String version) {
        RequestLine requestLine = new RequestLine();
        requestLine.method = HttpMethod.valueOf(method);
        requestLine.path = Path.valueOf(path);
        requestLine.protocol = Protocol.valueOf(protocol);
        requestLine.version = Version.fromString(version);
        return requestLine;
    }

    public static RequestLine of(String method, String path, String protocol, String version, Map<String, String> queryString) {
        RequestLine requestLine = new RequestLine();
        requestLine.method = HttpMethod.valueOf(method);
        requestLine.path = Path.valueOf(path);
        requestLine.protocol = Protocol.valueOf(protocol);
        requestLine.version = Version.fromString(version);
        requestLine.queryStringMap = queryString;
        return requestLine;
    }
}
