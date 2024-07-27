package org.apache.coyote.http11.request.factory;

import java.util.HashMap;
import java.util.Map;
import org.apache.coyote.http11.exception.RequestMethodNotFoundException;
import org.apache.coyote.http11.request.requestline.RequestMethod;

public class Http11RequestFactoryProvider implements HttpRequestFactoryProvider {

    private final Map<String, HttpRequestFactory> factories = new HashMap<>();

    public Http11RequestFactoryProvider() {
        factories.put(RequestMethod.GET.toString(), new Http11RequestGetFactory());
        factories.put(RequestMethod.POST.toString(), new Http11RequestPostFactory());
    }

    public HttpRequestFactory provideFactory(final String requestMethod) {
        if (!factories.containsKey(requestMethod)) {
            throw new RequestMethodNotFoundException("Request Method가 존재하지 않습니다.");
        }

        return factories.get(requestMethod);
    }

}
