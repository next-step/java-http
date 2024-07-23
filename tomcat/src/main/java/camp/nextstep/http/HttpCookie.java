package camp.nextstep.http;

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

  public void addCookie(String name, String value) {
    this.cookies.put(name, value);
  }

  public void addCookie(HttpCookie cookie) {
    this.cookies.putAll(cookie.getCookies());
  }

  public static HttpCookie of() {
    return new HttpCookie(Map.of(SESSION_COOKIE_KEY, UUID.randomUUID().toString()));

  }

  @Override
  public String toString() {
    return cookies.entrySet().stream()
        .map(entry -> entry.getKey() + "=" + entry.getValue())
        .collect(Collectors.joining("; "));
  }
}