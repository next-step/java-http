package camp.nextstep.http.domain;

import java.util.UUID;

public class JSessionId {
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
}
