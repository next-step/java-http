package org.apache.coyote;

import com.javax.servlet.Servlet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ServletHandlerTest {

    @DisplayName("등록된 서블릿과 알맞은 url 매핑이면 true 를 반환한다")
    @Test
    public void matchTrue() throws Exception {
        // given
        final Servlet servlet = mock();
        final ServletHandler servletHandler = new ServletHandler("/foo/*", servlet);

        // when
        final boolean match = servletHandler.match("/foo/bar");

        // then
        assertThat(match).isTrue();
    }

    @DisplayName("등록된 서블릿과 알맞지 않은 url 매핑이면 false 를 반환한다")
    @Test
    public void matchFalse() throws Exception {
        // given
        final Servlet servlet = mock();
        final ServletHandler servletHandler = new ServletHandler("/foo/*", servlet);

        // when
        final boolean match = servletHandler.match("/bar");

        // then
        assertThat(match).isFalse();
    }

}
