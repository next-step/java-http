package org.apache.coyote.http11.request;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class RequestParser {

    public static HttpRequest parse(InputStream inputStream) throws IOException {
        return RequestParser.parse(inputStream, Charset.defaultCharset());
    }

    public static HttpRequest parse(InputStream inputStream, Charset charset) throws IOException {
        final var br = new BufferedReader(new InputStreamReader(inputStream, charset));
        final var readLine = br.readLine();

        final var requestLine = new RequestLine(readLine);
        final var requestHeaders = new HttpRequestHeaders(parseHeaders(br));
        final var requestBody = new RequestBody(parseRequestBody(br, requestHeaders.contentLength()));

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

    private static String parseRequestBody(BufferedReader bufferedReader, int contentLength) throws IOException {
        final var buffer = new char[contentLength];
        int readCount = bufferedReader.read(buffer, 0, contentLength);
        if (contentLength != readCount) {
            throw new IOException("Content-Length is not matched: " + contentLength + " != " + readCount);
        }
        return new String(buffer);
    }
}
