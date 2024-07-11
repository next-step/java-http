package camp.nextstep.request;

import java.util.UUID;

public class HttpRequestCookie {
    public static final String JSESSIONID_NAME = "JSESSIONID";

    public static String randomJsessionId() {
        return UUID.randomUUID().toString();
    }

    private final String key;
    private final String value;

    public HttpRequestCookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
