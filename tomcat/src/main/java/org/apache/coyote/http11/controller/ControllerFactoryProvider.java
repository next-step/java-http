package org.apache.coyote.http11.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.http11.controller.strategy.IndexGetStrategy;
import org.apache.coyote.http11.controller.strategy.LoginGetStrategy;
import org.apache.coyote.http11.controller.strategy.LoginPostStrategy;

public class ControllerFactoryProvider implements ControllerProvider {

    private final Map<String, ControllerFactory> factories = new HashMap<>();
    private final ControllerFactory notFoundFactory = new Controller404Factory();
    private final ControllerFactory resourceFacotry = new ControllerResourceFactory();
    private final Pattern STATIC = Pattern.compile(".*\\..*");

    public ControllerFactoryProvider() {

        factories.put("/", new ControllerDefaultFactory(List.of()));
        factories.put("/login",
            new ControllerLoginFactory(List.of(new LoginGetStrategy(), new LoginPostStrategy())));
        factories.put("/index", new ControllerLoginFactory(List.of(new IndexGetStrategy())));

    }

    @Override
    public ControllerFactory provideFactory(String url) {
        if (STATIC.matcher(url).matches()) {
            return resourceFacotry;
        }

        return Optional.ofNullable(factories.get(url)).orElse(notFoundFactory);
    }
}
