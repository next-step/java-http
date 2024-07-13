package camp.nextstep.http;

import java.util.HashMap;
import java.util.Map;

public class QueryString {

  private final Map<String, String> queryString;

  public QueryString(Map<String, String> queryStringMap) {
    this.queryString = new HashMap<>(queryStringMap);
  }

  public Map<String, String> getQueryStringMap() {
    return new HashMap<>(queryString);
  }

  public static QueryString parse(String queryStrings) {
    Map<String, String> queryStringMap = new HashMap<>();

    String[] params = queryStrings.split("&");
    for (String param : params) {
      String[] keyValue = param.split("=");
      if (keyValue.length == 2) {
        queryStringMap.put(keyValue[0], keyValue[1]);
      } else {
        queryStringMap.put(keyValue[0], "");
      }
    }
    return new QueryString(queryStringMap);
  }

  @Override
  public String toString() {
    StringBuilder result = new StringBuilder();
    for (Map.Entry<String, String> entry : queryString.entrySet()) {
      if (result.length() > 0) {
        result.append("&");
      }
      result.append(entry.getKey()).append("=").append(entry.getValue());
    }
    return result.toString();
  }
}
