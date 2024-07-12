package support;

import camp.nextstep.Application;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.concurrent.CountDownLatch;

public class TestServerExtension implements BeforeAllCallback, AfterAllCallback {

    private final CountDownLatch latch = new CountDownLatch(1);
    private Thread thread;

    @Override
    public void beforeAll(final ExtensionContext extensionContext) throws Exception {
        thread = new Thread(() -> {
            try {
                Application.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }

            latch.countDown();
        });
        thread.start();
        latch.await();
    }

    @Override
    public void afterAll(final ExtensionContext extensionContext) throws Exception {
        if (thread.isAlive()) {
            thread.interrupt();
            thread.join();
        }
    }
}
