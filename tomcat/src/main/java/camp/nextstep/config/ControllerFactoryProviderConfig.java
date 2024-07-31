package camp.nextstep.config;

import camp.nextstep.controller.NotFoundControllerFactory;
import camp.nextstep.controller.DefaultControllerFactory;
import org.apache.coyote.controller.ControllerFactory;
import camp.nextstep.controller.RequestMapping;
import camp.nextstep.controller.LoginControllerFactory;
import camp.nextstep.controller.RegisterControllerFactory;
import camp.nextstep.controller.ResourceControllerFactory;
import camp.nextstep.controller.strategy.IndexGetStrategy;
import camp.nextstep.controller.strategy.LoginGetStrategy;
import camp.nextstep.controller.strategy.NotFoundStrategy;
import camp.nextstep.controller.strategy.RegisterPostStrategy;
import camp.nextstep.controller.strategy.ResourceStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ControllerFactoryProviderConfig {

    public RequestMapping createDefaultFactoryProvider(){
        ControllerFactory notFoundFactory = createNotFoundFactory();
        ControllerFactory resourceFacotry = createResourceFactory();
        Map<String, ControllerFactory> factories = createFactoryMap();

        return new RequestMapping(notFoundFactory, resourceFacotry, factories);
    }

    public Map<String, ControllerFactory> createFactoryMap(){
        Map<String, ControllerFactory> factories = new HashMap<>();

        factories.put("/", new DefaultControllerFactory());
        factories.put("/login",
            new LoginControllerFactory(List.of(new ResourceStrategy(), new LoginGetStrategy())));
        factories.put("/index", new LoginControllerFactory(List.of(new IndexGetStrategy())));
        factories.put("/register", new RegisterControllerFactory(List.of(new ResourceStrategy(), new RegisterPostStrategy())));
        return factories;
    }

    public ControllerFactory createNotFoundFactory(){
        return new NotFoundControllerFactory(new NotFoundStrategy());
    }
    public ControllerFactory createResourceFactory(){
        return new ResourceControllerFactory(new ResourceStrategy());
    }
}
