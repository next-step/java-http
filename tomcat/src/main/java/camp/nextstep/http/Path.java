package camp.nextstep.http;

import java.util.HashMap;

public class Path {

  private static final String QUERY_STRING_INDICATOR = "?";
  private static final String QUERY_STRING_SEPARATOR = "\\?";

  private final String urlPath;
  private final QueryString queryString;

  public Path(String urlPath, QueryString queryString) {
    this.urlPath = urlPath;
    this.queryString = queryString;
  }

  public String getUrlPath() {
    return urlPath;
  }

  public QueryString getQueryString() {
    return queryString;
  }

  @Override
  public String toString() {
    return "Path{" +
        "urlPath='" + urlPath + '\'' +
        ", queryString=" + queryString +
        '}';
  }


  public static Path parse(String fullPath) {
    String urlPath = fullPath;
    QueryString queryString = new QueryString(new HashMap<>());

    if (fullPath.contains(QUERY_STRING_INDICATOR)) {
      final String[] pathTokens = fullPath.split(QUERY_STRING_SEPARATOR);
      urlPath = pathTokens[0];
      queryString = QueryString.parse(pathTokens[1]);
    }

    return new Path(urlPath, queryString);
  }
}