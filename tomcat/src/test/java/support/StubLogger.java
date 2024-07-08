package support;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.slf4j.LoggerFactory;

public class StubLogger<T> extends ListAppender<ILoggingEvent> {

    public StubLogger(Class<T> type) {
        Logger logger = (Logger) LoggerFactory.getLogger(type);
        this.start();
        logger.addAppender(this);
    }

}
