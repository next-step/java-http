package org.apache.catalina.connector;

import com.javax.servlet.Servlet;
import org.apache.coyote.http11.Request;
import org.apache.coyote.http11.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CoyoteAdapterTest {

    @DisplayName("Servlet 을 추가한다")
    @Test
    public void addServlet() throws Exception {
        // given
        final CoyoteAdapter adapter = new CoyoteAdapter();
        final Servlet servlet = mock();

        // when then
        assertDoesNotThrow(() -> adapter.addServlet("/*", servlet));
    }

    @DisplayName("요청과 응답에 알맞은 서블릿을 호출한다")
    @Test
    public void service() throws Exception {
        // given
        final CoyoteAdapter adapter = new CoyoteAdapter();
        final Servlet servlet = mock();
        final String mapping = "/servlet";
        final Request request = mock();
        final Response response = mock();

        adapter.addServlet(mapping, servlet);
        when(request.getPath()).thenReturn(mapping);

        // when then
        assertDoesNotThrow(() -> adapter.service(request, response));
    }

}
