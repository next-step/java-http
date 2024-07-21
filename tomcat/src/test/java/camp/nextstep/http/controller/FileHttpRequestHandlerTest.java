package camp.nextstep.http.controller;

import camp.nextstep.http.domain.request.HttpRequest;
import camp.nextstep.http.domain.response.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

class ResourceControllerTest {
    @Test
    void 존재하는_파일조회_요청이_올경우_해당파일로_Response객체를_생성한다() throws Exception {
        //given
        String line = "GET /index.html HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
        HttpRequest httpRequest = HttpRequest.createRequestLineByInputStream(inputStream);
        ResourceController resourceController = new ResourceController();

        //when
        HttpResponse httpResponse = new HttpResponse();
        resourceController.doGet(httpRequest, httpResponse);

        //then
        assertTrue(httpResponse.getResponseStr().contains("로그인"));
    }

    @Test
    void 존재하지않는_파일조회_요청이_올경우_404파일로_Response객체를_생성한다() throws Exception {
        //given
        String line = "GET /no.html HTTP/1.1";
        InputStream inputStream = new ByteArrayInputStream(line.getBytes(StandardCharsets.UTF_8));
        HttpRequest httpRequest = HttpRequest.createRequestLineByInputStream(inputStream);
        ResourceController resourceController = new ResourceController();

        //when
        HttpResponse httpResponse = new HttpResponse();
        resourceController.doGet(httpRequest, httpResponse);

        //then
        assertTrue(httpResponse.getResponseStr().contains("404 Error"));
    }
}
