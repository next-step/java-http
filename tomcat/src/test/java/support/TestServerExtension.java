package support;

import com.javax.servlet.Servlet;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

public class TestServerExtension implements BeforeAllCallback, AfterAllCallback {

    private final CountDownLatch latch = new CountDownLatch(1);
    private final TestTomcat tomcat = TestTomcat.getInstance();
    private Thread thread;

    @Override
    public void beforeAll(final ExtensionContext extensionContext) throws Exception {
        TomcatServerTest annotation = getTomcatServerTest(extensionContext);

        Arrays.stream(annotation.servletMappings()).forEach(servletMapping -> {
            try {
                tomcat.addServlet(servletMapping.path(), (Servlet) servletMapping.servlet().getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });

//        thread = new Thread(() -> {
//            try {
                tomcat.start();
//                latch.countDown();
//            } catch (Exception e) {
//            }
//        });
//
//        thread.start();
//
//        latch.await();
    }

    private TomcatServerTest getTomcatServerTest(final ExtensionContext extensionContext) {
        final Class<?> requiredTestClass = extensionContext.getRequiredTestClass();
        TomcatServerTest annotation = requiredTestClass.getAnnotation(TomcatServerTest.class);

        if (annotation == null) {
            Class<?> superclass = requiredTestClass.getSuperclass();
            while (annotation == null && superclass != null) {
                annotation = superclass.getAnnotation(TomcatServerTest.class);
                superclass = superclass.getSuperclass();
            }
        }
        return annotation;
    }

    @Override
    public void afterAll(final ExtensionContext extensionContext) throws Exception {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();
            thread.join();
        }
    }
}
