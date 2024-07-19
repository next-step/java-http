package camp.nextstep.http.domain;

import java.io.BufferedReader;
import java.util.Map;

public class HttpHeader {
    private Map<String, String> httpHeaders;

    public static HttpHeader createHeadersFromReader(BufferedReader bufferedReader) {
        return new HttpHeader();
    }

    public boolean hasContentLength() {
        return true;
    }
}
