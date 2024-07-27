package org.apache.coyote.http11.request.factory;

import org.apache.coyote.http11.parser.HttpRequestDto;
import org.apache.coyote.http11.request.Http11Request;
import org.apache.coyote.http11.request.Http11Request.HttpRequestBuilder;
import org.apache.coyote.http11.request.HttpRequest;

public class Http11RequestPostFactory implements HttpRequestFactory {

    public Http11RequestPostFactory() {
    }

    @Override
    public HttpRequest createHttpInstance(HttpRequestDto httpRequestDto) {
        HttpRequestBuilder requestBuilder = Http11Request.HttpRequestBuilder.builder()
                .requestMethod(httpRequestDto.getRequestMethod())
                .requestUrl(httpRequestDto.getRequestUrl())
                .requestProtocol(httpRequestDto.getRequestProtocol())
                .requestBody(httpRequestDto.getRequestBody());

        httpRequestDto.getCookie().ifPresent(requestBuilder::cookie);
        return requestBuilder.build();
    }
}
