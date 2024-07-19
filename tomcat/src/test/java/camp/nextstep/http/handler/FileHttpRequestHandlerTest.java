package camp.nextstep.http.handler;

import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class FileHttpRequestHandlerTest {
    @Test
    void 존재하는_파일조회_요청이_올경우_해당파일로_Response객체를_생성한다() {
        //given
        String line = "GET /index.html HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
        RequestLine requestLine = RequestLine.createRequestLineByInputStream(inputStream);
        FileHttpRequestHandler fileRequestHandler = new FileHttpRequestHandler();

        //when
        HttpResponse httpResponse = fileRequestHandler.makeResponse(requestLine);

        //then
        assertTrue(httpResponse.getResponseStr().contains("로그인"));
    }

    @Test
    void 존재하지않는_파일조회_요청이_올경우_404파일로_Response객체를_생성한다() {
        //given
        String line = "GET /no.html HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
        RequestLine requestLine = RequestLine.createRequestLineByInputStream(inputStream);
        FileHttpRequestHandler fileRequestHandler = new FileHttpRequestHandler();

        //when
        HttpResponse httpResponse = fileRequestHandler.makeResponse(requestLine);

        //then
        assertTrue(httpResponse.getResponseStr().contains("404 Error"));
    }
}
