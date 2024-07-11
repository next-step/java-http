package camp.nextstep.http;


import java.util.HashMap;
import java.util.Map;

public class RequestLine {

  private final HttpMethod httpMethod;
  private final String path;
  private final String protocol;
  private final String version;
  private final Map<String, String> queryString;

  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  public String getPath() {
    return path;
  }

  public String getProtocol() {
    return protocol;
  }

  public String getVersion() {
    return version;
  }

  public Map<String, String> getQueryString() {
    return queryString;
  }

  public static RequestLine parse(String request) {
    final String[] tokens = request.split(" ");
    final String httpMethod = tokens[0];
    final String path = tokens[1];
    Map<String, String> queryString = new HashMap<>();

    if (path.contains("?")) {
      final String[] pathTokens = path.split("\\?");
      queryString = parseQueryString(pathTokens[1]);
    }

    final String[] versionTokens = tokens[2].split("/");
    final String protocol = versionTokens[0];
    final String version = versionTokens[1];

    return new RequestLine(HttpMethod.valueOf(httpMethod), path, protocol, version, queryString);
  }


  public static Map<String, String> parseQueryString(String queryStrings) {

    Map<String, String> queryString = new HashMap<>();

    String[] params = queryStrings.split("&");
    for (String param : params) {
      String[] keyValue = param.split("=");
      queryString.put(keyValue[0], keyValue[1]);
    }
    return queryString;
  }

  public RequestLine(HttpMethod httpMethod, String path, String protocol, String version,
      Map<String, String> queryString) {
    this.httpMethod = httpMethod;
    this.path = path;
    this.protocol = protocol;
    this.version = version;
    this.queryString = queryString;
  }
}
