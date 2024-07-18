package camp.nextstep;

import org.apache.catalina.startup.Tomcat;

public class Application {

    public static void main(String[] args) {
        new RequestMappingAdapter();

        final var tomcat = new Tomcat();
        tomcat.start();
    }
}
