package camp.nextstep.http;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HttpHeaders {

  private final Map<String, String> headers;
  private final HttpCookie httpCookie;

  public HttpHeaders(Map<String, String> headers) {
    this.headers = new LinkedHashMap<>(headers);
    this.httpCookie = new HttpCookie();
  }

  public void addCookie(HttpCookie cookie) {
    httpCookie.addCookie(cookie);
  }

  public static HttpHeaders from(List<String> headerLines) {
    Map<String, String> headerMap = parseHeaderLines(headerLines);
    return new HttpHeaders(headerMap);
  }

  private static Map<String, String> parseHeaderLines(List<String> headerLines) {
    Map<String, String> headerMap = new LinkedHashMap<>();
    for (String line : headerLines) {
      if (!line.isEmpty()) {
        addHeaderToMap(headerMap, line);
      }
    }
    return headerMap;
  }

  private static void addHeaderToMap(Map<String, String> headerMap, String headerLine) {
    String[] parts = splitHeaderLine(headerLine);
    if (parts.length == 2) {
      headerMap.put(parts[0], parts[1]);
    }
  }

  private static String[] splitHeaderLine(String headerLine) {
    return headerLine.split(": ", 2);
  }

  public boolean isCookieExisted() {
    return headers.containsKey("Cookie");
  }

  public String getValueByKey(String key) {
    return headers.get(key);
  }

  public boolean containsKey(String key) {
    return headers.containsKey(key);
  }

  public int getContentLength() {
    String contentLength = getValueByKey("Content-Length");
    return contentLength != null ? Integer.parseInt(contentLength) : 0;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (httpCookie != null && !httpCookie.getCookies().isEmpty()) {
      sb.append("Set-Cookie: ").append(httpCookie.toString()).append("\r\n");
    }
    for (Map.Entry<String, String> entry : headers.entrySet()) {
      sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\r\n");
    }

    return sb.toString();
  }

}