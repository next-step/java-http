package org.apache.coyote.http11.controller;

public interface ControllerProvider {

    ControllerFactory provideFactory(String requestUrl);

}
