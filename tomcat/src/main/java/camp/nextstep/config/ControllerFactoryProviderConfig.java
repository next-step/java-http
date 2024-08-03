package camp.nextstep.config;

import camp.nextstep.controller.*;
import camp.nextstep.controller.strategy.*;
import org.apache.coyote.controller.ControllerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ControllerFactoryProviderConfig {

    public static final Map<String, ControllerRequestMapping> cache = new HashMap<>();

    public ControllerRequestMapping createDefaultFactoryProvider() {
        if (cache.containsKey("factoryProvider")) {
            return cache.get("factoryProvider");
        }

        ControllerFactory notFoundFactory = createNotFoundFactory();
        ControllerFactory resourceFacotry = createResourceFactory();
        Map<String, ControllerFactory> factories = createFactoryMap();

        ControllerRequestMapping mapping = new ControllerRequestMapping(notFoundFactory, resourceFacotry, factories);
        cache.put("factoryProvider", mapping);

        return mapping;
    }

    public Map<String, ControllerFactory> createFactoryMap() {
        Map<String, ControllerFactory> factories = new HashMap<>();

        factories.put("/", new DefaultControllerFactory());
        factories.put("/login",
                new LoginControllerFactory(List.of(new ResourceStrategy(), new LoginGetStrategy())));
        factories.put("/index", new LoginControllerFactory(List.of(new IndexGetStrategy())));
        factories.put("/register", new RegisterControllerFactory(List.of(new ResourceStrategy(), new RegisterPostStrategy())));
        return factories;
    }

    public ControllerFactory createNotFoundFactory() {
        return new NotFoundControllerFactory(new NotFoundStrategy());
    }

    public ControllerFactory createResourceFactory() {
        return new ResourceControllerFactory(new ResourceStrategy());
    }
}
