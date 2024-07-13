package camp.nextstep.http;

public class RequestResolver {

  private final String urlPath;
  private final String path;

  public RequestResolver(String urlPath, String path) {
    this.urlPath = urlPath;
    this.path = path;
  }

  public static RequestResolver of(String path) {
    return new RequestResolver(urlPath, path);
  }
}
