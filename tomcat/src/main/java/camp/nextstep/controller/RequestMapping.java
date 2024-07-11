package camp.nextstep.controller;

import camp.nextstep.request.HttpRequest;

public class RequestMapping {
    private final HelloWorldController helloWorldController = new HelloWorldController();
    private final IndexController indexController = new IndexController();
    private final LoginController loginController = new LoginController();
    private final RegisterController registerController = new RegisterController();
    private final StaticPageController staticPageController = new StaticPageController();

    public Controller getController(HttpRequest request) {
        return switch (request.getPath()) {
            case "/" -> helloWorldController;
            case "/login" -> loginController;
            case "/register" -> registerController;
            case "/index.html" -> indexController;
            default -> staticPageController;
        };
    }
}
