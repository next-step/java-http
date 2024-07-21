package camp.nextstep.http.controller;

import camp.nextstep.http.domain.StaticResource;
import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.response.HttpResponse;

public class ResourceController extends AbstractController implements Controller {
    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws Exception {
    }

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            StaticResource resource = StaticResource.createResourceFromRequestLine(request, classLoader);
            response.successResponseByFile(resource.getResourceFile());
        } catch (Exception exception) {
            exception.printStackTrace();
            StaticResource resource = StaticResource.createResourceFromPath("/404.html", classLoader);
            response.notFoundResponseByFile(resource.getResourceFile());
        }
    }

    @Override
    public boolean isExactHandler(HttpRequest httpRequest) {
        return true;
    }
}
