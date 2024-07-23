package camp.nextstep.http;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {

  private final Map<String, String> headers;

  public HttpHeaders(Map<String, String> headers) {
    this.headers = Collections.unmodifiableMap(new LinkedHashMap<>(headers));
  }

  public static HttpHeaders from(List<String> headerLines) {
    Map<String, String> headerMap = new LinkedHashMap<>();
    for (String line : headerLines) {
      if (!line.isEmpty()) {
        String[] parts = line.split(": ", 2);
        if (parts.length == 2) {
          headerMap.put(parts[0], parts[1]);
        }
      }
    }
    return new HttpHeaders(headerMap);
  }

  public String get(String key) {
    return headers.get(key);
  }

  public boolean containsKey(String key) {
    return headers.containsKey(key);
  }

  public int getContentLength() {
    String contentLength = get("Content-Length");
    return contentLength != null ? Integer.parseInt(contentLength) : 0;
  }

  public String buildHeaders() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : headers.entrySet()) {
      sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
    }
    return sb.toString();
  }

  public Map<String, String> getHeaders() {
    return Collections.unmodifiableMap(headers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<String, String> entry : headers.entrySet()) {
      sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
    }
    return sb.toString();
  }
}