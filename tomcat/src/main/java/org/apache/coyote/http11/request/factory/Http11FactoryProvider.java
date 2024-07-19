package org.apache.coyote.http11.request.factory;

import java.util.HashMap;
import java.util.Map;
import org.apache.coyote.http11.exception.RequestMethodNotFoundException;
import org.apache.coyote.http11.request.RequestMethod;

public class Http11FactoryProvider implements HttpRequestFactoryProvider {

    private final Map<String, HttpRequestFactory> factories = new HashMap<>();

    public Http11FactoryProvider() {
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
