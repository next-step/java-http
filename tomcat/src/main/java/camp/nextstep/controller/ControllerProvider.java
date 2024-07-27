package camp.nextstep.controller;

public interface ControllerProvider {

    ControllerFactory provideFactory(String requestUrl);

}
