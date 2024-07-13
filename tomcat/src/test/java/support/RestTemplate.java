package support;

import org.apache.coyote.http.*;
import org.apache.coyote.http11.HttpParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public class RestTemplate {

    private static final String HOST = "http://localhost:8080";
    private static final Logger log = LoggerFactory.getLogger(RestTemplate.class);

    private final String uri;
    private final HttpMethod httpMethod;
    private final HeaderMapping headers = new HeaderMapping();
    private final ParamsMapping params = new ParamsMapping();

    public RestTemplate(final String uri, final HttpMethod httpMethod) {
        this.uri = uri;
        this.httpMethod = httpMethod;
    }

    public void setHeaders(final Map<HttpHeader, String[]> headers) {
        headers.forEach(this.headers::addHeader);
    }

    public void setParams(final Map<String, String> params) {
        this.params.addParams(params);
    }

    private String buildQueryString() {
        final Map<String, String> paramsMapping = params.getParams();
        if (paramsMapping.isEmpty()) {
            return "";
        }
        final StringBuilder queryString = new StringBuilder("?");
        for (final Map.Entry<String, String> entry : paramsMapping.entrySet()) {
            queryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return queryString.toString();
    }

    private String convertMapToBody(final ContentType contentType) throws IOException {
        if (contentType == ContentType.APPLICATION_FORM_URLENCODED) {
            final StringJoiner bodyBuilder = new StringJoiner("&");
            final Map<String, String> paramsMapping = params.getParams();

            for (final Map.Entry<String, String> entry : paramsMapping.entrySet()) {
                bodyBuilder.add(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8));
            }
            return bodyBuilder.toString();
        }

        throw new IllegalArgumentException("Unsupported Content-Type: " + contentType);
    }

    public HttpResponse execute() throws IOException {
        final StringBuilder uriBuilder = new StringBuilder(HOST + uri);
        log.debug("{} execute", uri);

        if (httpMethod == HttpMethod.GET) {
            uriBuilder.append(buildQueryString());
        }

        final URL url = new URL(uriBuilder.toString());
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(httpMethod.getMethod());

        final Map<String, String> headersMapping = headers.getHeaders();
        for (final Map.Entry<String, String> entry : headersMapping.entrySet()) {
            connection.setRequestProperty(entry.getKey(), entry.getValue());
        }

        if (httpMethod == HttpMethod.POST) {
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                final String body = convertMapToBody(ContentType.from(headers.getHeader((HttpHeader.CONTENT_TYPE)).get(0)));
                final byte[] input = body.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        }

        final HttpResponse httpResponse = new HttpResponse();
        final int headerNum = connection.getHeaderFields().size();
        try {
            httpResponse.setResponseLine(connection.getHeaderField(null));
        } catch (HttpParseException e) {
            log.error(e.getMessage());
        }

        for (int i = 1; i < headerNum; i++) {
            final String headerFieldKey = connection.getHeaderFieldKey(i);
            final String headerField = connection.getHeaderField(i);
            final String[] values = headerField.split(";");
            httpResponse.addHeader(HttpHeader.from(headerFieldKey), values);
        }

        httpResponse.setBody(extractBody(connection), ContentType.from(connection.getHeaderField("Content-Type")));

        return httpResponse;
    }

    private String extractBody(final HttpURLConnection connection) throws IOException {
        if (connection.getResponseCode() >= 400) {
            return new String(connection.getErrorStream().readAllBytes());
        }

        return new String(connection.getInputStream().readAllBytes());
    }

}
