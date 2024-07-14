package camp.nextstep.controller;

import camp.nextstep.request.HttpRequest;

public class RequestMapping {
    private final HelloWorldController helloWorldController = new HelloWorldController();
    private final IndexController indexController = new IndexController();
    private final LoginController loginController = new LoginController();
    private final RegisterController registerController = new RegisterController();
    private final StaticPageController staticPageController = new StaticPageController();

    /**
     * 요구사항: request 객체의 정보를 보고, 처리할 수 있는 컨트롤러를 리턴한다.
     */
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
