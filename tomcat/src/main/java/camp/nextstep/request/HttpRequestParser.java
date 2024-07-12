package camp.nextstep.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpRequestParser {

    public HttpRequest parse(BufferedReader bufferedReader) throws IOException {
        String requestLineString = extractRequestLine(bufferedReader);
        HttpRequestLine requestLine = HttpRequestLine.parse(requestLineString);

        List<String> headers = extractHeaders(bufferedReader);
        HttpRequestHeaders requestHeaders = HttpRequestHeaders.parse(headers);
        String cookieValue = requestHeaders.getCookieHeader();
        HttpRequestCookies cookies = HttpRequestCookies.parse(cookieValue);

        String bodyString = extractBody(bufferedReader, requestHeaders.getContentLength());
        HttpRequestBody requestBody = new HttpRequestBody(bodyString);

        return new HttpRequest(requestLine, requestHeaders, cookies, requestBody);
    }

    private String extractRequestLine(BufferedReader bufferedReader) throws IOException {
        return bufferedReader.readLine();
    }

    private List<String> extractHeaders(BufferedReader bufferedReader) throws IOException {
        final List<String> headers = new ArrayList<>();
        while (bufferedReader.ready()) {
            var line = bufferedReader.readLine();
            if (line.isEmpty()) break;

            headers.add(line);
        }
        return headers;
    }

    private String extractBody(BufferedReader bufferedReader, Integer contentLength) throws IOException {
        String bodyString = null;
        if (contentLength != null && contentLength > 0 && bufferedReader.ready()) {
            char[] buffer = new char[contentLength];
            //noinspection ResultOfMethodCallIgnored
            bufferedReader.read(buffer, 0, contentLength);
            bodyString = new String(buffer);
        }
        return bodyString;
    }

}
