package camp.nextstep.controller.strategy;

import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.requestline.RequestMethod;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;

public class ResourceStrategy implements RequestMethodStrategy {

    public static final String ROOT_PATH = "/";
    public static final String DOT = ".";
    private static final String BASE_DIR = "static";
    private static final String DEFAULT_URL = BASE_DIR + "/index.html";

    @Override
    public boolean matched(HttpRequest httpRequest) {
        return httpRequest.getRequestMethod()
                .equals(RequestMethod.GET.name())
                && httpRequest.getParams().isEmpty();
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        String url = addStaticDir(httpRequest);

        return Http11Response.resource(httpRequest.getVersion(), url);
    }

    private String addStaticDir(HttpRequest httpRequest) {
        String url = new StringBuilder()
                .append(BASE_DIR)
                .append(httpRequest.getRequestUrl())
                .toString();

        if (httpRequest.getRequestUrl().equals(ROOT_PATH)) {
            url = DEFAULT_URL;
        }

        if (!httpRequest.getRequestUrl().contains(DOT)) {
            url = url + ".html";
        }

        return url;
    }

}
