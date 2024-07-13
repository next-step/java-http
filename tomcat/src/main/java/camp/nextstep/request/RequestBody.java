package camp.nextstep.request;

public class RequestBody {
    private final String content;

    public RequestBody(String content) {
        this.content = content;
    }

    public QueryParameters toQueryParameters() {
        return QueryParameters.parse(content);
    }
}
