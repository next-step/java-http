package org.apache.catalina.connector;

import com.javax.servlet.Servlet;
import org.apache.coyote.http.HttpRequest;
import org.apache.coyote.http.HttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssumptions.given;
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
        final HttpRequest httpRequest = mock();
        final HttpResponse httpResponse = mock();

        adapter.addServlet(mapping, servlet);
        when(httpRequest.getPath()).thenReturn(mapping);

        // when then
        assertDoesNotThrow(() -> adapter.service(httpRequest, httpResponse));
    }

    @DisplayName("세션 쿠키가 없다면 새로운 새션 ID 를 생성해 추가한다")
    @Test
    public void addSessionCookie() throws Exception {
        // given
        final CoyoteAdapter adapter = new CoyoteAdapter();
        final Servlet servlet = mock();
        final String mapping = "/servlet";
        final HttpRequest httpRequest = mock();
        final HttpResponse httpResponse = new HttpResponse();

        adapter.addServlet(mapping, servlet);
        when(httpRequest.getPath()).thenReturn(mapping);
        when(httpRequest.hasNotSessionId()).thenReturn(true);

        // when
        adapter.service(httpRequest, httpResponse);

        // then
        assertThat(httpResponse.headers()).contains("JSESSIONID");
    }

}
