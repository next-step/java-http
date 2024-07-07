package camp.nextstep.request;

import java.util.List;

public class Request {
    private final RequestMethod method;
    private final String path;
    private final List<String[]> queryParameters;
    private final String httpVersion;

    public Request(RequestMethod method, String path, List<String[]> queryParameters, String httpVersion) {
        this.method = method;
        this.path = path;
        this.queryParameters = queryParameters;
        this.httpVersion = httpVersion;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    /**
     * key 이름을 가진 파라메터 값의 배열을 리턴한다.
     *
     * @param key 쿼리 파라메터의 키. 대소문자 구별함
     * @return 하나도 없을 때는 빈 배열, 1개 이상일 때, 쿼리 문자열에 등장한 순서대로 배열로 리턴
     */
    public Object[] getQueryParameters(String key) {
        return queryParameters
                .stream()
                .filter(it -> it[0].equals(key))
                .map(it -> it[1])
                .toArray();
    }

    public Object getQueryParameter(String key) {
        return queryParameters
                .stream()
                .filter(it -> it[0].equals(key))
                .map(it -> it[1])
                .findFirst()
                .orElse(null);
    }

    public String getHttpVersion() {
        return httpVersion;
    }

    public String getPredictedMimeType() {
        if (path.equals("/")) {
            return "text/html";
        }

        if (path.endsWith(".html")) {
            return "text/html";
        }

        if (path.endsWith(".js")) {
            return "application/javascript";
        }

        if (path.endsWith(".css")) {
            return "text/css";
        }

        return "text/html";
    }
}
