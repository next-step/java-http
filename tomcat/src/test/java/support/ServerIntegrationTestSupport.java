package support;

import camp.nextstep.servlet.LoginServlet;

@TomcatServerTest(
        servletMappings = @ServletMapping(path = "/login", servlet = LoginServlet.class)
)
public abstract class ServerIntegrationTestSupport {
}
