package org.apache.coyote.http11.request;


import camp.nextstep.exception.UncheckedServletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class RequestParser {

    public static HttpRequest parse(InputStream inputStream) {
        return RequestParser.parse(inputStream, Charset.defaultCharset());
    }

    public static HttpRequest parse(InputStream inputStream, Charset charset) {
        try {
            final var br = new BufferedReader(new InputStreamReader(inputStream, charset));
            final var readLine = br.readLine();

            final var requestLine = new RequestLine(readLine);
            final var requestHeaders = new HttpRequestHeaders(parseHeaders(br));

            return new HttpRequest(requestLine, requestHeaders);
        } catch (IOException e) {
            throw new UncheckedServletException(e);
        } catch (HttpRequestLineInvalidException e) {
            return new HttpRequest(RequestLine.SERVER_ERROR_REQUEST_LINE, new HttpRequestHeaders(List.of()));
        }
    }

    public static List<String> parseHeaders(BufferedReader bufferedReader) throws IOException {
        final var headers = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null && !line.isBlank()) {
            headers.add(line);
        }
        return headers;
    }
}
