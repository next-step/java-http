package org.apache.coyote.controller;

public interface RequestMapping {

    ControllerFactory getController(String requestUrl);

}
