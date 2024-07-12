package org.apache.coyote.http11;

import camp.nextstep.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemorySessionRepository {
    private static final Map<String, User> database = new ConcurrentHashMap<>();

    public static void save(String jSessionId, User user) {
        database.put(jSessionId, user);
    }

    public static boolean exists(String uuid) {
        return database.containsKey(uuid);
    }
}
