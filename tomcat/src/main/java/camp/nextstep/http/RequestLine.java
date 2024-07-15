package camp.nextstep.http;

import camp.nextstep.exception.InvalidRequestException;

public class RequestLine {

  private final HttpMethod httpMethod;
  private final Path path;
  private final String protocol;
  private final String version;

  public HttpMethod getHttpMethod() {
    return httpMethod;
  }

  public Path getPath() {
    return path;
  }

  public String getProtocol() {
    return protocol;
  }

  public String getVersion() {
    return version;
  }

  public static RequestLine parse(String request) {

    if (request == null || request.isEmpty()) {
      throw new InvalidRequestException("request가 null이거나 비어있습니다. " + request);
    }

    final String[] tokens = request.split(" ");
    final String httpMethod = tokens[0];
    String fullPath = tokens[1];
    final String[] versionTokens = tokens[2].split("/");
    final String protocol = versionTokens[0];
    final String version = versionTokens[1];

    Path path = Path.parse(fullPath);

    return new RequestLine(HttpMethod.valueOf(httpMethod), path, protocol, version);
  }

  public RequestLine(HttpMethod httpMethod, Path path, String protocol, String version) {
    this.httpMethod = httpMethod;
    this.path = path;
    this.protocol = protocol;
    this.version = version;
  }
}
