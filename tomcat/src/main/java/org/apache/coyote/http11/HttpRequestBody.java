package org.apache.coyote.http11;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpRequestBody {

  private static final String PARAMETER_SEPARATOR = "&";
  private static final String KEY_VALUE_SEPARATOR = "=";

  private final Map<String, String> values;

  public HttpRequestBody() {
    this.values = new HashMap<>();
  }

  private HttpRequestBody(Map<String, String> values) {
    this.values = new HashMap<>(values);
  }

  public static HttpRequestBody parse(String body) {
    Map<String, String> parsedValues = new HashMap<>();
    String[] pairs = body.split(PARAMETER_SEPARATOR);
    for (String pair : pairs) {
      String[] keyValue = pair.split(KEY_VALUE_SEPARATOR);
      if (keyValue.length == 2) {
        parsedValues.put(keyValue[0], decodeValue(keyValue[1]));
      }
    }
    return new HttpRequestBody(parsedValues);
  }

  private static String decodeValue(String value) {
    return URLDecoder.decode(value, StandardCharsets.UTF_8);
  }

  public String getValue(String key) {
    return values.get(key);
  }

  public boolean hasKey(String key) {
    return values.containsKey(key);
  }

  public Map<String, String> getValues() {
    return Collections.unmodifiableMap(values);
  }
}