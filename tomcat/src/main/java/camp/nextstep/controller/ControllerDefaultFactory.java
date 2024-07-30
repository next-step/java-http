package camp.nextstep.controller;

import org.apache.coyote.controller.ControllerFactory;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.Http11Response;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.header.ContentType;
import org.apache.coyote.http11.response.header.Http11ResponseHeader;
import org.apache.coyote.http11.response.header.Http11ResponseHeader.HttpResponseHeaderBuilder;
import org.apache.coyote.http11.response.statusline.ProtocolVersion;
import org.apache.coyote.http11.response.statusline.StatusCode;

public class ControllerDefaultFactory implements ControllerFactory {

    public ControllerDefaultFactory() {
    }

    @Override
    public HttpResponse serve(HttpRequest httpRequest) {
        String message = "Hello world!";

        Http11ResponseHeader responseHeader = HttpResponseHeaderBuilder.builder()
            .contentType(ContentType.html.name())
            .contentLength(message.getBytes().length)
            .build();

        return new Http11Response.HttpResponseBuilder()
            .statusLine(ProtocolVersion.HTTP11.getVersion(), StatusCode.OK.name())
            .responseHeader(responseHeader)
            .messageBody(message.getBytes())
            .build();
    }
}
