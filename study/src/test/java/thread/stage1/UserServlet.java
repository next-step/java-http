package thread.stage1;

import java.util.ArrayList;
import java.util.List;

public class UserServlet {

    private final List<User> users = new ArrayList<>();

    public void service(final User user) {
        join(user);
    }

    /**
     * user 가 없는 경우 users.add(user) 를 수행하는데 이 작업이 길어지면 여러 쓰레드가 동시에 users.add(user) 까지 진입할 수 있음.
     *
     *  @param user
     */
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
