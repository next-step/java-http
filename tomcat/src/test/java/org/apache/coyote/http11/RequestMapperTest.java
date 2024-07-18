package org.apache.coyote.http11;

import camp.nextstep.controller.LoginHandler;
import org.apache.coyote.http11.handler.DefaultHandler;
import org.apache.coyote.http11.handler.StaticResourceHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class RequestMapperTest {

    @Test
    @DisplayName("유효한 http path 를 넣으면 등록된 handler 를 반환한다")
    void findHandler_valid() {
        final String path = "/";

        final RequestHandler actual = RequestMapper.findHandler(path);

        assertThat(actual).isInstanceOf(DefaultHandler.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"/css/styles.css", "/assets/img/error-404-monochrome.svg", "/js/scripts.js"})
    @DisplayName("정적 리소스 http path 를 넣으면 StaticResourceHandler 를 반환한다")
    void findHandler_staticResource() {
        final String path = "/index.html";

        final RequestHandler actual = RequestMapper.findHandler(path);

        assertThat(actual).isInstanceOf(StaticResourceHandler.class);
    }

    @Test
    @DisplayName("/login http path 를 넣으면 LoginHandler 를 반환한다")
    void findHandler_login() {
        RequestMapper.addHandlers(Map.of("/login", new LoginHandler()));
        final String path = "/login";

        final RequestHandler actual = RequestMapper.findHandler(path);

        assertThat(actual).isInstanceOf(LoginHandler.class);
    }
}