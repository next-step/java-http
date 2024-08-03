package camp.nextstep.config;

import camp.nextstep.controller.*;
import camp.nextstep.controller.strategy.*;
import org.apache.coyote.controller.ControllerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ControllerFactoryProviderConfig {

    public static final String STATIC = "static";
    public static final String NOTFOUND = "notfound";
    private static final Map<String, ControllerRequestMapping> cache = new HashMap<>();
    private static final String FACTORY_PROVIDER = "factoryProvider";

    public ControllerRequestMapping createDefaultFactoryProvider() {
        if (cache.containsKey(FACTORY_PROVIDER)) {
            return cache.get(FACTORY_PROVIDER);
        }

        Map<String, ControllerFactory> factories = createFactoryMap();

        ControllerRequestMapping mapping = new ControllerRequestMapping(factories);
        cache.put(FACTORY_PROVIDER, mapping);

        return mapping;
    }

    public Map<String, ControllerFactory> createFactoryMap() {
        Map<String, ControllerFactory> factories = new HashMap<>();

        factories.put(STATIC, new ResourceControllerFactory(new ResourceStrategy()));
        factories.put(NOTFOUND, new NotFoundControllerFactory(new NotFoundStrategy()));
        factories.put("/", new DefaultControllerFactory());
        factories.put("/login",
                new LoginControllerFactory(List.of(new ResourceStrategy(), new LoginGetStrategy())));
        factories.put("/index", new LoginControllerFactory(List.of(new IndexGetStrategy())));
        factories.put("/register", new RegisterControllerFactory(List.of(new ResourceStrategy(), new RegisterPostStrategy())));
        return factories;
    }

}
