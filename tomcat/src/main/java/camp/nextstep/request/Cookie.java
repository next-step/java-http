package camp.nextstep.request;

import java.util.UUID;

public class Cookie {
    public static final String JSESSIONID_NAME = "JSESSIONID";

    private final String key;
    private final String value;

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String randomJsessionId() {
        return UUID.randomUUID().toString();
    }

    public String getValue() {
        return value;
    }
}
