package org.apache.coyote.http11;

import camp.nextstep.exception.UncheckedServletException;
import camp.nextstep.http.domain.RequestLine;
import camp.nextstep.http.domain.Resource;
import camp.nextstep.http.domain.Response;
import camp.nextstep.http.exception.InvalidHttpRequestSpecException;
import camp.nextstep.http.exception.ResourceNotFoundException;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);
    private final Socket connection;

    public Http11Processor(final Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStream = connection.getInputStream();
             final var outputStream = connection.getOutputStream();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {

            RequestLine requestLine = readRequestLine(bufferedReader);
            if (isRootPath(requestLine)) {
                writeDefaultResponse(outputStream);
            }

            Resource resource = Resource.createResourceFromRequestLine(requestLine, getClass().getClassLoader());
            writeResponse(resource.getResourceFile(), outputStream);
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }

    private boolean isRootPath(RequestLine requestLine) {
        return requestLine.getPath().getUrlPath().equals("/");
    }

    private RequestLine readRequestLine(BufferedReader bufferedReader) {
        try {
            String requestLineStr = bufferedReader.readLine();
            return RequestLine.createRequestLineByRequestLineStr(requestLineStr);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new InvalidHttpRequestSpecException(exception.getMessage());
        }
    }

    private void writeDefaultResponse(OutputStream outputStream) throws IOException {
        final var responseBody = "Hello world!";
        Response response = Response.createResponseByString(responseBody);
        writeResponse(response.getResponseStr(), outputStream);
    }

    private void writeResponse(File file, OutputStream outputStream) throws IOException {
        Response response = Response.createResponseByFile(file);
        writeResponse(response.getResponseStr(), outputStream);
    }

    private void writeResponse(String string, OutputStream outputStream) throws IOException {
        outputStream.write(string.getBytes());
        outputStream.flush();
    }
}
