package org.apache.coyote.http11;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import org.apache.coyote.http11.exception.StaticResourceNotFoundException;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.header.ContentType;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;
import org.apache.coyote.http11.response.header.Http11ResponseHeader.HttpResponseHeaderBuilder;
import org.apache.coyote.http11.response.statusline.ProtocolVersion;
import org.apache.coyote.http11.response.statusline.StatusCode;

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

    public static HttpResponse resource(String version, String url){
        try {
            final URL resource = HttpEntity.class
                .getClassLoader()
                .getResource(url);
            final File file = new File(Objects.requireNonNull(resource).getFile());
            final Path path = file.toPath();
            final byte[] content = Files.readAllBytes(path);

            final ContentType extension =
                Arrays.stream(ContentType.values())
                    .filter(ext -> file.getName().endsWith(ext.name()))
                    .findFirst().orElseGet(() -> ContentType.all);

            final Http11ResponseHeader http11ResponseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder
                .builder()
                .contentLength(content.length)
                .contentType(extension.name())
                .build();

            return new Http11Response.HttpResponseBuilder()
                .responseHeader(http11ResponseHeader)
                .statusLine(version, StatusCode.OK.name())
                .messageBody(content)
                .build();

        } catch (IOException e) {
            throw new StaticResourceNotFoundException("Static Resource가 없습니다.");
        }
    }

    public static HttpResponse unauthorized(){
        try {

            final URL resource = HttpEntity.class
                .getClassLoader()
                .getResource(UNAUTHOIRZED);

            final File file = new File(Objects.requireNonNull(resource).getFile());
            final Path path = file.toPath();
            final byte[] content = Files.readAllBytes(path);

            final Http11ResponseHeader http11ResponseHeader = Http11ResponseHeader.HttpResponseHeaderBuilder
                .builder()
                .contentLength(content.length)
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
        try {
            final URL resource = HttpEntity.class
                .getClassLoader()
                .getResource(NOTFOUND);
            final File file = new File(Objects.requireNonNull(resource).getFile());
            final Path path = file.toPath();
            final byte[] content = Files.readAllBytes(path);

            Http11ResponseHeader responseHeader = HttpResponseHeaderBuilder.builder()
                .contentType(ContentType.html.name())
                .contentLength(content.length)
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

}
