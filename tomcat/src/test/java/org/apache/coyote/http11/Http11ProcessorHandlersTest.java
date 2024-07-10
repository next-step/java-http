package org.apache.coyote.http11;

import org.apache.coyote.handler.*;
import org.apache.http.HttpPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import support.StubHttpRequest;

import java.util.List;

import static org.mockito.Mockito.*;

class Http11ProcessorHandlersTest {

    private Handler registerHandler;
    private Handler loginHandler;
    private Handler resourceHandler;
    private Handler defaultHandler;
    private Http11ProcessorHandlers handlers;

    @BeforeEach()
    void setUp() {
        registerHandler = spy(RegisterHandler.class);
        loginHandler = spy(LoginHandler.class);
        resourceHandler = spy(ResourceHandler.class);
        defaultHandler = spy(DefaultHandler.class);
        handlers = new Http11ProcessorHandlers(List.of(registerHandler, loginHandler, resourceHandler, defaultHandler));
    }

    @Test
    void register() {
        var request = new StubHttpRequest(new HttpPath("/register"));

        handlers.handle(request);
        verify(registerHandler, atLeastOnce()).handle(request);
        verify(loginHandler, never()).handle(request);
        verify(resourceHandler, never()).handle(request);
        verify(defaultHandler, never()).handle(request);
    }

    @Test
    void login() {
        var request = new StubHttpRequest(new HttpPath("/login?account=gugu&password=password"));

        handlers.handle(request);
        verify(loginHandler, atLeastOnce()).handle(request);
        verify(resourceHandler, never()).handle(request);
        verify(defaultHandler, never()).handle(request);
    }

    @Test
    void resource() {
        var request = new StubHttpRequest(new HttpPath("/index.html"));

        handlers.handle(request);
        verify(loginHandler, atLeastOnce()).handle(request);
        verify(resourceHandler, atLeastOnce()).handle(request);
        verify(defaultHandler, never()).handle(request);
    }

    @Test
    void noMatch() {
        var request = new StubHttpRequest(new HttpPath("/"));

        handlers.handle(request);
        verify(loginHandler, atLeastOnce()).handle(request);
        verify(resourceHandler, atLeastOnce()).handle(request);
        verify(defaultHandler, atLeastOnce()).handle(request);
    }

}