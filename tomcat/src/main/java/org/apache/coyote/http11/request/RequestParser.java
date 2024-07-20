package org.apache.coyote.http11.request;



import org.apache.coyote.http11.HttpMessageConverter;
import org.apache.coyote.http11.constants.HttpFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

public final class RequestParser {

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        return RequestParser.parse(inputStream, Charset.defaultCharset());
    }

    public static HttpRequest parse(InputStream inputStream, Charset charset) throws IOException {
        final var br = new BufferedReader(new InputStreamReader(inputStream, charset));
        final var readLine = br.readLine();

        final var requestLine = new RequestLine(readLine);
        final var requestHeaders = new HttpRequestHeaders(parseHeaders(br));

        Optional<Object> value = requestHeaders.get(HttpFormat.HEADERS.CONTENT_LENGTH);
        final var requestBody = new MessageBody(HttpMessageConverter.convert(br, Integer.parseInt(value.orElse("0").toString())));
        return new HttpRequest(requestLine, requestHeaders, requestBody);
    }

    public static List<String> parseHeaders(BufferedReader bufferedReader) throws IOException {
        final var headers = new ArrayList<String>();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            if (line.isEmpty()) {
                break;
            }
            headers.add(line);
        }
        return headers;
    }


}
