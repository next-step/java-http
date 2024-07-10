package camp.nextstep.request;

import java.util.UUID;

public class Cookie {
    public static final String JSESSIONID_NAME = "JSESSIONID";

    public static UUID randomJsessionId() {
        return UUID.randomUUID();
    }

    private final String key;
    private final String value;

    public Cookie(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
