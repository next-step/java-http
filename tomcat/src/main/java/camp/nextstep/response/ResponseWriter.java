package camp.nextstep.response;

import camp.nextstep.request.HttpRequestCookie;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import static camp.nextstep.response.ResponseWritingStage.*;

public class ResponseWriter {
    private final OutputStream outputStream;
    private final StringBuilder responseBuilder;
    private ResponseWritingStage stage;

    public ResponseWriter(OutputStream outputStream) {
        this.responseBuilder = new StringBuilder();

        this.outputStream = outputStream;

        this.stage = BEFORE_RESPONSE_LINE;
    }

    public void setResponseLine(ResponseStatusCode statusCode) {
        assert stage == BEFORE_RESPONSE_LINE : "ResponseLine 을 입력할 수 없는 단계입니다: " + stage;

        responseBuilder.append("HTTP/1.1 ").append(statusCode.getResponseMessage()).append(" ").append("\r\n");

        stage = ADDING_HEADERS;
    }

    public void appendHeaders(Map<String, String> additionalHeaders) {
        additionalHeaders.forEach(this::setHeader);
    }

    public void setHeader(String key, String value) {
        assert stage == ADDING_HEADERS : "헤더를 입력할 수 없는 단계입니다: " + stage;

        responseBuilder.append(key).append(": ")
                .append(value)
                .append(" ").append("\r\n");
    }

    public void appendNewCookies(List<HttpRequestCookie> newCookies) {
        newCookies.forEach(this::appendCookie);
    }

    public void appendCookie(HttpRequestCookie cookie) {
        assert stage == ADDING_HEADERS : "헤더를 입력할 수 없는 단계입니다: " + stage;

        setHeader("Set-Cookie", cookie.getKey() + "=" + cookie.getValueWithParam());
    }

    public void setContent(String content) {
        assert stage == ADDING_HEADERS : "content 를 입력할 수 없는 단계입니다: " + stage;

        responseBuilder.append("\r\n").append(content);
        stage = WROTE_CONTENT;
    }

    public void write() throws IOException {
        assert stage == ADDING_HEADERS || stage == WROTE_CONTENT : "응답을 쓸 수 있는 단계가 아닙니다: " + stage;

        outputStream.write(responseBuilder.toString().getBytes());
        outputStream.flush();
        stage = FINISHED;
    }
}
