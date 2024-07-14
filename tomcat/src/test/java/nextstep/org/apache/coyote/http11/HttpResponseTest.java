package nextstep.org.apache.coyote.http11;

import org.apache.coyote.http11.HttpProtocol;
import org.apache.coyote.http11.HttpResponse;
import org.apache.coyote.http11.ResourceFinder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpResponseTest {
    @DisplayName("sendResource 메서드는 resourcePath에 해당하는 파일을 찾아 Content-Type, Content-Length 헤더와 response body를 추가한다")
    @Test
    void sendResource() {
        HttpResponse httpResponse = HttpResponse.of(HttpProtocol.from("HTTP/1.1"), new ResourceFinder());

        httpResponse.sendResource("/nextstep.txt");
        
        String format = httpResponse.createFormat();
        assertThat(format).contains("Content-Type", "Content-Length", "nextstep");
    }

    @DisplayName("sendRedirect 메서드는 명시된 path를 Location 헤더에 추가하고, 302 Found 상태 코드를 추가한다")
    @Test
    void sendRedirect() {
        HttpResponse httpResponse = HttpResponse.of(HttpProtocol.from("HTTP/1.1"), new ResourceFinder());

        httpResponse.sendRedirect("/여기로가셈");

        String format = httpResponse.createFormat();
        assertThat(format).contains("Location", "302 Found");
    }
}
