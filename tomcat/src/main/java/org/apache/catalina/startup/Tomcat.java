package org.apache.catalina.startup;

import com.javax.servlet.Servlet;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.CoyoteAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Tomcat {

    private static final Logger log = LoggerFactory.getLogger(Tomcat.class);

    private final CoyoteAdapter adapter = new CoyoteAdapter();

    public void start() {
        var connector = new Connector(adapter);
        connector.start();

//        try {
//            // make the application wait until we press any key.
//            System.in.read();
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        } finally {
//            log.info("web server stop.");
//            connector.stop();
//        }
    }

    public void addServlet(final String mapping, final Servlet servlet) {
        adapter.addServlet(mapping, servlet);
    }
}
