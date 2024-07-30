package org.apache.catalina.startup;

import camp.nextstep.controller.ControllerFactoryProvider;
import java.io.IOException;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.controller.ControllerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tomcat {

    private static final Logger log = LoggerFactory.getLogger(Tomcat.class);

    public void start(ControllerProvider controllerProvider) {
        var connector = new Connector(controllerProvider);
        connector.start();

        try {
            // make the application wait until we press any key.
            System.in.read();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.info("web server stop.");
            connector.stop();
        }
    }
}
