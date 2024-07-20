package camp.nextstep.http.domain;

import java.util.UUID;

public class JSessionId {
    private static final String JSESSION_ID_HEADER = "JSESSIONID=";
    private UUID jSessionId;

    private JSessionId(UUID jSessionId) {
        this.jSessionId = jSessionId;
    }

    public UUID getJSessionId() {
        return jSessionId;
    }

    public static JSessionId createJSessionIdByJSessionIdStr(String jSessionId) {
        return new JSessionId(UUID.fromString(jSessionId));
    }

    public static JSessionId createJSessionId() {
        return new JSessionId(UUID.randomUUID());
    }

    public static String createJSessionIdStr() {
        return JSESSION_ID_HEADER.concat(createJSessionId().jSessionId.toString());
    }
}
