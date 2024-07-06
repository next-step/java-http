package camp.nextstep.io;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RequestLine {
    private String method;
    private String path;
    private String protocol;
    private String version;
    private List<Map> queryString = new ArrayList<>();

    public RequestLine(String request) {
        String[] tokens = request.split(" ");
        this.method = tokens[0];

        if (tokens[1].contains("?")) {
            String[] pathAndQueryString = tokens[1].split("\\?");
            this.path = pathAndQueryString[0];
            String[] queryStrings = pathAndQueryString[1].split("&");
            for (String queryString : queryStrings) {
                String[] keyAndValue = queryString.split("=");
                this.queryString.add(Map.of(keyAndValue[0], keyAndValue[1]));
            }
        } else {
            this.path = tokens[1];
        }

        String[] protocolAndVersion = tokens[2].split("/");
        this.protocol = protocolAndVersion[0];
        this.version = protocolAndVersion[1];
    }

    public String getMethod() {
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

    public List<Map> getQueryString() {
        return queryString;
    }
}
