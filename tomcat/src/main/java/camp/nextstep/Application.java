package camp.nextstep;

import camp.nextstep.controller.RequestConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.RequestHandler;

public class Application {

    public static void main(String[] args) {
        final var tomcat = new Tomcat();
        RequestHandler requestHandler = new RequestHandler();
        RequestConfig requestConfig = new RequestConfig(requestHandler);
        requestConfig.register();
        tomcat.start(requestHandler);
    }
}
