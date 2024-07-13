package camp.nextstep.http;

import java.util.HashMap;
import java.util.Map;

public class QueryString {

  private static final String PARAMETER_SEPARATOR = "&";
  private static final String KEY_VALUE_SEPARATOR = "=";
  private final Map<String, String> queryString;

  public QueryString(Map<String, String> queryStringMap) {
    this.queryString = new HashMap<>(queryStringMap);
  }

  public Map<String, String> getQueryString() {
    return new HashMap<>(queryString);
  }

  public static QueryString parse(String queryStrings) {
    Map<String, String> queryStringMap = new HashMap<>();

    String[] params = queryStrings.split(PARAMETER_SEPARATOR);
    for (String param : params) {
      String[] keyValue = param.split(KEY_VALUE_SEPARATOR);
      if (keyValue.length == 2) {
        queryStringMap.put(keyValue[0], keyValue[1]);
      } else {
        queryStringMap.put(keyValue[0], "");
      }
    }
    return new QueryString(queryStringMap);
  }

  public String getValueByKey(String key) {
    return queryString.get(key);
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
