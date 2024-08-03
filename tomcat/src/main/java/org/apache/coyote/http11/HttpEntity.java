package org.apache.coyote.http11;

import org.apache.coyote.http11.exception.StaticResourceNotFoundException;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.header.ContentType;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;
import org.apache.coyote.http11.response.header.Http11ResponseHeader.HttpResponseHeaderBuilder;
import org.apache.coyote.http11.response.header.SetCookieAdd;
import org.apache.coyote.http11.response.statusline.ProtocolVersion;
import org.apache.coyote.http11.response.statusline.StatusCode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class HttpEntity {

    private static final String BASE_DIR = "static";
    private static final String UNAUTHOIRZED = BASE_DIR + "/401.html";
    private static final String NOTFOUND = "static/404.html";


    public static HttpResponse redirect(String redirectPath){
        Http11ResponseHeader responseHeader = HttpResponseHeaderBuilder.builder()
                .contentType(ContentType.html.name())
                .location(redirectPath)
                .build();

        return new Http11Response.HttpResponseBuilder()
                .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.FOUND.name())
                .responseHeader(responseHeader)
                .build();
    }

    public static HttpResponse redirect(String redirectPath, Map<String, String> session){
        Http11ResponseHeader responseHeader = HttpResponseHeaderBuilder.builder()
            .contentType(ContentType.html.name())
            .location(redirectPath)
            .build();

        SetCookieAdd sessionAdder = new SetCookieAdd();
        sessionAdder.add(responseHeader, session);

        return new Http11Response.HttpResponseBuilder()
            .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.FOUND.name())
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
                .statusLine(version, StatusCode.OK.name())
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
                .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.UNAUTHORIZED.name())
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

            Http11ResponseHeader responseHeader = HttpResponseHeaderBuilder.builder()
                .contentType(ContentType.html.name())
                .build();

            return new Http11Response.HttpResponseBuilder()
                .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.NOTFOUND.name())
                .responseHeader(responseHeader)
                .messageBody(content)
                .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File getFile(String url){
        final URL resource = HttpEntity.class
            .getClassLoader()
            .getResource(url);
        return new File(Objects.requireNonNull(resource).getFile());
    }
}
