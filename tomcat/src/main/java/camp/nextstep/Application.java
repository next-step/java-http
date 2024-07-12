package camp.nextstep;

import camp.nextstep.request.RequestMappingInitializer;
import org.apache.catalina.startup.Tomcat;

public class Application {

    public static void main(String[] args) {
        RequestMappingInitializer.init();

        final var tomcat = new Tomcat();
        tomcat.start();
    }
}
