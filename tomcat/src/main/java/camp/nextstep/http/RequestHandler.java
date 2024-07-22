package camp.nextstep.http;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import camp.nextstep.util.FileUtils;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler {

  private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
  private static final String LOGIN_PATH = "/login";
  private static final String INDEX_PATH = "/index.html";
  private static final String STATIC_PATH = "static/";

  public HttpResponse handleRequest(HttpRequest httpRequest) {
    Path path = httpRequest.getRequestLine().getPath();
    String urlPath = path.getUrlPath();

    if (urlPath.equals(INDEX_PATH)) {
      return handleStaticFileRequest("index.html");
    }

    if (urlPath.equals(LOGIN_PATH)) {
      return handleLoginRequest(path);
    }

    return handleStaticFileRequest(urlPath);
  }

  private HttpResponse handleLoginRequest(Path path) {
    QueryString queryString = path.getQueryString();
    String userId = queryString.getValueByKey("account");
    String password = queryString.getValueByKey("password");

    try {
      checkUserInformation(userId, password);
      return HttpResponse.redirect("/index.html");
    } catch (NoSuchElementException e) {
      return HttpResponse.error(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
  }

  private void checkUserInformation(String userId, String password) {
    final User user = InMemoryUserRepository.findByAccount(userId)
        .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

    if (!user.checkPassword(password)) {
      throw new NoSuchElementException("잘못된 비밀번호입니다.");
    }

    log.info("User logged in: {}", user);
  }

  private HttpResponse handleStaticFileRequest(String filePath) {
    String extension = FileUtils.getFileExtension(filePath);
    URL url = getClass().getClassLoader().getResource(STATIC_PATH + filePath);

    if (url != null) {
      try {
        String responseBody = new String(Files.readAllBytes(new File(url.getFile()).toPath()));
        return HttpResponse.ok(responseBody, ContentType.getTypeByExtention(extension));
      } catch (IOException e) {
        log.error("Error reading file: {}", filePath, e);
        return HttpResponse.error(HttpStatus.INTERNAL_SERVER_ERROR, "File reading error");
      }
    }
    return HttpResponse.error(HttpStatus.NOT_FOUND, "File not found");
  }
}