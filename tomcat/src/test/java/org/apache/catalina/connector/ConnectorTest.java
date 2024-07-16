package org.apache.catalina.connector;

import org.apache.coyote.RequestMapping;
import org.junit.jupiter.api.Test;
import support.StubConnector;
import support.StubController;

import static org.assertj.core.api.Assertions.assertThat;

class ConnectorTest {

    @Test
    void test_max_thread() {
        RequestMapping.addDefault(new StubController(false));
        var connector = new StubConnector(8083, 10, 10, 100, 20);
        connector.setTestCount(200);
        connector.start();

        assertThat(connector.getCount()).isEqualTo(100);
    }

    @Test
    void test_queue() {
        RequestMapping.addDefault(new StubController(true));
        var connector = new StubConnector(8082, 10, 10, 100, 20);
        connector.setTestCount(200);
        connector.start();

        assertThat(connector.getCount()).isEqualTo(120);
    }


}