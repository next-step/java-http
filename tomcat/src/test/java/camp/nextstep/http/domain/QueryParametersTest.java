package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

class QueryParametersTest {

    @Test
    void Query_Parameters_을_통해_값을_추출_할_수_있다() {
        final QueryParameters queryParameters = new QueryParameters("userId=javajigi&password=password&name=JaeSung");

        assertSoftly(softly -> {
            softly.assertThat(queryParameters.get("userId")).isEqualTo("javajigi");
            softly.assertThat(queryParameters.get("password")).isEqualTo("password");
            softly.assertThat(queryParameters.get("name")).isEqualTo("JaeSung");
        });
    }

    @Test
    void Query_Parameters_에_없는_값을_요청하면_null_을_반환한다() {
        final QueryParameters queryParameters = new QueryParameters("userId=javajigi&password=password&name=JaeSung");

        assertThat(queryParameters.get("none")).isNull();
    }

    @Test
    void Query_Parameters_의_형식이_맞지_않으면_null_을_반환한다() {
        final QueryParameters queryParameters = new QueryParameters("userId=javajigi&password:password");

        assertThat(queryParameters.get("password")).isNull();
    }
}
