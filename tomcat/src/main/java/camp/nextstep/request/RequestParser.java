package camp.nextstep.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestParser {

    public Request parse(BufferedReader bufferedReader) throws IOException {
        String requestLineString = extractRequestLine(bufferedReader);
        RequestLine requestLine = RequestLine.parse(requestLineString);

        List<String> headers = extractHeaders(bufferedReader);
        RequestHeaders requestHeaders = RequestHeaders.parse(headers);
        String cookieValue = requestHeaders.getCookieHeader();
        RequestCookies requestCookies = RequestCookies.parse(cookieValue);

        String bodyString = extractBody(bufferedReader, requestHeaders.getContentLength());
        QueryParameters requestBody = QueryParameters.parse(bodyString);

        return new Request(requestLine, requestHeaders, requestCookies, requestBody);
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
