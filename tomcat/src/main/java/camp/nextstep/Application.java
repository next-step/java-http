package camp.nextstep;

import camp.nextstep.config.ControllerFactoryProviderConfig;
import org.apache.catalina.startup.Tomcat;

public class Application {

    public static void main(String[] args) {
        final var tomcat = new Tomcat();
        tomcat.start(new ControllerFactoryProviderConfig().createDefaultFactoryProvider());
    }
}
