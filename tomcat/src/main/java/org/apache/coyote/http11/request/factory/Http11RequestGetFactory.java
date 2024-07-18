package org.apache.coyote.http11.request.factory;

import org.apache.coyote.http11.parser.HttpRequestDto;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.request.HttpRequest;

public class Http11RequestGetFactory implements HttpRequestFactory {

    public Http11RequestGetFactory() {
    }

    @Override
    public HttpRequest createHttpInstance(HttpRequestDto httpRequestDto) {
        return Http11Request.HttpRequestBuilder.builder()
            .requestMethod(httpRequestDto.getRequestMethod())
            .requestUrl(httpRequestDto.getRequestUrl())
            .requestProtocol(httpRequestDto.getRequestProtocol())
            .build();
    }

}
