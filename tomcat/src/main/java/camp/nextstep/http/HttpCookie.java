package camp.nextstep.http;

import camp.nextstep.session.Session;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class HttpCookie {

  private static final String SESSION_COOKIE_KEY = "JSESSIONID";

  private final Map<String, String> cookies;

  public HttpCookie() {
    this.cookies = new LinkedHashMap<>();
  }

  public HttpCookie(Map<String, String> cookies) {
    this.cookies = new LinkedHashMap<>(cookies);
  }

  public Map<String, String> getCookies() {
    return new LinkedHashMap<>(cookies);
  }

  public void addCookie(HttpCookie cookie) {
    this.cookies.putAll(cookie.getCookies());
  }

  public HttpCookie(String cookieString) {
    this.cookies = new LinkedHashMap<>();
    String[] cookiePairs = cookieString.split(";");
    for (String pair : cookiePairs) {
      String[] keyValue = pair.trim().split("=", 2);
      if (keyValue.length == 2) {
        cookies.put(keyValue[0], keyValue[1]);
      }
    }
  }

  public static HttpCookie of() {
    return new HttpCookie(Map.of(SESSION_COOKIE_KEY, UUID.randomUUID().toString()));
  }

  public static HttpCookie cookieSession(Session session) {
    return new HttpCookie(Map.of(SESSION_COOKIE_KEY, session.getId()));
  }

  @Override
  public String toString() {
    return cookies.entrySet().stream()
        .map(entry -> entry.getKey() + "=" + entry.getValue())
        .collect(Collectors.joining("; "));
  }
}