package camp.nextstep.http.domain;

import java.util.Map;

public class Session {
    private String id;
    private Map<String, Object> attributeMap;

    private Session(String id) {
        this.id = id;
    }

    private Session(String id, Map<String, Object> attributeMap) {
        this.id = id;
        this.attributeMap = attributeMap;
    }

    public String getId() {
        return id;
    }
}
