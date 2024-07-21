package camp.nextstep.http.domain.response;

public class StringResponseBody implements HttpResponseBody {
    private String responseBody;

    public StringResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    @Override
    public String getBodyString() {
        return responseBody;
    }
}
