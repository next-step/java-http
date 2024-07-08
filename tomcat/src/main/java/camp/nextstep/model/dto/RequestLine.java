package camp.nextstep.model.dto;

import camp.nextstep.model.enums.HttpMethod;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class RequestLine {
    HttpMethod method;
    String path;
    String protocol;
    String version;
    Map<String, String> queryStringMap = new HashMap<>();

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getQueryStringMap() {
        return queryStringMap;
    }
    public String getFileExtension() {
        final String dot = ".";
        if (StringUtils.isEmpty(this.path)) {
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
        requestLine.path = path;
        requestLine.protocol = protocol;
        requestLine.version = version;
        return requestLine;
    }

    public static RequestLine of(String method, String path, String protocol, String version, Map<String, String> queryString) {
        RequestLine requestLine = new RequestLine();
        requestLine.method = HttpMethod.valueOf(method);
        requestLine.path = path;
        requestLine.protocol = protocol;
        requestLine.version = version;
        requestLine.queryStringMap = queryString;
        return requestLine;
    }
}
