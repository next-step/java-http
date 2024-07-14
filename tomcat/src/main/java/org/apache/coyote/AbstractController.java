package org.apache.coyote;

public abstract class AbstractController implements Controller {

    public AbstractController(final String mappingPath) {
        RequestMapping.addMapping(mappingPath, this);
    }

    @Override
    public HttpResponse service(HttpRequest request) throws Exception {
       if (request.isGet()) {
           return doGet(request);
       }
       return doPost(request);
    }

    protected HttpResponse doPost(HttpRequest request) throws Exception {
        return null;
    }
    protected HttpResponse doGet(HttpRequest request) throws Exception {
        return null;
    }
}
