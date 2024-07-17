package org.apache.coyote.http11.request.factory;

public interface HttpFactoryProvider {

    HttpRequestFactory provideFactory(final String requestMethod);
}
