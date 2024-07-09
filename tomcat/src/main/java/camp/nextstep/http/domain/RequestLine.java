package camp.nextstep.http.domain;

import camp.nextstep.enums.Protocol;
import camp.nextstep.http.enums.HttpMethod;
import camp.nextstep.http.enums.HttpVersion;

public class RequestLine {
    private HttpMethod method;
    private String path;
    private Protocol protocol;
    private HttpVersion version;

    public static RequestLine createRequestLineByRequestLineStr(String requestLineStr) {
        return null;
    }
}
