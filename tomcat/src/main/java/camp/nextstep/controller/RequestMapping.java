package camp.nextstep.controller;

import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import org.apache.coyote.controller.ControllerFactory;

public class RequestMapping implements org.apache.coyote.controller.RequestMapping {

    private static final Pattern STATIC = Pattern.compile(".*\\..*");

    private final Map<String, ControllerFactory> factories;
    private final ControllerFactory notFoundFactory;
    private final ControllerFactory resourceFacotry;

    public RequestMapping(ControllerFactory notFoundFactory,
        ControllerFactory resourceFacotry, Map<String, ControllerFactory> factories) {
        this.notFoundFactory = notFoundFactory;
        this.resourceFacotry = resourceFacotry;
        this.factories = factories;

    }

    @Override
    public ControllerFactory getController(String url) {
        if (STATIC.matcher(url).matches()) {
            return resourceFacotry;
        }

        return Optional.ofNullable(factories.get(url)).orElse(notFoundFactory);
    }
}
