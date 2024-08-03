package camp.nextstep.controller;

import camp.nextstep.config.ControllerFactoryProviderConfig;
import org.apache.coyote.controller.ControllerFactory;
import org.apache.coyote.controller.RequestMapping;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class ControllerRequestMapping implements RequestMapping {

    private static final Pattern STATIC = Pattern.compile(".*\\..*");

    private final Map<String, ControllerFactory> factories;

    public ControllerRequestMapping(Map<String, ControllerFactory> factories) {
        this.factories = factories;
    }

    @Override
    public ControllerFactory getController(String url) {
        if (STATIC.matcher(url).matches()) {
            return factories.get(ControllerFactoryProviderConfig.STATIC);
        }

        return Optional.ofNullable(factories.get(url))
                .orElse(factories.get(ControllerFactoryProviderConfig.NOTFOUND));
    }
}
