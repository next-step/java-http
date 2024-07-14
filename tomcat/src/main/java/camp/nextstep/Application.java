package camp.nextstep;

import camp.nextstep.controller.RequestConfig;
import org.apache.catalina.startup.Tomcat;
import org.apache.coyote.http11.RequestHandlerMapping;

public class Application {

    public static void main(String[] args) {
        final var tomcat = new Tomcat();

        RequestHandlerMapping requestHandlerMapping = new RequestHandlerMapping();
        RequestConfig requestConfig = new RequestConfig(requestHandlerMapping);
        requestConfig.register();
        tomcat.start(requestHandlerMapping);
    }
}
