package camp.nextstep;

import camp.nextstep.request.RequestHandlerInitializer;
import org.apache.catalina.startup.Tomcat;

public class Application {

    public static void main(String[] args) {
        RequestHandlerInitializer.init();

        final var tomcat = new Tomcat();
        tomcat.start();
    }
}
