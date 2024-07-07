package camp.nextstep.http.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RequestURITest {

    @Test
    void RequestURI_에서_물음표_앞부분은_Path_이다() {
        final RequestURI requestURI = new RequestURI("/users?userId=javajigi&password=password&name=JaeSung");

        assertThat(requestURI.getPath()).isEqualTo(new HttpPath("/users"));
    }

    @Test
    void RequestURI_에서_물음표_뒷부분은_QueryString_이다() {
        final RequestURI requestURI = new RequestURI("/users?userId=javajigi&password=password&name=JaeSung");

        assertThat(requestURI.getQueryParameters()).isEqualTo(new QueryParameters("userId=javajigi&password=password&name=JaeSung"));
    }

    @Test
    void RequestURI_은_QueryString_없이_생성_할_수_있다() {
        final RequestURI requestURI = new RequestURI("/users");

        SoftAssertions.assertSoftly(softly->{
            softly.assertThat(requestURI.getPath()).isEqualTo(new HttpPath("/users"));
            softly.assertThat(requestURI.getQueryParameters()).isEqualTo(new QueryParameters(""));
        });
    }

}
