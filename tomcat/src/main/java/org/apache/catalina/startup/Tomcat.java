package org.apache.catalina.startup;

import org.apache.catalina.Manager;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.DefaultSession;
import org.apache.coyote.http11.RequestHandler;
import org.apache.coyote.http11.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

public class Tomcat {

    private static final Logger log = LoggerFactory.getLogger(Tomcat.class);

    public void start() {
        Manager sessionManager = new SessionManager();
        Session session = new DefaultSession(UUID.randomUUID().toString(), sessionManager);
        var connector = new Connector(session);
        var connector = new Connector(RequestHandler.from(session));
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
