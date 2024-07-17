package org.apache.coyote.http11.request.factory;

import org.apache.coyote.http11.parser.HttpRequestDto;
import org.apache.coyote.http11.request.HttpRequest;

public interface HttpRequestFactory {

    HttpRequest createHttpInstance(HttpRequestDto httpRequestDto);
}
