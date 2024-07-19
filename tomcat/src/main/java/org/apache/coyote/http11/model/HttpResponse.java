package org.apache.coyote.http11.model;

import org.apache.coyote.http11.constants.HttpStatus;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpResponse {
    private final OutputStream outputStream;
    private final HttpHeaders httpHeaders = new HttpHeaders();
    private HttpStatus status;
    private ContentType contentType;
    private String content;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public void setJSessionId(String jSessionId) {
        this.httpHeaders.addHeader("Set-Cookie", "JSESSIONID=" + jSessionId);
    }

    public void send() throws IOException {
        List<String> responseLines = new ArrayList<>();
        responseLines.add("HTTP/1.1 " + status.getCode() + " " + status.getMessage() + " ");
        responseLines.add("Content-Type: " + contentType.getType() + ";charset=utf-8 ");
        responseLines.add("Content-Length: " + content.getBytes().length + " ");

        /* 커스텀 response header 추가 */
        for(Map.Entry<String, String> entry : httpHeaders.headers().entrySet()) {
            responseLines.add(entry.getKey() + ": " + entry.getValue());
        }

        responseLines.add(""); // header 와 body 구분을 위한 빈 줄
        responseLines.add(content);

        final String response = String.join("\r\n", responseLines);

        outputStream.write(response.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }
}
