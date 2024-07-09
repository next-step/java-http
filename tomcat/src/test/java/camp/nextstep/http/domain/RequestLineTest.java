package camp.nextstep.http.domain;

import camp.nextstep.http.exception.InvalidHttpRequestSpecException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestLineTest {
    @Test
    void HTTP메소드가_유효하지_않을때_예외를_발생시킨다() {
        //given
        String requestLineStr = "TEST /docs/index.html HTTP/1.1";

        //when & then
        assertThrows(
                InvalidHttpRequestSpecException.class,
                () -> RequestLine.createRequestLineByRequestLineStr(requestLineStr)
        );
    }

    @Test
    void HTTP메소드가_누락되었을때_예외를_발생시킨다() {
        //given
        String requestLineStr = "/docs/index.html HTTP/1.1";

        //when & then
        assertThrows(
                InvalidHttpRequestSpecException.class,
                () -> RequestLine.createRequestLineByRequestLineStr(requestLineStr)
        );
    }

    @Test
    void 엔드포인트가_누락되었을때_예외를_발생시킨다() {
        //given
        String requestLineStr = "GET HTTP/1.1";

        //when & then
        assertThrows(
                InvalidHttpRequestSpecException.class,
                () -> RequestLine.createRequestLineByRequestLineStr(requestLineStr)
        );
    }

    @Test
    void 버전이_누락되었을때_예외를_발생시킨다() {
        //given
        String requestLineStr = "GET /docs/index.html HTTP/";

        //when & then
        assertThrows(
                InvalidHttpRequestSpecException.class,
                () -> RequestLine.createRequestLineByRequestLineStr(requestLineStr)
        );
    }

    @Test
    void 프로토콜이_HTTP가_아닐경우_예외를_발생시킨다() {
        //given
        String requestLineStr = "GET /docs/index.html SOAP/1.1";

        //when & then
        assertThrows(
                InvalidHttpRequestSpecException.class,
                () -> RequestLine.createRequestLineByRequestLineStr(requestLineStr)
        );
    }

    @Test
    void 지원하는_HTTP버전이_아닐경우_예외를_발생시킨다() {
        //given
        String requestLineStr = "GET /docs/index.html HTTP/4.1";

        //when & then
        assertThrows(
                InvalidHttpRequestSpecException.class,
                () -> RequestLine.createRequestLineByRequestLineStr(requestLineStr)
        );
    }

    @Test
    void 정확한_요청이_들어올_경우_성공한다() {
        //given
        String requestLineStr = "GET /docs/index.html HTTP/1.1";

        //when
        RequestLine requestLine =
                RequestLine.createRequestLineByRequestLineStr(requestLineStr);

        //then
        assertNotNull(requestLine);
    }

    @Test
    void 띄어쓰기가_한칸초과로_들어와도_파라미터가_누락되지_않았을_경우_성공한다() {
        //given
        String requestLineStr = "GET          /docs/index.html         HTTP/1.1";

        //when
        RequestLine requestLine =
                RequestLine.createRequestLineByRequestLineStr(requestLineStr);

        //then
        assertNotNull(requestLine);
    }

    @Test
    void 쿼리파라미터가_여러개들어와도_정상파싱이_된다() {
        //given
        String requestLineStr = "GET /users?userId=%20javajigi&password=password&name=JaeSung HTTP/1.1";

        //when
        RequestLine requestLine =
                RequestLine.createRequestLineByRequestLineStr(requestLineStr);

        //then
        assertNotNull(requestLine);
        assertEquals(3, requestLine.getQueryParams().size());
    }
}