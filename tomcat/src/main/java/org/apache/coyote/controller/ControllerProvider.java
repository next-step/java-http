package org.apache.coyote.controller;

public interface ControllerProvider {

    ControllerFactory provideFactory(String requestUrl);

}
