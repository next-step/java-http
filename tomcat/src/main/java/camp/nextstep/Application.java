package camp.nextstep;

import camp.nextstep.config.ControllerConfig;
import org.apache.catalina.startup.Tomcat;

public class Application {

    public static void main(String[] args) {
        final var tomcat = new Tomcat();
        tomcat.start();
        ControllerConfig.initialize();
    }
}
