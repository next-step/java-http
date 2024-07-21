package camp.nextstep.http.domain.request;

import camp.nextstep.http.enums.HttpMethod;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestMappingKey {
    private Pattern urlPattern;
    private HttpMethod httpMethod;

    public RequestMappingKey(Pattern urlPattern, HttpMethod httpMethod) {
        this.urlPattern = urlPattern;
        this.httpMethod = httpMethod;
    }

    public boolean isRequestMatch(HttpRequest httpRequest) {
        Matcher matcher = urlPattern.matcher(httpRequest.getHttpStartLine().getPath().getUrlPath());
        return matcher.matches()
                && httpMethod == httpRequest.getHttpStartLine().getMethod();
    }
}
