package camp.nextstep.http;

import java.util.HashMap;
import java.util.Map;


/*이 객체의 요구사항은 뭘까? 어떤거를 테스트 하면 좋을까?  난  특정 분기값을 주었을때 좋은 불변 객체가 오면 좋겠다. => 여기서는 비즈니스 로직에서 값을 조합하는 역할을 하면 어떨까? */
public class HttpHeaders {

  private Map<String, String> httpHeader;


  public HttpHeaders(String httpHeader) {
    Map<String, String> header = parseHttpHeader(httpHeader);
    this.httpHeader = header;

  }

  private Map<String, String> parseHttpHeader(String httpHeader) {
    return new HashMap<>();
  }
}