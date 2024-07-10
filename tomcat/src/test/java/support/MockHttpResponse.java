package support;

import camp.nextstep.http.domain.HttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

public class MockHttpResponse extends HttpResponse {
    private final OutputStream outputStream;

    private MockHttpResponse(final ByteArrayOutputStream outputStream) {
        super(outputStream);
        this.outputStream = outputStream;
    }

    public static MockHttpResponse create() {
        return new MockHttpResponse(new ByteArrayOutputStream());
    }

    public String getOutputAsString() {
        return outputStream.toString();
    }
}
