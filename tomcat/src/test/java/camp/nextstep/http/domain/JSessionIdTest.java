package camp.nextstep.http.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSessionIdTest {
    @Test
    void UUID_문자열이_들어올_경우_같은_UUID_객체를_가진_JSessionId를_생성한다() {
        // given
        String inputStr = "bbcc4621-d88f-4a94-ae2f-b38072bf5087";

        // when
        JSessionId jSessionId = JSessionId.createJSessionIdByJSessionIdStr(inputStr);

        // then
        assertEquals(inputStr, jSessionId.getJSessionId().toString());
    }
}