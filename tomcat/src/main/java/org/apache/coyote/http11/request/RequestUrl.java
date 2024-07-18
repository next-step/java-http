package org.apache.coyote.http11.request;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestUrl {

    public static final String QUESTION_MARK = "?";
    private static final int REQUEST_URL = 0;
    private static final int REQEUST_PARAMS = 1;
    private static final Pattern KEYVALUE = Pattern.compile("(\\w+)=(\\w+)");
    private static final Pattern AMPERSAND = Pattern.compile("&");
    private final String requestUrl;
    private final Map<String, String> params;

    public RequestUrl(final String requestUrl) {
        if (requestUrl.contains(QUESTION_MARK)) {

            String[] urlAndParams = requestUrl.split("\\?");
            this.requestUrl = urlAndParams[REQUEST_URL];
            this.params = AMPERSAND.splitAsStream(urlAndParams[REQEUST_PARAMS])
                .map(KEYVALUE::matcher)
                .filter(Matcher::find)
                .collect(Collectors.toUnmodifiableMap(m -> m.group(1), m -> m.group(2)));
            return;
        }

        this.requestUrl = requestUrl;
        this.params = Collections.EMPTY_MAP;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "RequestUrl{" +
            "requestUrl='" + requestUrl + '\'' +
            ", params=" + params +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestUrl that = (RequestUrl) o;
        return Objects.equals(requestUrl, that.requestUrl) && Objects.equals(params,
            that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestUrl, params);
    }
}
