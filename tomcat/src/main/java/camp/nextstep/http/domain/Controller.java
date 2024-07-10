package camp.nextstep.http.domain;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws Exception;
}
