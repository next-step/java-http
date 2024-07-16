package support;

import org.apache.catalina.connector.Connector;

import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;

public class StubConnector extends Connector {
    private final AtomicInteger count = new AtomicInteger(0);
    private Thread[] threads;
    private final int port;

    public StubConnector(int port, int acceptCount, int corePoolSize, int maxPoolSize, int queueSize) {
        super(port, acceptCount, corePoolSize, maxPoolSize, queueSize);
        this.port = port;
    }

    public void setTestCount(int testCount) {
        threads = new Thread[testCount];
    }

    @Override
    public void start() {
        super.start();
        try {
            for (int i = 0; i < threads.length; i++) {
                threads[i] = new Thread(() -> incrementIfOk(count, TestHttpUtils.send(port)));
            }

            for (final var thread : threads) {
                thread.start();
            }

            for (final var thread : threads) {
                thread.join();
            }
        } catch (Exception ignored) {

        }
    }

    public int getCount() {
        return count.get();
    }

    private void incrementIfOk(AtomicInteger count, final HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            count.incrementAndGet();
        }
    }
}
