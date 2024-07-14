package org.apache.coyote;

public interface Controller {
    HttpResponse service(HttpRequest request) throws Exception;
}
