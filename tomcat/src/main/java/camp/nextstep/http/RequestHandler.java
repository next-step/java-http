package camp.nextstep.http;

public class RequestHandler {
  private RequestMapping requestMapping = RequestMapping.create();

  public HttpResponse handleRequest(HttpRequest httpRequest) {
    Path path = httpRequest.getRequestLine().getPath();
    String urlPath = path.getUrlPath();


    if(requestMapping.isRegisteredPath(urlPath)){
      try {
        return requestMapping.getController(urlPath).service(httpRequest);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }

    }
    return HttpResponse.notFound("Not found Path");
  }
}