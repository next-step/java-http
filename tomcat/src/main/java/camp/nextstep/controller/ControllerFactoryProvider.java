package camp.nextstep.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.controller.ControllerFactory;
import org.apache.coyote.controller.ControllerProvider;

public class ControllerFactoryProvider implements ControllerProvider {

    private static final Pattern STATIC = Pattern.compile(".*\\..*");

    private final Map<String, ControllerFactory> factories = new HashMap<>();
    private final ControllerFactory notFoundFactory;
    private final ControllerFactory resourceFacotry;

    public ControllerFactoryProvider(ControllerFactory notFoundFactory,
        ControllerFactory resourceFacotry, Map<String, ControllerFactory> factories) {
        this.notFoundFactory = notFoundFactory;
        this.resourceFacotry = resourceFacotry;

        factories.putAll(factories);
    }

    @Override
    public ControllerFactory provideFactory(String url) {
        if (STATIC.matcher(url).matches()) {
            return resourceFacotry;
        }

        return Optional.ofNullable(factories.get(url)).orElse(notFoundFactory);
    }
}
