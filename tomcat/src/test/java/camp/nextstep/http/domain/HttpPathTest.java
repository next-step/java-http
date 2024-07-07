package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HttpPathTest {

    @Test
    void httpPath_를_생성할_수_있다() {
        final HttpPath httpPath = new HttpPath("/users");

        assertThat(httpPath.getPath()).isEqualTo("/users");
    }

    @Test
    void httpPath_가_html_인지_반환받을_수_있다() {
        final HttpPath httpPath = new HttpPath("/index.html");

        assertThat(httpPath.isHtml()).isTrue();
    }

    @Test
    void httpPath_가_css_인지_반환받을_수_있다() {
        final HttpPath httpPath = new HttpPath("/style.css");

        assertThat(httpPath.isCss()).isTrue();
    }

    @Test
    void httpPath_가_js_인지_반환받을_수_있다() {
        final HttpPath httpPath = new HttpPath("/scripts.js");

        assertThat(httpPath.isJs()).isTrue();
    }

}
