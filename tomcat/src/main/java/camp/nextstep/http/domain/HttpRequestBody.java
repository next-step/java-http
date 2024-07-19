package camp.nextstep.http.domain;

import camp.nextstep.http.enums.ContentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class HttpRequestBody {
    private static final Pattern URL_ENCODED_ARGS_SEPARATOR = Pattern.compile("&");
    private static final Pattern URL_ENCODED_KEY_VALUE_SEPARATOR = Pattern.compile("=");
    private static final int URL_ENCODED_KEY_INDEX = 0;
    private static final int URL_ENCODED_VALUE_INDEX = 1;

    private ContentType contentType;
    private String rawRequestBody;

    public HttpRequestBody(ContentType contentType, String rawRequestBody) {
        this.contentType = contentType;
        this.rawRequestBody = rawRequestBody;
    }

    public static HttpRequestBody createHttpRequestBodyFromReader(
            BufferedReader bufferedReader,
            ContentType contentType,
            int contentLength
    ) throws IOException {
        char[] buf = new char[contentLength];
        bufferedReader.read(buf, 0, contentLength);
        String requestBody = new String(buf);

        return new HttpRequestBody(contentType, requestBody);
    }

    // TODO content type 별로 파싱하는 로직이 늘어날텐데 이를 분리 필요
    public Map<String, String> getFormUrlEncodedRequestBody() {
        if (contentType != ContentType.APPLICATION_X_WWW_FORM_URLENCODED) {
            return null;
        }
        Map<String, String> parsedRequestBody = new HashMap<>();
        String[] args = URL_ENCODED_ARGS_SEPARATOR.split(rawRequestBody);
        for (String arg : args) {
            String[] keyVals = URL_ENCODED_KEY_VALUE_SEPARATOR.split(arg);
            parsedRequestBody.put(
                    keyVals[URL_ENCODED_KEY_INDEX],
                    keyVals[URL_ENCODED_VALUE_INDEX]
            );
        }
        return parsedRequestBody;
    }
}
