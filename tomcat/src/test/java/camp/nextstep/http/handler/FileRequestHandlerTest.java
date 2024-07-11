package camp.nextstep.http.handler;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileRequestHandlerTest {
    @Test
    void 존재하는_파일조회_요청이_올경우_해당파일로_Response객체를_생성한다() {
        //given
        RequestLine requestLine = RequestLine.createRequestLineByRequestLineStr("GET /index.html HTTP/1.1");
        FileRequestHandler fileRequestHandler = new FileRequestHandler();

        //when
        Response response = fileRequestHandler.makeResponse(requestLine);

        //then
        assertTrue(response.getResponseStr().contains("로그인"));
    }

    @Test
    void 존재하지않는_파일조회_요청이_올경우_404파일로_Response객체를_생성한다() {
        //given
        RequestLine requestLine = RequestLine.createRequestLineByRequestLineStr("GET /no.html HTTP/1.1");
        FileRequestHandler fileRequestHandler = new FileRequestHandler();

        //when
        Response response = fileRequestHandler.makeResponse(requestLine);

        //then
        assertTrue(response.getResponseStr().contains("404 Error"));
    }
}