package org.apache.catalina.connector;

import com.javax.servlet.Servlet;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.coyote.http.HttpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServletMappingTest {

    @DisplayName("기본 서블릿을 추가하고 정상적으로 반환한다")
    @Test
    public void getServlet() throws Exception {
        // given
        final ServletMapping servletMapping = new ServletMapping();
        final HttpRequest httpRequest = mock();
        when(httpRequest.getPath()).thenReturn("/");

        // when
        final Servlet actual = servletMapping.getServlet(httpRequest);

        // then
        assertThat(actual).isNotNull().isInstanceOf(DefaultServlet.class);
    }

    @DisplayName("서블릿을 추가하고, 우선순위를 기본 서블릿보다 상위에 둔다")
    @Test
    public void addServlet() throws Exception {
        // given
        final ServletMapping servletMapping = new ServletMapping();
        final Servlet servlet = mock(Servlet.class);
        final HttpRequest httpRequest = mock();

        when(httpRequest.getPath()).thenReturn("/");

        // when
        servletMapping.addServlet("/*", servlet);

        // then
        assertThat(servletMapping.getServlet(httpRequest)).isNotNull().isNotInstanceOf(DefaultServlet.class);
    }

}
