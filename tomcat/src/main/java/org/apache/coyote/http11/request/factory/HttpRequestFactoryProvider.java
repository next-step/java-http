package org.apache.coyote.http11.request.factory;

public interface HttpRequestFactoryProvider {

    HttpRequestFactory provideFactory(final String requestMethod);
}
