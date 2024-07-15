package camp.nextstep;

import camp.nextstep.service.UserService;
import camp.nextstep.servlet.LoginServlet;
import camp.nextstep.servlet.RegisterServlet;
import org.apache.catalina.startup.Tomcat;

public class Application {

    public static void main(String[] args) {
        final var tomcat = new Tomcat();

        final UserService userService = new UserService();

        tomcat.addServlet("/login", new LoginServlet(userService));
        tomcat.addServlet("/register", new RegisterServlet(userService));
        tomcat.start();
    }
}
