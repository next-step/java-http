package org.apache.coyote.http11.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.coyote.http11.controller.strategy.IndexGetStrategy;
import org.apache.coyote.http11.controller.strategy.LoginGetStrategy;
import org.apache.coyote.http11.controller.strategy.LoginPostStrategy;
/*
 * Static Resource 를 스캔닝해서 가져오는 방법이 필요했다. (staticResources를 전담으로 핸들링하는 컨트롤러 팩토리가 필요하기 때문이다.)
 * PathMatchingResourcePatternResolver 을사용하면 Spring에서는 사용이 가능하다.
 * 스캐닝이 해당 Resolver 없이 조금 복잡해지기 때문에 수기로 작성하는것으로 대체한다.
 *
 *        URL url = Thread.currentThread().getContextClassLoader().getResource(""); stackoverflow 발췌
        if (url != null) {
            if (url.getProtocol().equals("file")) {
                File file = Paths.get(url.toURI()).toFile();
                if (file != null) {
                    File[] files = file.listFiles();
                    if (files != null) {
                        for (File filename : files) {
                            filenames.add(filename.toString());
                        }
                    }
                }
 */

public class ControllerFactoryProvider implements ControllerProvider {

    private final Map<String, ControllerFactory> staticResources = new HashMap<>();
    private final Map<String, ControllerFactory> factories = new HashMap<>();
    private final ControllerFactory notFoundFactory = new Controller404Factory();

    public ControllerFactoryProvider() {
        ControllerFactory resourceFactory = new ControllerResourceFactory();
        staticResources.put("/register.html", resourceFactory);
        staticResources.put("/login.html", resourceFactory);
        staticResources.put("/index.html", resourceFactory);
        staticResources.put("/css/styles.css", resourceFactory);
        staticResources.put("/img/chart-area.js", resourceFactory);
        staticResources.put("/img/chart-bar.js", resourceFactory);
        staticResources.put("/img/chart-pie.js", resourceFactory);

        // 해당 로그인 팩터리에 전략 패턴을 사용해서 만약 POST, GET, OPTION 과 같은 REQUEST METHOD를 다수 공급해주면 된다.
        factories.put("/", new ControllerDefaultFactory(List.of()));
        factories.put("/login",
            new ControllerLoginFactory(List.of(new LoginGetStrategy(), new LoginPostStrategy())));
        factories.put("/index", new ControllerLoginFactory(List.of(new IndexGetStrategy())));

    }

    @Override
    public ControllerFactory provideFactory(String url) {
        if (staticResources.containsKey(url)) {
            return staticResources.get(url);
        }

        return Optional.ofNullable(factories.get(url)).orElse(notFoundFactory);
    }
}
