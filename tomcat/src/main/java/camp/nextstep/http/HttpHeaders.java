package camp.nextstep.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HttpHeaders {

  private final Map<String, String> headers;

  public HttpHeaders() {
    this.headers = new HashMap<>();
  }

  public HttpHeaders(List<String> headers) {
    this.headers = new HashMap<>();
    headers.forEach(header -> {
      String[] split = header.split(": ");
      this.headers.put(split[0].toLowerCase(), split[1]);
    });
  }

  public void add(String key, String value) {
    headers.put(key.toLowerCase(), value);
  }

  public String get(String key) {
    return headers.get(key.toLowerCase());
  }

  public boolean contains(String key) {
    return headers.containsKey(key.toLowerCase());
  }
}