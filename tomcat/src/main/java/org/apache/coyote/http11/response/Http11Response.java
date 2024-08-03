package org.apache.coyote.http11.response;

import org.apache.coyote.http11.exception.StaticResourceNotFoundException;
import org.apache.coyote.http11.response.header.ContentLength;
import org.apache.coyote.http11.response.header.ContentType;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;
import org.apache.coyote.http11.response.header.SetCookieAdd;
import org.apache.coyote.http11.response.statusline.StatusCode;
import org.apache.coyote.http11.response.statusline.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class Http11Response extends HttpResponse {

    private static final String BASE_DIR = "static";
    private static final String UNAUTHOIRZED = BASE_DIR + "/401.html";
    private static final String NOTFOUND = "static/404.html";

    private Http11Response(final HttpResponseBuilder responseBuilder) {
        super(responseBuilder.statusLine, responseBuilder.messageBody,
                responseBuilder.http11ResponseHeader);
    }

    public static HttpResponse redirect(String redirectPath){
        Http11ResponseHeader responseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder.builder()
                .contentType(ContentType.html.name())
                .location(redirectPath)
                .build();

        return new Http11Response.HttpResponseBuilder()
                .found()
                .responseHeader(responseHeader)
                .build();
    }

    public static HttpResponse redirect(String redirectPath, Map<String, String> session){
        Http11ResponseHeader responseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder.builder()
                .contentType(ContentType.html.name())
                .location(redirectPath)
                .build();

        SetCookieAdd sessionAdder = new SetCookieAdd();
        sessionAdder.add(responseHeader, session);

        return new Http11Response.HttpResponseBuilder()
                .found()
                .responseHeader(responseHeader)
                .build();
    }

    public static HttpResponse resource(String version, String url){
        File file = getFile(url);

        try (Stream<String> content = Files.lines(file.toPath())){

            final Http11ResponseHeader http11ResponseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder
                    .builder()
                    .contentType(file.getName())
                    .build();

            return new Http11Response.HttpResponseBuilder()
                    .ok()
                    .responseHeader(http11ResponseHeader)
                    .messageBody(content)
                    .build();

        } catch (IOException e) {
            throw new StaticResourceNotFoundException("Static Resource가 없습니다.");
        }
    }

    public static HttpResponse unauthorized(){
        File file = getFile(UNAUTHOIRZED);
        try (Stream<String> content = Files.lines(file.toPath())){

            final Http11ResponseHeader http11ResponseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder
                    .builder()
                    .contentType(ContentType.html.name())
                    .build();

            return new Http11Response.HttpResponseBuilder()
                    .unauthorized()
                    .responseHeader(http11ResponseHeader)
                    .messageBody(content)
                    .build();

        } catch (
                IOException e) {
            throw new StaticResourceNotFoundException("Static Resource가 없습니다.");
        }
    }

    public static HttpResponse notfound(){
        File file = getFile(NOTFOUND);
        try (Stream<String> content = Files.lines(file.toPath())){

            Http11ResponseHeader responseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder.http();

            return new Http11Response.HttpResponseBuilder()
                    .notfound()
                    .responseHeader(responseHeader)
                    .messageBody(content)
                    .build();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File getFile(String url){
        final URL resource = Http11Response.class
                .getClassLoader()
                .getResource(url);

        return new File(Objects.requireNonNull(resource).getFile());
    }


    public static class HttpResponseBuilder {

        private static final Logger log = LoggerFactory.getLogger(HttpResponseBuilder.class);

        private StatusLine statusLine;
        private MessageBody messageBody;
        private Http11ResponseHeader http11ResponseHeader;

        public HttpResponseBuilder() {
        }

        public static Http11Response.HttpResponseBuilder builder() {
            return new Http11Response.HttpResponseBuilder();
        }

        public Http11Response.HttpResponseBuilder ok(){
            this.statusLine = new StatusLine("1.1", StatusCode.OK.name());

            return this;
        }

        public Http11Response.HttpResponseBuilder found(){
            this.statusLine = new StatusLine("1.1", StatusCode.FOUND.name());

            return this;
        }

        public Http11Response.HttpResponseBuilder notfound(){
            this.statusLine = new StatusLine("1.1", StatusCode.NOTFOUND.name());

            return this;
        }

        public Http11Response.HttpResponseBuilder unauthorized(){
            this.statusLine = new StatusLine("1.1", StatusCode.UNAUTHORIZED.name());

            return this;
        }

        public Http11Response.HttpResponseBuilder headers(Http11ResponseHeader http11ResponseHeader){
            this.http11ResponseHeader = http11ResponseHeader;

            return this;
        }

        public Http11Response.HttpResponseBuilder body(MessageBody messageBody){
            this.messageBody = messageBody;

            return this;
        }

        public Http11Response.HttpResponseBuilder statusLine(String version, String status) {
            this.statusLine = new StatusLine(version, status);
            return this;
        }

        public Http11Response.HttpResponseBuilder responseHeader(String contentType,
                                                                 int responseBodySize) {
            this.http11ResponseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder
                    .builder()
                    .contentType(contentType)
                    .contentLength(responseBodySize)
                    .build();
            return this;
        }

        public Http11Response.HttpResponseBuilder responseHeader(Http11ResponseHeader http11ResponseHeader) {
            this.http11ResponseHeader = http11ResponseHeader;
            return this;
        }

        public Http11Response.HttpResponseBuilder messageBody(Stream<String> content) {
            this.messageBody = new MessageBody(content);
            this.http11ResponseHeader.setContentLength(new ContentLength(this.messageBody.getContentLength()));
            return this;
        }

        public Http11Response build() {
            validate();

            return new Http11Response(this);
        }

        private void validate() {
            if (Objects.isNull(statusLine)) {
                log.error("Http11Response{" +
                        "statusLine=" + statusLine);
                throw new IllegalStateException("빌더: HttpResponse의 필드값이 할당되지 않았습니다.");
            }
        }
    }
}
