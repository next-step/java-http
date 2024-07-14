package support;

import com.javax.servlet.Servlet;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.CoyoteAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestTomcat {

    private static final Logger log = LoggerFactory.getLogger(TestTomcat.class);
    private static TestTomcat instance;

    private final CoyoteAdapter adapter = new CoyoteAdapter();
    private boolean started = false;

    public static synchronized TestTomcat getInstance() {
        if (instance == null) {
            instance = new TestTomcat();
        }

        return instance;
    }

    public synchronized void start() {
        if (!started) {
            var connector = new Connector(adapter);
            connector.start();
            started = true;
        }
    }

    public void addServlet(final String mapping, final Servlet servlet) {
        adapter.addServlet(mapping, servlet);
    }
}
