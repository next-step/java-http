package camp.nextstep.http.domain.response;

public class EmptyResponseBody implements HttpResponseBody {

    @Override
    public String getBodyString() {
        return null;
    }
}
