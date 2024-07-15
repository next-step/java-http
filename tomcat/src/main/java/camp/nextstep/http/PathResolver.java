package camp.nextstep.http;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathResolver {

  private static final Logger log = LoggerFactory.getLogger(PathResolver.class);
  private final String filePath;

  public String getFilePath() {
    return filePath;
  }

  public PathResolver(String filePath) {
    this.filePath = filePath;
  }

  public static PathResolver of(Path path) {

    if (path.getUrlPath().equals("/index.html")) {
      return new PathResolver("index.html");
    }

    if (path.getUrlPath().equals("/login")) {

      QueryString queryString = path.getQueryString();
      checkUserInformation(queryString.getValueByKey("account"),
          queryString.getValueByKey("password"));

      return new PathResolver("/login.html");
    }
    return new PathResolver(path.getUrlPath());
  }

  private static void checkUserInformation(String userId, String password) {

    System.out.println("userId = " + userId);
    System.out.println("password = " + password);
    final User user = InMemoryUserRepository.findByAccount(userId)
        .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

    if (user.checkPassword(password)) {
      log.info("user" + user);
    }
  }
}
