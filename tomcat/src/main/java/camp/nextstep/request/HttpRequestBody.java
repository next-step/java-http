package camp.nextstep.request;

public class HttpRequestBody {
    private final String content;

    public HttpRequestBody(String content) {
        this.content = content;
    }

    public HttpQueryParameters toQueryParameters() {
        return HttpQueryParameters.parse(content);
    }
}
