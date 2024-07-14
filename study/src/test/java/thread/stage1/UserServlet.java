package thread.stage1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 해결 방안
 * 1. synchronized 키워드
 * 2. ReentrantLock 을 활용한다
 * 3. DB에서 unique 제약조건을 활용한다
 * 4. concurrency 자료 구조를 활용한다
 */
public class UserServlet {
    private final ReentrantLock lock = new ReentrantLock();

    private final List<User> users = new ArrayList<>();

    public void service(final User user) {
        lock.lock();
        join(user);
        lock.unlock();
    }

    private void join(final User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public int size() {
        return users.size();
    }

    public List<User> getUsers() {
        return users;
    }
}
