package camp.nextstep;

import camp.nextstep.servlet.LoginServlet;
import camp.nextstep.servlet.RegisterServlet;
import org.apache.catalina.startup.Tomcat;

public class Application {

    public static void main(String[] args) {
        final var tomcat = new Tomcat();
        tomcat.addServlet("/login", new LoginServlet());
        tomcat.addServlet("/register", new RegisterServlet());
        tomcat.start();
    }
}
