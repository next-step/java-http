package camp.nextstep.http.config;

import camp.nextstep.http.controller.*;
import camp.nextstep.http.handler.*;
import camp.nextstep.service.UserService;

import java.util.List;

public class ServerStartUpConfig {
    private UserService userService;
    private SessionHandler sessionHandler;
    private RootController rootController;
    private LoginController loginController;
    private RegisterController registerController;
    private ResourceController resourceController;
    private TestController testController;
    private RequestMappingHandler requestMappingHandler;

    public UserService getUserService() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public SessionHandler getSessionHandler() {
        if (sessionHandler == null) {
            sessionHandler = new SessionHandler();
        }
        return sessionHandler;
    }

    public RootController getRootController() {
        if (rootController == null) {
            rootController = new RootController();
        }
        return rootController;
    }

    public LoginController getLoginController() {
        if (loginController == null) {
            loginController = new LoginController(
                    getSessionHandler(),
                    getUserService()
            );
        }
        return loginController;
    }

    public RegisterController getRegisterController() {
        if (registerController == null) {
            registerController = new RegisterController(
                    getUserService()
            );
        }
        return registerController;
    }

    public ResourceController getResourceController() {
        if (resourceController == null) {
            resourceController = new ResourceController();
        }
        return resourceController;
    }

    public TestController getTestController() {
        if (testController == null) {
            testController = new TestController();
        }
        return testController;
    }

    public RequestMappingHandler getHttpRequestHandlerContainer() {
        if (requestMappingHandler == null) {
            requestMappingHandler = new RequestMappingHandler(
                    List.of(
                            getRootController(),
                            getLoginController(),
                            getRegisterController(),
                            getTestController(),
                            getResourceController()
                    )
            );
        }
       return requestMappingHandler;
    }
}
