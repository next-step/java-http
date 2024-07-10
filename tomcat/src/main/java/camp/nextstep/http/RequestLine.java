package camp.nextstep.http;

public class RequestLine {

  private final String httpMethod;
  private final String path;
  private final String protocol;
  private final String version;

  public String getHttpMethod() {
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

  public static RequestLine parse(String request) {
    final String[] tokens = request.split(" ");
    final String httpMethod = tokens[0];
    final String path = tokens[1];

    final String[] versionTokens = tokens[2].split("/");
    final String protocol = versionTokens[0];
    final String version = versionTokens[1];

    return new RequestLine(httpMethod, path, protocol, version);
  }

  public RequestLine(String httpMethod, String path, String protocol, String version) {
    this.httpMethod = httpMethod;
    this.path = path;
    this.protocol = protocol;
    this.version = version;
  }
}
