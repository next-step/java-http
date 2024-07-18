package thread.stage1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/*
 * 해결 방안
 * 1. synchronized
 * 2. ReentrantLock
 * 3. DB에서 UNIUUE 제약조건을 활요한다.
 * 4. CONCURRENCY 자료 구조를 활용한다. (1번과 동일) `자바 최적화`나 `Effective Java` 에서 추천하는 방법
 * 성능은 무엇이 제일 좋을까?
 * - ReentrantLock이 제일 좋지 않을까?
 * - 하지만, 추후에, 데드락이나 경합상태를 분석하면서 해결하는 경우가 생길것같다.
 * - 서버가 2개가 된다면, 여기서의 동시성 문제를 해결해도 의미가 없다.
 * - 그러므로, DB에 의존을 하거나, UNIQUE KEY를 생성하는 UUID에 의존하는 방향이 좋지 않을까?
 */

public class UserServlet {
    private final ReentrantLock lock = new ReentrantLock();
    private final List<User> users = new ArrayList<>();
    // 혹은 private final ConcurrentHashMap 으로 userName에 대해 중복 체크를 한다.
    public void service(final User user) {
        // 혹은 public synchronized void service
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
