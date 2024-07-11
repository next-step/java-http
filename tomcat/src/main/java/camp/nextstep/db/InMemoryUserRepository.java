package camp.nextstep.db;

import camp.nextstep.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryUserRepository {

    private static final Map<String, User> database = new ConcurrentHashMap<>();
    private static final AtomicLong autoIncrement = new AtomicLong(1);

    static {
        final User user = new User(autoIncrement.getAndIncrement(), "gugu", "password", "hkkang@woowahan.com");
        database.put(user.getAccount(), user);
    }

    public static void save(User user) {
        database.put(user.getAccount(), user);
    }

    public static Optional<User> findByAccount(String account) {
        return Optional.ofNullable(database.get(account));
    }

    public static long getAutoIncrement() {
        return autoIncrement.getAndIncrement();
    }

    private InMemoryUserRepository() {}
}
