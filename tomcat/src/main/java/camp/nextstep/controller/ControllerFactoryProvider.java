package camp.nextstep.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import camp.nextstep.controller.strategy.IndexGetStrategy;
import camp.nextstep.controller.strategy.LoginGetStrategy;
import camp.nextstep.controller.strategy.NotFoundStrategy;
import camp.nextstep.controller.strategy.RegisterPostStrategy;
import camp.nextstep.controller.strategy.ResourceStrategy;

public class ControllerFactoryProvider implements ControllerProvider {

    private final Map<String, ControllerFactory> factories = new HashMap<>();
    private final ControllerFactory notFoundFactory;
    private final ControllerFactory resourceFacotry;
    private final Pattern STATIC = Pattern.compile(".*\\..*");

    public ControllerFactoryProvider() {
        notFoundFactory = new Controller404Factory(new NotFoundStrategy());
        resourceFacotry = new ControllerResourceFactory(new ResourceStrategy());

        factories.put("/", new ControllerDefaultFactory(List.of()));
        factories.put("/login",
            new ControllerLoginFactory(List.of(new ResourceStrategy(), new LoginGetStrategy())));
        factories.put("/index", new ControllerLoginFactory(List.of(new IndexGetStrategy())));
        factories.put("/register", new ControllerRegisterFactory(List.of(new ResourceStrategy(), new RegisterPostStrategy())));

    }

    @Override
    public ControllerFactory provideFactory(String url) {
        if (STATIC.matcher(url).matches()) {
            return resourceFacotry;
        }

        return Optional.ofNullable(factories.get(url)).orElse(notFoundFactory);
    }
}
