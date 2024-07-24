package camp.nextstep.http;

import camp.nextstep.db.InMemoryUserRepository;
import camp.nextstep.model.User;
import camp.nextstep.session.Session;
import camp.nextstep.session.SessionManager;
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
  private static final String ROOT_PATH = "/";
  private static final String LOGIN_PATH = "/login";
  private static final String REGISTER_PATH = "/register";
  private static final String INDEX_PATH = "/index.html";
  private static final String STATIC_PATH = "static/";


  private RequestMapping requestMapping = RequestMapping.create();

  public HttpResponse handleRequest(HttpRequest httpRequest) {
    Path path = httpRequest.getRequestLine().getPath();
    String urlPath = path.getUrlPath();


    if(requestMapping.isRegisteredPath(urlPath)){
      try {
        return requestMapping.getController(urlPath).service(httpRequest);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

    }
    return HttpResponse.notFound(HttpStatus.NOT_FOUND, "Not found Path");

  }

  private HttpResponse handleLoginRequest(HttpRequest httpRequest) {
    QueryString queryString = httpRequest.getRequestLine().getPath().getQueryString();
    String userId = queryString.getValueByKey("account");
    String password = queryString.getValueByKey("password");

    if (httpRequest.getHeaders().isCookieExisted()) {

      String sessionId = httpRequest.getHeaders().getValueByKey("Cookie").split("=")[1];
      if (SessionManager.findSession(sessionId) == null) {
        return HttpResponse.redirect("/401.html");
      }
      return HttpResponse.redirect(INDEX_PATH);
    }

    try {
      User user = checkUserInformation(userId, password);
      Session session = Session.createNewSession();
      session.setAttribute("user", user);
      SessionManager.add(session);

      return HttpResponse.redirect(INDEX_PATH).addCookie(HttpCookie.cookieSession(session));

    } catch (NoSuchElementException e) {
      return HttpResponse.redirect("/401.html");
    }
  }

  private HttpResponse handleRegisterRequest(HttpRequest httpRequest) {

    if (httpRequest.getRequestLine().isGetMethod()) {
      return handleStaticFileRequest("register.html");
    }

    if (httpRequest.getRequestLine().isPostMethod()) {
      return handlePostRegisterRequest(httpRequest);
    }

    return HttpResponse.error(HttpStatus.NOT_FOUND, "Not found");
  }

  private HttpResponse handlePostRegisterRequest(HttpRequest httpRequest) {

    final User user = new User(httpRequest.getRequestBody().getValue("account"),
        httpRequest.getRequestBody().getValue("password"),
        httpRequest.getRequestBody().getValue("email"));
    InMemoryUserRepository.save(user);

    return HttpResponse.redirect(INDEX_PATH);
  }

  private User checkUserInformation(String userId, String password) {

    if (userId == null || password == null) {
      throw new NoSuchElementException("사용자 정보가 올바르지 않습니다.");
    }

    final User user = InMemoryUserRepository.findByAccount(userId)
        .orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));

    if (!user.checkPassword(password)) {
      throw new NoSuchElementException("잘못된 비밀번호입니다.");
    }
    log.info("User logged in: {}", user);
    return user;
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